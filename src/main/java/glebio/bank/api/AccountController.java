package glebio.bank.api;


import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import glebio.bank.core.account.AccountManager;
import glebio.bank.core.account.AccountManagerImpl;
import glebio.bank.data.Db;
import glebio.bank.data.model.Account;

/**
 * @author Gleb Danichev
 */
@Path("/account")
public class AccountController {

    private final AccountManager accountManager = new AccountManagerImpl();

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public UUID addAccount() {
        Account account = new Account();
        Db.getInstance().addAccount(account);
        return account.getId();
    }

    @GET
    @Path("{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Account getAccount(@PathParam("accountId") UUID accountId) {
        return Db.getInstance().getAccount(accountId).orElseThrow();
    }

    @POST
    @Path("{accountId}/replenish")
    public void replenish(@PathParam("accountId") UUID accountId, @QueryParam("cents") long cents) {
        accountManager.replenish(accountId, cents);
    }
}
