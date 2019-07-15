package glebio.bank.core;

import java.util.Arrays;
import java.util.UUID;

import glebio.bank.data.Account;
import glebio.bank.data.Db;
import glebio.bank.data.Transfer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Gleb Danichev
 */
public class InMemoryTransferManager implements TransferManager {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void transfer(Transfer transfer) {
        Db.getInstance().addTransfer(transfer);
        Account from = Db.getInstance().getAccount(transfer.getFromAccountId()).orElseThrow();
        Account to = Db.getInstance().getAccount(transfer.getToAccountId()).orElseThrow();

        UUID[] ids = {from.getId(), to.getId()};
        Arrays.sort(ids);

        synchronized (ids[0]) {
            synchronized (ids[1]) {
                from.setCents(from.getCents() - transfer.getCents());
                to.setCents(to.getCents() + transfer.getCents());
                logger.info("Transferred {} cent(s) from {} to {}",
                    transfer.getCents(), transfer.getFromAccountId(), transfer.getToAccountId());
            }
        }
    }
}
