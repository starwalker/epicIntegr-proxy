package edu.musc.bi.restclient;

// import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
// import org.jboss.resteasy.annotations.jaxrs.QueryParam;

// import java.util.concurrent.CompletionStage;

// import java.util.Set;
import io.smallrye.mutiny.Uni;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
//
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "gateway-api")
@RegisterClientHeaders
public interface fhirclientService {
    /*
     * Mom impact: This is a sync call (Same method as getMRN)
     */
    @POST
    @Path("/core/patlookup")
    // @Produces(MediaType.APPLICATION_JSON)
    // @Consumes(MediaType.APPLICATION_JSON)
    biApiResp patLookupByDemogr(
            @QueryParam("env") String env, @QueryParam("type") String type, modelPatDemogr demo);
    /*
     * Mom impact: This is a sync call (Same method as getMRN)
     */
    @POST
    @Path("/core/patlookup")
    // @Produces(MediaType.APPLICATION_JSON)
    // @Consumes(MediaType.APPLICATION_JSON)
    Uni<biApiResp> patLookupCoreByDemogr_Async(
            @QueryParam("env") String env, @QueryParam("type") String type, modelPatDemogr demo);

    /*
     * Mobile App: This is an async call
     */
    @POST
    @Path("/inourdnasc/mrn")
    @CircuitBreaker
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    // Set<mrnInOurDNASC> getMRN(@QueryParam("env") String env, modelPatDemogr demo);
    Uni<biApiResp> patLookupDNASCByDemogr_Async(@QueryParam("env") String env, modelPatDemogr demo);

    /*
     * Mobile App: This is an async call
     */
    @POST
    @Path("/inourdnasc/mrn")
    // @Produces(MediaType.APPLICATION_JSON)
    // @Consumes(MediaType.APPLICATION_JSON)
    // Set<mrnInOurDNASC> getMRN(@QueryParam("env") String env, modelPatDemogr demo);
    biApiResp getMRN(@QueryParam("env") String env, modelPatDemogr demo);

    @POST
    @Path("/inourdnasc/pat")
    biApiResp crtPAT(@QueryParam("env") String env, modelPatDemogr demo);
    // Uni<Set<mrnInOurDNASC>> getMRN(@QueryParam("env") String env, modelPatDemogr demo);
    // Uni<RestResponse<mrnInOurDNASC>> getMRN(@QueryParam("env") String env, modelPatDemogr demo);

    @POST
    @Path("/inourdnasc/pat")
    @CircuitBreaker
    Uni<biApiResp> crtPAT_Async(@QueryParam("env") String env, modelPatDemogr demo);

    @POST
    @Path("/inourdnasc/getphone")
    biApiResp getPhone(@QueryParam("env") String env, modelPatReq patreq);
    // Uni<Set<mrnInOurDNASC>> getMRN(@QueryParam("env") String env, modelPatDemogr demo);
    // Uni<RestResponse<mrnInOurDNASC>> getMRN(@QueryParam("env") String env, modelPatDemogr demo);

    @POST
    @Path("/inourdnasc/getnamebymrn")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<String> getNameByMRN(@QueryParam("env") String env, modelPatReq patreq);

    @POST
    @Path("/inourdnasc/getstudystatusbymrn")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    // Uni<biApiResp> getStudyStatusByMRN(@QueryParam("env") String env, modelPatReq patreq);
    Uni<String> getStudyStatusByMRN(@QueryParam("env") String env, modelPatReq patreq);

    @POST
    @Path("/inourdnasc/notable/getstudystatusforupt")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    // Uni<biApiResp> GetStudyStatusByMRN4NotableUpt(@QueryParam("env") String env, modelPatReq
    // patreq);
    Uni<String> GetStudyStatusByMRN4NotableUpt(@QueryParam("env") String env, modelPatReq patreq);
}
