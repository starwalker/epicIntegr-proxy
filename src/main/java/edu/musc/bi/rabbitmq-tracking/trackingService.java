package edu.musc.bi.tracking;

import io.smallrye.mutiny.Uni;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/trackings")
@RegisterRestClient(configKey = "rabbitmq-tracking-api")
public interface trackingService {
    @POST
    @Path("/request")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<modelTrackingRequest> trackingRequest(
            @QueryParam("env") String env, modelTrackingRequest track);
}
