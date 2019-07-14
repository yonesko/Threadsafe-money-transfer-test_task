package glebio.bank.api;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import glebio.bank.data.Account;
import glebio.bank.data.Db;
import glebio.bank.data.Transfer;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Gleb Danichev
 */
public class TransferControllerTest {

    @Test
    public void raceConditionTest() throws InterruptedException, ExecutionException {
        TransferController transferController = new TransferController();
        AccountController accountController = new AccountController();
        Account a = new Account();
        Account b = new Account();
        Db.getInstance().addAccount(a);
        Db.getInstance().addAccount(b);

        accountController.replenish(a.getId(), 500_000);

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<?>> futures = new LinkedList<>();
        for (int i = 0; i < 500_000; i++) {
            futures.add(
                executorService.submit(() -> transferController.transfer(new Transfer(a.getId(), b.getId(), 1))));
        }

        for (Future<?> future : futures) {
            future.get();
        }

        Assert.assertEquals(a.getCents(), 0);
        Assert.assertEquals(b.getCents(), 500_000);
    }

    @Test(timeout = 3_000)
    public void deadLockTest() throws InterruptedException, ExecutionException {
        TransferController transferController = new TransferController();
        AccountController accountController = new AccountController();

        Account a = new Account();
        Account b = new Account();
        Db.getInstance().addAccount(a);
        Db.getInstance().addAccount(b);

        accountController.replenish(a.getId(), 1_000);
        accountController.replenish(b.getId(), 1_000);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<?> future1 = executorService.submit(() -> {
            while (true) {
                transferController.transfer(new Transfer(a.getId(), b.getId(), 1));
            }
        });
        Future<?> future2 = executorService.submit(() -> {
            while (true) {
                transferController.transfer(new Transfer(b.getId(), a.getId(), 1));
            }
        });

        new DeadlockDetector().start();

        future1.get();
        future2.get();
    }

    static class DeadlockDetector extends Thread {

        private static final Logger logger = Logger.getLogger(DeadlockDetector.class.getSimpleName());

        DeadlockDetector() {
            setName("DeadlockDetector");
            setDaemon(true);
        }

        @Override
        public void run() {
            while (true) {
                long[] monitorDeadlockedThreads = ManagementFactory.getThreadMXBean().findMonitorDeadlockedThreads();
                if (monitorDeadlockedThreads != null) {
                    logger.severe(String.format("Deadlock detected %s", Arrays.toString(monitorDeadlockedThreads)));
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    logger.warning(e.getMessage());
                    break;
                }
            }
        }
    }
}
