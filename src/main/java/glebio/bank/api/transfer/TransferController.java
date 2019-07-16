package glebio.bank.api.transfer;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import glebio.bank.core.account.AccountManager;
import glebio.bank.core.account.AccountManagerImpl;
import glebio.bank.core.transfer.TransferManager;
import glebio.bank.core.transfer.TransferManagerImpl;
import glebio.bank.data.model.Account;
import glebio.bank.data.model.Transfer;

/**
 * @author Gleb Danichev
 */
@Path("transfer")
public class TransferController {

    private final TransferManager transferManager = new TransferManagerImpl();

    private final AccountManager accountManager = new AccountManagerImpl();

    @POST
    public Response transfer(
        @QueryParam("from") UUID fromAccountId,
        @QueryParam("to") UUID toAccountId,
        @QueryParam("cents") long cents
    )
    {
        if (cents <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Account fromAccount = accountManager.getAccount(fromAccountId);
        Account toAccount = accountManager.getAccount(toAccountId);
        if (fromAccount.equals(toAccount)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (fromAccount.getCents() < cents) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        transferManager.transfer(new Transfer(fromAccountId, toAccountId, cents));
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transfer> findTransfers(@QueryParam("from") UUID fromAccountId) {
        return transferManager.findTransfers(fromAccountId);
    }
}
