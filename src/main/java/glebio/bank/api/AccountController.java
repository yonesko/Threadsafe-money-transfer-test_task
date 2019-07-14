package glebio.bank.api;


import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import glebio.bank.data.Account;
import glebio.bank.data.Db;

/**
 * @author Gleb Danichev
 */
@Path("/account")
public class AccountController {

    public UUID addAccount() {
        Account account = new Account();
        Db.getInstance().addAccount(account);
        return account.getId();
    }

    @GET
    @Path("/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Account getAccount(@PathParam("accountId") UUID accountId) {
        return Db.getInstance().getAccount(accountId).orElseThrow();
    }

    public void replenish(UUID accountId, long cents) {
        Db.getInstance().getAccount(accountId).orElseThrow()
            .addCents(cents);
    }
}
