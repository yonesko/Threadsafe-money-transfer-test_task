package glebio.bank.api;

import glebio.bank.data.Account;
import glebio.bank.data.Db;
import glebio.bank.data.Transfer;
import org.junit.Test;

/**
 * @author Gleb Danichev
 */
public class TransferControllerTest {

    @Test
    public void transferTest() {
        TransferController transferController = new TransferController();
        Account a = new Account();
        Account b = new Account();
        Db.getInstance().addAccount(a);
        Db.getInstance().addAccount(b);

        Transfer transfer = new Transfer(a.getId(), a.getId(), 341);

        transferController.transfer(transfer);


    }
}
