package glebio.bank.api;

import java.util.UUID;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import glebio.bank.core.transfer.TransferManager;
import glebio.bank.core.transfer.TransferManagerImpl;
import glebio.bank.data.model.Transfer;

/**
 * @author Gleb Danichev
 */
@Path("transfer")
public class TransferController {

    private final TransferManager transferManager = new TransferManagerImpl();

    @POST
    public void transfer(
        @QueryParam("from") UUID fromAccountId,
        @QueryParam("to") UUID toAccountId,
        @QueryParam("cents") long cents
    )
    {
        transferManager.transfer(new Transfer(fromAccountId, toAccountId, cents));
    }
}
