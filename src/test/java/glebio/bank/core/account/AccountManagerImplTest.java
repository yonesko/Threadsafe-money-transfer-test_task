package glebio.bank.core.account;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import glebio.bank.data.Db;
import glebio.bank.data.model.Account;
import org.junit.Assert;
import org.junit.Test;

public class AccountManagerImplTest {

    @Test
    public void replenishRaceConditionTest() throws ExecutionException, InterruptedException {
        AccountManager accountManager = new AccountManagerImpl();
        Account a = new Account();
        Db.getInstance().addAccount(a);

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<?>> futures = new LinkedList<>();
        for (int i = 0; i < 500_000; i++) {
            futures.add(
                executorService.submit(() -> accountManager.replenish(a.getId(), 1)));
        }

        for (Future<?> future : futures) {
            future.get();
        }
        Assert.assertEquals(a.getCents(), 500_000);
    }
}
