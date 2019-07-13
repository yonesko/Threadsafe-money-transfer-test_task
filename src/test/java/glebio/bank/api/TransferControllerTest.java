package glebio.bank.api;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    public void transferTest() throws InterruptedException, ExecutionException {
        TransferController transferController = new TransferController();
        AccountController accountController = new AccountController();
        Account a = new Account();
        Account b = new Account();
        Db.getInstance().addAccount(a);
        Db.getInstance().addAccount(b);

        accountController.replenish(a.getId(), 500_000);


        System.out.println(a);
        System.out.println(b);

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
}
