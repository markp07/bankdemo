package xyz.markpost.bankdemo;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import xyz.markpost.bankdemo.controller.ClientController;


@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

  public JerseyConfig() {
    register(ClientController.class);
  }
}