package glebio.bank.core.transfer;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import glebio.bank.core.account.AccountManager;
import glebio.bank.core.account.AccountManagerImpl;
import glebio.bank.data.model.Account;
import glebio.bank.data.model.Transfer;
import org.junit.Assert;
import org.junit.Test;

public class TransferManagerImplTest {

    private final TransferManager transferManager = new TransferManagerImpl();

    private final AccountManager accountManager = new AccountManagerImpl();

    @Test
    public void raceConditionTest() throws InterruptedException, ExecutionException {
        Account a = accountManager.addAccount();
        Account b = accountManager.addAccount();

        accountManager.replenish(a.getId(), 500_000);

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<?>> futures = new LinkedList<>();
        for (int i = 0; i < 500_000; i++) {
            futures.add(
                executorService.submit(() -> transferManager.transfer(new Transfer(a.getId(), b.getId(), 1))));
        }

        for (Future<?> future : futures) {
            future.get();
        }

        Assert.assertEquals(a.getCents(), 0);
        Assert.assertEquals(b.getCents(), 500_000);
        executorService.shutdownNow();
    }

    @Test
    public void deadLockTest() throws InterruptedException {
        AtomicBoolean stop = new AtomicBoolean();
        Account a = accountManager.addAccount();
        Account b = accountManager.addAccount();

        accountManager.replenish(a.getId(), 1_000);
        accountManager.replenish(b.getId(), 1_000);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> {
            while (!stop.get()) {
                transferManager.transfer(new Transfer(a.getId(), b.getId(), 1));
            }
        });
        executorService.execute(() -> {
            while (!stop.get()) {
                transferManager.transfer(new Transfer(b.getId(), a.getId(), 1));
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
        stop.set(true);
        executorService.shutdownNow();
    }

    @Test
    public void findTransfersTest() {
        Account a = accountManager.addAccount();
        Account b = accountManager.addAccount();

        accountManager.replenish(a.getId(), 500_000);
        for (int i = 0; i < 100_000; i++) {
            transferManager.transfer(new Transfer(a.getId(), b.getId(), 1));
        }

        Assert.assertEquals(100_000, transferManager.findTransfers(a.getId()).size());

    }
}
