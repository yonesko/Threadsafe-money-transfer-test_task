package glebio.bank.core.account;

import java.util.UUID;

import glebio.bank.data.Db;
import glebio.bank.data.model.Account;

public class AccountManagerImpl implements AccountManager {

    private final Db db = Db.getInstance();

    @Override
    public void replenish(UUID accountId, long cents) {
        Account account = db.getAccounts().get(accountId);
        synchronized (account) {
            account.addCents(cents);
        }
    }

    @Override
    public Account addAccount() {
        Account account = new Account();
        db.getAccounts().putIfAbsent(account.getId(), account);
        return account;
    }

    @Override
    public Account getAccount(UUID accountId) {
        return db.getAccounts().get(accountId);
    }
}
