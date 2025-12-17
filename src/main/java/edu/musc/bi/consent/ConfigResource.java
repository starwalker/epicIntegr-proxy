package edu.musc.bi.consent;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@Path("/config")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConfigResource {
    private static final Logger LOGGER = Logger.getLogger("ConfigResource");

    @ConfigProperty(name = "consent-app-config.grant-type")
    String granttype;

    @ConfigProperty(name = "consent-app-config.client-id")
    String clientid;

    @ConfigProperty(name = "consent-app-config.launch-uri")
    String launchuri;

    @ConfigProperty(name = "consent-app-config.redirect-uri")
    String redirecturi;

    @ConfigProperty(name = "consent-app-config.scope")
    String scope;

    public ConfigResource() {}

    @GET
    public Response list() {
        Config config = new Config(granttype, clientid, launchuri, redirecturi, scope);
        LOGGER.info("Config resource got called.");
        return Response.ok(config).build();
    }
}
