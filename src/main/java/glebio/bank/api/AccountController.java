package glebio.bank.api;


import java.util.UUID;

import glebio.bank.data.Account;
import glebio.bank.data.Db;

/**
 * @author Gleb Danichev
 */
public class AccountController {

    public UUID addAccount() {
        Account account = new Account();
        Db.getInstance().addAccount(account);
        return account.getId();
    }

    public Account getAccount(UUID accountId) {
        return Db.getInstance().getAccount(accountId).orElseThrow();
    }
}
