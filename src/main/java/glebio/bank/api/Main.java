package glebio.bank.api;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author Gleb Danichev
 */
public class Main {

    public static void main(String[] args) {
        JettyHttpContainerFactory.createServer(
            UriBuilder.fromUri("http://localhost/").port(9998).build(),
            new ResourceConfig(AccountController.class, UUIDMessageBodyWriter.class)
        );
    }
}
