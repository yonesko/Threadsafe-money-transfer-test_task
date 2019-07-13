package glebio.bank.api;

import glebio.bank.data.Db;
import glebio.bank.data.Transfer;

/**
 * @author Gleb Danichev
 */
public class TransferController {

    public void addTransfer(Transfer transfer) {
        Db.getInstance().addTransfer(transfer);
    }
}
