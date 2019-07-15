package glebio.bank.core.transfer;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import glebio.bank.api.AccountController;
import glebio.bank.data.Account;
import glebio.bank.data.Db;
import glebio.bank.data.Transfer;
import org.junit.Assert;
import org.junit.Test;

public class InMemoryTransferManagerTest {

    @Test
    public void raceConditionTest() throws InterruptedException, ExecutionException {
        TransferManager transferController = new InMemoryTransferManager();
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

    @Test
    public void deadLockTest() throws InterruptedException {
        TransferManager transferController = new InMemoryTransferManager();
        AccountController accountController = new AccountController();

        Account a = new Account();
        Account b = new Account();
        Db.getInstance().addAccount(a);
        Db.getInstance().addAccount(b);

        accountController.replenish(a.getId(), 1_000);
        accountController.replenish(b.getId(), 1_000);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> {
            while (true) {
                transferController.transfer(new Transfer(a.getId(), b.getId(), 1));
            }
        });
        executorService.execute(() -> {
            while (true) {
                transferController.transfer(new Transfer(b.getId(), a.getId(), 1));
            }
        });

        for (int i = 0; i < 100; i++) {
            long[] lockedThreadIds = ManagementFactory.getThreadMXBean().findMonitorDeadlockedThreads();
            if (lockedThreadIds != null) {
                throw new AssertionError("Deadlock detected:" + Arrays.toString(
                    ManagementFactory.getThreadMXBean().getThreadInfo(lockedThreadIds)));
            }
            Thread.sleep(5);
        }
    }
}
