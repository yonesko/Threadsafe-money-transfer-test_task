package glebio.bank.processing.tasks;


import glebio.bank.data.Transfer;

/**
 * @author Gleb Danichev
 */
public class TransferTask implements Runnable {

    private final Transfer transfer;

    public TransferTask(Transfer transfer) {
        this.transfer = transfer;
    }

    @Override
    public void run() {

    }
}
