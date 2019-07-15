package glebio.bank.api.transfer;

import javax.ws.rs.core.Application;

import glebio.bank.api.account.AccountController;
import glebio.bank.api.jersey.UUIDMessageBodyWriter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

public class TransferControllerTest extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(AccountController.class, UUIDMessageBodyWriter.class);
    }

    @Test
    public void transferTest() {

    }

    @Test
    public void findTransfers() {
    }
}
