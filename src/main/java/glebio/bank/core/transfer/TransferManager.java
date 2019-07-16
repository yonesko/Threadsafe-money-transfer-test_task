package glebio.bank.core.transfer;

import java.util.List;
import java.util.UUID;

import glebio.bank.data.model.Transfer;

/**
 * @author Gleb Danichev
 */
public interface TransferManager {

    void transfer(Transfer transfer);

    List<Transfer> findTransfers(UUID fromAccountId);
}
