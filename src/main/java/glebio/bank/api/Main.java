package glebio.bank.api;

import javax.ws.rs.core.UriBuilder;

import glebio.bank.api.account.AccountController;
import glebio.bank.api.transfer.TransferController;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Slf4jRequestLog;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author Gleb Danichev
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Server server = JettyHttpContainerFactory.createServer(
            UriBuilder.fromUri("http://localhost/").port(9998).build(),
            new ResourceConfig(
                AccountController.class,
                TransferController.class,
                UUIDMessageBodyWriter.class
            )
        );
        configureServer(server);
    }

    private static void configureServer(Server server) throws Exception {
        Slf4jRequestLog requestLog = new Slf4jRequestLog();
        requestLog.start();
        server.setRequestLog(requestLog);
    }
}
