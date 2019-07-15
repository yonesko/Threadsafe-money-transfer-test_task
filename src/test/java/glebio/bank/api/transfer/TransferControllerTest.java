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
            .queryParam("to", tomAccount.getId().toString())
            .queryParam("cents", 1234 * 100)
            .request().method("POST");

        Assert.assertEquals(Response.Status.OK.getStatusCode(), transferResponce.getStatus());
    }

    @Test
    public void findTransfers() {
    }
}
