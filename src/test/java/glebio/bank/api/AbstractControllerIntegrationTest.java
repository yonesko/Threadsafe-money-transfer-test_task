package glebio.bank.api;

import java.util.UUID;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import glebio.bank.api.account.AccountController;
import glebio.bank.api.transfer.TransferController;
import glebio.bank.data.model.Account;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;

public class AbstractControllerIntegrationTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(AccountController.class, TransferController.class);
    }

    protected Account getAccount(UUID accountCreatedId) {
        return target("account")
            .path(accountCreatedId.toString()).request()
            .get(Account.class);
    }

    protected Account createAccount() {
        Response response = target("account").request().method("POST");

        Assert.assertEquals("Http Response should be 201 ",
            Response.Status.CREATED.getStatusCode(), response.getStatus());

        return response.readEntity(Account.class);
    }

    protected Response replenish(UUID uuid, int cents) {
        return target("account")
            .path(uuid.toString())
            .path("replenish")
            .queryParam("cents", cents).request().method("POST");
    }

    protected Response transfer(Account fromAccount, Account toAccount, int cents) {
        return target("transfer")
            .queryParam("from", fromAccount.getId().toString())
            .queryParam("to", toAccount.getId().toString())
            .queryParam("cents", cents)
            .request().method("POST");
    }
}
