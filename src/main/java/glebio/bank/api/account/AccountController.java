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
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAccount() {
        return Response
            .status(Response.Status.CREATED)
            .entity(accountManager.addAccount())
            .build();
    }

    @GET
    @Path("{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@PathParam("accountId") UUID accountId) {
        Account account = accountManager.getAccount(accountId);
        if (account == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(account).build();
    }

    @POST
    @Path("{accountId}/replenish")
    public Response replenish(@PathParam("accountId") UUID accountId, @QueryParam("cents") long cents) {
        if (accountManager.getAccount(accountId) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (cents <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        accountManager.replenish(accountId, cents);
        return Response.ok().build();
    }

}
