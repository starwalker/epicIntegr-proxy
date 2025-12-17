package edu.musc.bi.aesclient;

import io.smallrye.mutiny.Uni;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/json")
@RegisterRestClient(configKey = "aes-api")
public interface aesClientService {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<String> getMessage_Async(modelAESReq reqAES);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    String getMessage(modelAESReq reqAES);

    // this isn't used, but it makes sure that the generated provider can be properly instantiated
    // in native mode
    // @ClientExceptionMapper
    // static RuntimeException toException(Response response) {
    //    if (response.getStatus() == 400) {
    //        return new RuntimeException("The remote service responded with HTTP 400");
    //    } else if (response.getStatus() == 404) {
    //        return new RuntimeException("The remote service responded with HTTP 404");
    //    } else if (response.getStatus() == 500) {
    //        return new RuntimeException("The remote service responded with HTTP 500");
    //    }
    //    return null;
    // }
}
