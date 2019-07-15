package glebio.bank.api.account;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import glebio.bank.api.jersey.UUIDMessageBodyWriter;
import glebio.bank.data.model.Account;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

public class AccountControllerIntegrationTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(AccountController.class, UUIDMessageBodyWriter.class);
    }

    @Test
    public void accountCreationTest() {
        Response response = target(AccountController.ACCOUNT_PATH).request().method("POST");

        Assert.assertEquals("Http Response should be 201 ",
            Response.Status.CREATED.getStatusCode(), response.getStatus());

        Account accountCreated = response.readEntity(Account.class);
        Account accountActual =
            target(AccountController.ACCOUNT_PATH).path(accountCreated.getId().toString()).request().get(Account.class);

        Assert.assertEquals(accountActual, accountActual);
    }
}
