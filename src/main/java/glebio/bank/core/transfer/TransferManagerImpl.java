package glebio.bank.core.transfer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import glebio.bank.data.Db;
import glebio.bank.data.model.Account;
import glebio.bank.data.model.Transfer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Gleb Danichev
 */
public class TransferManagerImpl implements TransferManager {

    private static final Logger logger = LogManager.getLogger();

    private final Db db = Db.getInstance();

    @Override
    public void transfer(Transfer transfer) {
        Account from = db.getAccounts().get(transfer.getFromAccountId());
        Account to = db.getAccounts().get(transfer.getToAccountId());
        UUID[] ids = {from.getId(), to.getId()};
        Arrays.sort(ids);

        synchronized (ids[0]) {
            synchronized (ids[1]) {
                if (from.getCents() < transfer.getCents()) {
                    logger.error("No enough sum {}", transfer);
                    return;
                }
                from.subtractCents(transfer.getCents());
                to.addCents(transfer.getCents());
                db.getTransfers().add(transfer);
                logger.info(
                    "Transferred {} cent(s) from {} to {}",
                    transfer.getCents(),
                    transfer.getFromAccountId(),
                    transfer.getToAccountId()
                );
            }
        }
    }

    @Override
    public List<Transfer> findTransfers(UUID fromAccountId) {
        return List.copyOf(Db.getInstance().getTransfers()).stream()
            .sorted(Comparator.comparing(Transfer::getCreated).reversed())
            .filter(transfer -> transfer.getFromAccountId().equals(fromAccountId))
            .collect(Collectors.toList());
    }
}
