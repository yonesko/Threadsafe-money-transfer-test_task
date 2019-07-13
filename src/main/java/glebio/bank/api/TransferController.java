package glebio.bank.api;

import glebio.bank.data.Account;
import glebio.bank.data.Db;
import glebio.bank.data.Transfer;

/**
 * @author Gleb Danichev
 */
public class TransferController {

    public void transfer(Transfer transfer) {
        Account from = Db.getInstance().getAccount(transfer.getFromAccountId()).orElseThrow();
        Account to = Db.getInstance().getAccount(transfer.getToAccountId()).orElseThrow();
        synchronized (from) {
            synchronized (to) {
                from.setCents(from.getCents() - transfer.getCents());
                to.setCents(to.getCents() + transfer.getCents());
            }
        }
    }
}
