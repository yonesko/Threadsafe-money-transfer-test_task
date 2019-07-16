package glebio.bank.api.transfer;

import java.util.List;

import javax.ws.rs.core.GenericType;
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

        Response transferResponse = transfer(tomAccount, zendayaAccount, 2300 * 100);

        Assert.assertEquals(Response.Status.OK.getStatusCode(), transferResponse.getStatus());
        Assert.assertEquals(2700 * 100, getAccount(tomAccount.getId()).getCents());
        Assert.assertEquals(2300 * 100, getAccount(zendayaAccount.getId()).getCents());
    }

    @Test
    public void transferNotEnoughMoneyTest() {
        Account tomAccount = createAccount();
        Account zendayaAccount = createAccount();
        Response response = transfer(tomAccount, zendayaAccount, 2345678);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void transferNegativeMoneyTest() {
        Account tomAccount = createAccount();
        Account zendayaAccount = createAccount();
        Response response = transfer(tomAccount, zendayaAccount, -2345678);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void transferSameFromAndToTest() {
        Account account = createAccount();
        replenish(account.getId(), 5);
        Response response = transfer(account, account, 1);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void findTransfersTest() {
        Account tomAccount = createAccount();
        Account zendayaAccount = createAccount();

        Response replenishResponse = replenish(tomAccount.getId(), 5000 * 100);

        Assert.assertEquals(Response.Status.OK.getStatusCode(), replenishResponse.getStatus());

        Response transferResponse = transfer(tomAccount, zendayaAccount, 2300 * 100);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), transferResponse.getStatus());

        List<Object> transfers = target("transfer")
            .queryParam("from", tomAccount.getId())
            .request().get(new GenericType<List<Object>>() {
            });

        Assert.assertEquals(1, transfers.size());
    }
}
