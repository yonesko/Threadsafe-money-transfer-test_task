package glebio.bank.core;

import glebio.bank.data.Transfer;

/**
 * @author Gleb Danichev
 */
public interface TransferManager {

    void transfer(Transfer transfer);
}
