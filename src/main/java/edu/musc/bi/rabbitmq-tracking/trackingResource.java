package edu.musc.bi.tracking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import io.quarkus.logging.Log;
import io.quarkus.runtime.configuration.ProfileManager;
import io.smallrye.mutiny.Uni;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@ApplicationScoped
@Path("/bitrackings")
public class trackingResource {
    private static final ObjectMapper mapper = new JsonMapper();
    protected static final AtomicBoolean flag = new AtomicBoolean(false);
    private static String profile = ProfileManager.getActiveProfile();

    @Inject @RestClient trackingService tService;

    @ConfigProperty(name = "rabbitmq-tracking.baseurl")
    String baseurl;

    @POST
    @Path("/dnasc/request")
    public Uni<modelTrackingRequest> Tracking_Async(
            @QueryParam("env") String env, final modelTrackingRequest track) {
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        Log.infof(
                "TransactionID: _%s_%s | TransactionType: %s | TransactionStatus: %s_%s",
                profile, uuid, "PATLookUp/LookupAsync/Tracking", "Completed", "url: " + baseurl);
        return tService.trackingRequest(env, track);
    }
}
