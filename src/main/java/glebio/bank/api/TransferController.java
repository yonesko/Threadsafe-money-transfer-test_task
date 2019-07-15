package glebio.bank.api;

import glebio.bank.core.transfer.TransferManager;
import glebio.bank.core.transfer.TransferManagerImpl;
import glebio.bank.data.model.Transfer;

/**
 * @author Gleb Danichev
 */
public class TransferController {

    private final TransferManager transferManager = new TransferManagerImpl();

    public void transfer(Transfer transfer) {
        transferManager.transfer(transfer);
    }
}
