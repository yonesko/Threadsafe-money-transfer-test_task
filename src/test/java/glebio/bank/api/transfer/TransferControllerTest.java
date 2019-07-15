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

        Response transferResponce = target("transfer")
            .queryParam("from", tomAccount.getId().toString())
            .queryParam("to", zendayaAccount.getId().toString())
            .queryParam("cents", 2300 * 100)
            .request().method("POST");

        Assert.assertEquals(Response.Status.OK.getStatusCode(), transferResponce.getStatus());

        Assert.assertEquals(2700 * 100, getAccount(tomAccount).getCents());
        Assert.assertEquals(2300 * 100, getAccount(zendayaAccount).getCents());
    }

    @Test
    public void findTransfers() {
    }
}
