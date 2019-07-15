package glebio.bank.api.account;


import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import glebio.bank.core.account.AccountManager;
import glebio.bank.core.account.AccountManagerImpl;
import glebio.bank.data.model.Account;

/**
 * @author Gleb Danichev
 */
@Path("account")
public class AccountController {

    private final AccountManager accountManager = new AccountManagerImpl();

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public UUID addAccount() {
        return accountManager.addAccount().getId();
    }

    @GET
    @Path("{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Account getAccount(@PathParam("accountId") UUID accountId) {
        return accountManager.getAccount(accountId);
    }

    @POST
    @Path("{accountId}/replenish")
    public Response replenish(@PathParam("accountId") UUID accountId, @QueryParam("cents") long cents) {
        if (accountManager.getAccount(accountId) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().build();
    }

}
