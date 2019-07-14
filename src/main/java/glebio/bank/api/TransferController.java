package glebio.bank.api;

import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Logger;

import glebio.bank.data.Account;
import glebio.bank.data.Db;
import glebio.bank.data.Transfer;

/**
 * @author Gleb Danichev
 */
public class TransferController {

    private static final Logger logger = Logger.getLogger(TransferController.class.getSimpleName());

    public void transfer(Transfer transfer) {
        Account from = Db.getInstance().getAccount(transfer.getFromAccountId()).orElseThrow();
        Account to = Db.getInstance().getAccount(transfer.getToAccountId()).orElseThrow();

        UUID[] ids = {from.getId(), to.getId()};
        Arrays.sort(ids);

        synchronized (ids[0]) {
            synchronized (ids[1]) {
                from.setCents(from.getCents() - transfer.getCents());
                to.setCents(to.getCents() + transfer.getCents());
                logger.info(String.format("Transferred %s from %s to %s",
                    transfer.getCents(), transfer.getFromAccountId(), transfer.getToAccountId()));
            }
        }
    }
}
