package glebio.bank.core.account;

import java.util.UUID;

import glebio.bank.data.Db;
import glebio.bank.data.model.Account;

public class AccountManagerImpl implements AccountManager {

    @Override
    public void replenish(UUID accountId, long cents) {
        Account account = Db.getInstance().getAccount(accountId).orElseThrow();
        synchronized (account) {
            account.addCents(cents);
        }
    }
}
