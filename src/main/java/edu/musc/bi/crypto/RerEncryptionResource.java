package edu.musc.bi.crypto;

import io.smallrye.mutiny.Uni;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/security/iodsc")
@PermitAll
@ApplicationScoped
public class RerEncryptionResource {

    private static final Logger LOGGER = Logger.getLogger(RerEncryptionResource.class);

    @Inject RerEncryptionService service;
    @Inject Validator validator;
    @Inject JsonWebToken jwt;

    @ConfigProperty(name = "bi-key-dlscan")
    String keyDLScan;

    @ConfigProperty(name = "bi-key-notable")
    String keyNotable;

    @ConfigProperty(name = "bi-key-dlscan-first")
    String keyDLScanFirst;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/encrypt/mobile")
    public Uni<Response> encryptmobile(@Valid RerEncryption body) throws Exception {
        try {
            return service.encrypt(body, keyDLScan);
        } catch (ConstraintViolationException e) {
            return Uni.createFrom()
                    .item(
                            Response.status(400)
                                    .entity(new EncryptResp(e.getConstraintViolations()))
                                    .build());
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/encrypt/notable")
    public Uni<Response> encryptnotable(@Valid RerEncryption body) throws Exception {
        try {
            return service.encrypt(body, keyNotable);
        } catch (ConstraintViolationException e) {
            return Uni.createFrom()
                    .item(
                            Response.status(400)
                                    .entity(new EncryptResp(e.getConstraintViolations()))
                                    .build());
        }
    }
}
