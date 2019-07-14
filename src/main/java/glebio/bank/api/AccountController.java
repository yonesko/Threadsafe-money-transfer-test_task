package glebio.bank.api;


import java.util.UUID;

import javax.ws.rs.GET;

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

    @GET
    public Account getAccount(UUID accountId) {
        return Db.getInstance().getAccount(accountId).orElseThrow();
    }

    public void replenish(UUID accountId, long cents) {
        Db.getInstance().getAccount(accountId).orElseThrow()
            .addCents(cents);
    }
}
