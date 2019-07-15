package glebio.bank.core.account;

import java.util.UUID;

import glebio.bank.data.Account;
import glebio.bank.data.Db;

public class AccountManagerImpl implements AccountManager {

    @Override
    public void replenish(UUID accountId, long cents) {
        Account account = Db.getInstance().getAccount(accountId).orElseThrow();
        account.addCents(cents);
    }
}
