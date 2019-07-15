package glebio.bank.api.transfer;

import javax.ws.rs.core.Response;

import glebio.bank.api.AbstractControllerIntegrationTest;
import glebio.bank.data.model.Account;
import org.junit.Assert;
import org.junit.Test;

public class TransferControllerTest extends AbstractControllerIntegrationTest {

    @Test
    public void transferTest() {
        Account tomAccount = createAccount();
        Account zendayaAccount = createAccount();

        Response replenishResponse = replenish(tomAccount.getId(), 5000 * 100);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), replenishResponse.getStatus());

        Response transferResponce = transfer(tomAccount, zendayaAccount, 2300 * 100);

        Assert.assertEquals(Response.Status.OK.getStatusCode(), transferResponce.getStatus());

        Assert.assertEquals(2700 * 100, getAccount(tomAccount.getId()).getCents());
        Assert.assertEquals(2300 * 100, getAccount(zendayaAccount.getId()).getCents());
    }

    @Test
    public void transferErrorTest() {
        Account tomAccount = createAccount();
        Account zendayaAccount = createAccount();
    }

    @Test
    public void findTransfers() {
    }
}
