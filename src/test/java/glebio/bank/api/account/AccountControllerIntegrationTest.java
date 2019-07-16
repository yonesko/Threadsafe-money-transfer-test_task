package glebio.bank.api.account;

import java.util.UUID;

import javax.ws.rs.core.Response;

import glebio.bank.api.AbstractControllerIntegrationTest;
import glebio.bank.data.model.Account;
import org.junit.Assert;
import org.junit.Test;

public class AccountControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Test
    public void getAbsentAccountTest() {
        Response response = target("account")
            .path(UUID.randomUUID().toString()).request().get();
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void accountCreationTest() {
        Account accountCreated = createAccount();
        Account accountActual = getAccount(accountCreated.getId());
        Assert.assertEquals(accountCreated, accountActual);
    }

    @Test
    public void replenishAbsentAccountTest() {
        Response response = replenish(UUID.randomUUID(), 34);
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void replenishNegativeSumTest() {
        Account account = createAccount();
        Response response = replenish(account.getId(), -1);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void accountReplenishTest() {
        Account account = createAccount();
        replenish(account.getId(), 922459);
        Assert.assertEquals(922459, getAccount(account.getId()).getCents());
    }
}
