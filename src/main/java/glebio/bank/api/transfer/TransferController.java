package glebio.bank.api.transfer;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import glebio.bank.core.transfer.TransferManager;
import glebio.bank.core.transfer.TransferManagerImpl;
import glebio.bank.data.Db;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transfer> findTransfers(@QueryParam("from") UUID fromAccountId) {
        return Db.getInstance().getTransfers().stream()
            .sorted(Comparator.comparing(Transfer::getCreated).reversed())
            .filter(transfer -> transfer.getFromAccountId().equals(fromAccountId))
            .collect(Collectors.toList());
    }
}
