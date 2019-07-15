package glebio.bank.api;

import glebio.bank.core.InMemoryTransferManager;
import glebio.bank.core.TransferManager;
import glebio.bank.data.Transfer;

/**
 * @author Gleb Danichev
 */
public class TransferController {

    private final TransferManager transferManager = new InMemoryTransferManager();

    public void transfer(Transfer transfer) {
        transferManager.transfer(transfer);
    }
}
