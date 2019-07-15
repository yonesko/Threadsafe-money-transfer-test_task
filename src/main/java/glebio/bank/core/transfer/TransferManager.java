package glebio.bank.core.transfer;

import glebio.bank.data.Transfer;

/**
 * @author Gleb Danichev
 */
public interface TransferManager {

    void transfer(Transfer transfer);
}
