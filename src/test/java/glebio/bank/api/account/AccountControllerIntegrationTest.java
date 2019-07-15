package glebio.bank.api.account;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import glebio.bank.api.jersey.UUIDMessageBodyWriter;
import glebio.bank.data.model.Account;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

public class AccountControllerIntegrationTest extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(AccountController.class, UUIDMessageBodyWriter.class);
    }

    @Test
    public void accountCreationTest() {
        Account accountCreated = createAccount();
        Account accountActual = getAccount(accountCreated);
        Assert.assertEquals(accountCreated, accountActual);
    }

    @Test
    public void accountReplenishTest() {
        Account account = createAccount();
        target(AccountController.ACCOUNT_PATH)
            .path(account.getId().toString())
            .path("replenish")
            .queryParam("cents", 922459).request().method("POST");
        Assert.assertEquals(922459, getAccount(account).getCents());
    }

    private Account getAccount(Account accountCreated) {
        return target(AccountController.ACCOUNT_PATH)
            .path(accountCreated.getId().toString()).request()
            .get(Account.class);
    }

    private Account createAccount() {
        Response response = target(AccountController.ACCOUNT_PATH).request().method("POST");

        Assert.assertEquals("Http Response should be 201 ",
            Response.Status.CREATED.getStatusCode(), response.getStatus());

        return response.readEntity(Account.class);
    }
}
