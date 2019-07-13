package glebio.bank.api;

import glebio.bank.data.Db;
import glebio.bank.data.Transfer;

/**
 * @author Gleb Danichev
 */
public class TransferController {

    public void transfer(Transfer transfer) {
        Db.getInstance().getAccount(transfer.getFromAccountId());
    }
}
