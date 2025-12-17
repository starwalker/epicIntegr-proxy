package edu.musc.bi.crypto;

import io.quarkus.runtime.configuration.ProfileManager;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpServerRequest;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

@Path("/inourdnasc/crypto")
@RolesAllowed({"iodsc", "bi-dev", "bi-admin", "bi-client", "bmic-client", "momsimpactt"})
@Provider
@ApplicationScoped
public class RerDecryptionResource {

    private static final Logger LOGGER = Logger.getLogger(RerDecryptionResource.class);

    @Context UriInfo info;

    @Context HttpServerRequest request;

    @Inject RerDecryptionService service;
    private static String profile = ProfileManager.getActiveProfile();

    @Inject Validator validator;
    @Inject JsonWebToken jwt;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Counted(
            name = "performedChecks",
            description = "How many primality checks have been performed.")
    @Timed(
            name = "checksTimer",
            description = "A measure of how long it takes to perform the primality test.",
            unit = MetricUnits.MILLISECONDS)
    @Path("/epictoken")
    public Uni<Response> decrypt(
            @Context HttpServerRequest request,
            @DefaultValue("test") @QueryParam("env") String env,
            @DefaultValue("adult") @QueryParam("agegroup") String agegroup, // all, adult, child
            @DefaultValue("false") @QueryParam("rtnsrc") boolean rtnsrc, // all, adult, child
            @DefaultValue("false") @QueryParam("rtnenrollstatus")
                    boolean rtnenrollstatus, // all, adult, child
            @Valid RerDecryption body)
            throws Exception {
        try {
            final String address = request.remoteAddress().toString();

            return service.decrypt(env, agegroup, rtnsrc, rtnenrollstatus, body);
        } catch (ConstraintViolationException e) {
            final String errMsg =
                    String.format(
                            "Rest Client Chain has an issue!! decrypt: %s -- error: %s", body, e);
            LOGGER.errorf(e, errMsg);
            modelDecryptResp respDecrypt = new modelDecryptResp();
            respDecrypt.setMsg(errMsg);
            return Uni.createFrom()
                    .item(
                            Response.status(400)
                                    // .entity(new modelDecryptResp(e.getConstraintViolations()))
                                    .entity(respDecrypt)
                                    .build());
        } catch (Exception e) {
            // LOGGER.errorf(e, "Rest Client Chain has an issue!! decrypt: %s", body);
            final String errMsg =
                    String.format(
                            "Rest Client Chain has an issue!! decrypt: %s -- error: %s", body, e);
            LOGGER.errorf(e, errMsg);
            modelDecryptResp respDecrypt = new modelDecryptResp();
            respDecrypt.setMsg(errMsg);
            return Uni.createFrom()
                    .item(
                            Response.status(400)
                                    // .entity(new modelDecryptResp(e.getMessage(), "", "", "", "",
                                    // "",
                                    // ""))
                                    .entity(respDecrypt)
                                    .build());
        }
    }

    @Path("/getepictoken")
    public Uni<Response> encrypt(
            @Context HttpServerRequest request,
            @DefaultValue("test") @QueryParam("env") String env,
            @DefaultValue("adult") @QueryParam("agegroup")
                    String agegroup // all, adult, child
                            ,
            @Valid RerDecryption body)
            throws Exception {
        try {
            final String address = request.remoteAddress().toString();

            return service.encrypt(env, agegroup, body);
        } catch (ConstraintViolationException e) {
            // LOGGER.errorf(e, "Rest Client Chain has an issue!! encrypt: %s", body);
            final String errMsg =
                    String.format(
                            "Rest Client Chain has an issue!! encrypt: %s -- error: %s", body, e);
            LOGGER.errorf(e, errMsg);
            modelDecryptResp respDecrypt = new modelDecryptResp();
            respDecrypt.setMsg(errMsg);
            return Uni.createFrom()
                    .item(
                            Response.status(400)
                                    // .entity(new modelDecryptResp(e.getConstraintViolations()))
                                    .entity(respDecrypt)
                                    .build());
        } catch (Exception e) {
            // LOGGER.errorf(e, "Rest Client Chain has an issue!! encrypt: %s", body);
            final String errMsg =
                    String.format(
                            "Rest Client Chain has an issue!! encrypt: %s -- error: %s", body, e);
            LOGGER.errorf(e, errMsg);
            modelDecryptResp respDecrypt = new modelDecryptResp();
            respDecrypt.setMsg(errMsg);
            return Uni.createFrom()
                    .item(
                            Response.status(400)
                                    // .entity(new modelDecryptResp(e.getMessage(), "", "", "", "",
                                    // "",
                                    // ""))
                                    .entity(respDecrypt)
                                    .build());
        }
    }
}
