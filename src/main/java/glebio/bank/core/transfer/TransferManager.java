package glebio.bank.core.transfer;

import glebio.bank.data.model.Transfer;

/**
 * @author Gleb Danichev
 */
public interface TransferManager {

    void transfer(Transfer transfer);
}
