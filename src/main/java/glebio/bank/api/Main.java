package glebio.bank.api;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author Gleb Danichev
 */
public class Main {

    public static void main(String[] args) {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        ResourceConfig config = new ResourceConfig(AccountController.class);
        JettyHttpContainerFactory.createServer(baseUri, config);
    }
}
