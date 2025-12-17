package edu.musc.bi.det;

//import org.jboss.resteasy.reactive.PartType;
import io.vertx.mutiny.core.MultiMap;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.codec.BodyCodec;

//import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;
import edu.musc.bi.utils.wdUtils;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.*;
import java.io.IOException;
import java.time.Duration;
import java.util.stream.Collectors;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import io.smallrye.common.annotation.NonBlocking;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.google.protobuf.Timestamp;
import edu.musc.bi.HeaderCollectingInterceptor;
import edu.musc.bi.det.model.modelPatRegRecord;
import edu.musc.bi.det.model.modelRedcapAIPRequestFormDto;
import edu.musc.bi.det.moms.impactt.modelLandingPageIntakeRecord;
import edu.musc.bi.det.moms.impactt.modelPatLookupResult;
import edu.musc.bi.humanNameParser.*;
import edu.musc.bi.restclient.biApiResp;
import edu.musc.bi.restclient.fhirclientResource;
import edu.musc.bi.restclient.modelPatDemogr;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import org.jboss.resteasy.reactive.RestQuery;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
//import java.util.concurrent.CompletionStage;
//import java.util.concurrent.CompletableFuture;
import javax.ws.rs.Consumes;
import java.util.concurrent.CompletableFuture;
import javax.annotation.security.*;
import javax.ws.rs.core.*;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.resteasy.reactive.client.api.QuarkusRestClientProperties;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.RedirectionException;
import javax.ws.rs.core.SecurityContext;

// import io.quarkus.arc.Lock;
// import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Path("/det")
public class biDETResource {

    // private static String redcap_url = "";
    private final modelRedcapAIPRequestFormDto formData = new modelRedcapAIPRequestFormDto();

    @Inject
    fhirclientResource fcRes;

    @Inject
    @RestClient
    biDETService detService;

    private final Vertx vertx;
    private final WebClient client;

    @Context
    UriInfo uri;

    @Inject
    @ConfigProperty(name = "redcap.baseurl")
    String redcap_url;

   @Inject
    public biDETResource(Vertx vertx) {


        final Config config = ConfigProvider.getConfig();
        //
        this.vertx = vertx;
        this.client = WebClient.create(vertx,new WebClientOptions().setFollowRedirects(true).setSsl(true).setTrustAll(true));
        //redcap_url = config.getValue("redcap.baseurl", String.class);

        //System.out.println("redcap baseurl: " + redcap_url);
        //Log.infov("redcap baseurl: {0}", redcap_url);
        //fcRes = new fhirclientResource();

        /*
        if (detService == null) {
            detService =
                    RestClientBuilder.newBuilder()
                            .followRedirects(true)
                            .baseUri(URI.create(redcap_url))
                            .property("io.quarkus.rest.client.connection-pool-size", 50)
                            .property(QuarkusRestClientProperties.NAME, "bi-redcap-cli-group")
                            .property(QuarkusRestClientProperties.SHARED, true)
                            .build(biDETService.class);
        }
        */
    }

    @Inject HeaderCollectingInterceptor headerCollectingInterceptor;

    @Inject JsonWebToken jwt;

    static final ObjectMapper mapper = new ObjectMapper();

    //
    //

    /*
     *
     *
     */
    private Uni<JsonObject> callDetAnother(final boolean call, final String url, final MultiMap forms) {
        //if (this.client==null) {
        //    this.client = WebClient.create(vertx,new WebClientOptions().setFollowRedirects(false).setSsl(true).setTrustAll(true));
        //}
        JsonObject resp = new JsonObject().put("endpoint", url);
        resp.put("hasbeencalled",true);
        if (call && StringUtils.isNotBlank(url)) {
            System.out.println("Another URL Before Call:"+url);
            return this.client
                .postAbs(url)
                .as(BodyCodec.string())
                .putHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                .sendForm(forms)
                .onItem().transform(r -> {
                    System.out.println("Another URL:"+url);
                    return new JsonObject().put("endpoint", url)
                    .put("hasbeencalled",true)
                    .put("response_status_code", r.statusCode())
                    .put("response_status_message", r.statusMessage())
                    .put("response_body", r.bodyAsString());
                });
        } else {
            resp.put("hasbeencalled", false);
        }
        return Uni.createFrom().item(resp);
    }

    /*
     *
     *
     */
    private Uni<JsonObject> callDetEpicPatReg(final boolean call, final String url,
                                        final MultiMap forms,
                                        final String env) {
        //if (this.client==null) {
        //    this.client = WebClient.create(vertx,new WebClientOptions().setFollowRedirects(false).setSsl(true).setTrustAll(true));
        //}
        JsonObject resp = new JsonObject().put("endpoint", url);
        resp.put("hasbeencalled",true);
        resp.put("EpicENV",env);
        if (call && StringUtils.isNotBlank(url)) {
            System.out.println("Epic PAT REG URL Before Call:"+url);
            return this.client.postAbs(url)
                        .as(BodyCodec.string())
                        .putHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                        .sendForm(forms)
                        .onItem().transform(r -> {
                             System.out.println("Epic PAT REG URL:"+url);
                            return new JsonObject().put("endpoint", url)
                            .put("hasbeencalled",true)
                            .put("response_status_code", r.statusCode())
                            .put("response_status_message", r.statusMessage())
                            .put("response_body", r.bodyAsString());
                        });
        } else {
            resp.put("hasbeencalled", false);
        }
        return Uni.createFrom().item(resp);
    }

    /*
     *
     *
     */
    @POST
    @Path("epic/patreg")
    @PermitAll
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Uni<JsonObject> epicPatreg(
            @Context UriInfo uriInfo,
            @Context HttpHeaders headers,
            @Context SecurityContext ctx,
            @QueryParam("redcaptoken") String token,
            @QueryParam("env") String env,
            @QueryParam("anodet") String anotherEndPoint,
            MultivaluedMap<String, String> requestParams
            //@RestForm @PartType(MediaType.APPLICATION_FORM_URLENCODED) MultivaluedMap<String, String> requestParams
            ) throws Exception {

        //Log.infov("instrument: {0}", instrument);
        //Log.infov("record: {0}", record);
        //Log.infov("redcap_event_name: {0}", redcap_event_name);
        //Log.infov("redcap_data_access_group: {0}", redcap_data_access_group);
        //Log.infov("landing_page_intake_complete: {0}", landing_page_intake_complete);
        //Log.infov("patient_registration_form_complete: {0}", patient_registration_form_complete);
        final String INSTR1= "landing_page_intake_complete";
        final String INSTR2= "patient_registration_form_complete";
        boolean detBeenCalledEpic = false;
        final boolean detBeenCalledAnother = StringUtils.isNotBlank(anotherEndPoint) ? true : false;
        String rcUrl = "";
        String prjUrl = "";
        String prjId = "";
        String username = "";
        String instr = "";
        String event = "";
        String record = "";
        String formCompleteName = "";
        // 0: Incomplete 1: Unverified 2: Complete
        String formCompleteStatus = "";

        int detEpicRespCode;
        String detEpicResp="";
        //if (uri!=null) {
        //    detEpicUri=String.format("%sdet/moms/impactt/patreg?redcaptoken=%s&env=%s",uri.getBaseUri().toString(),token,env);
        //}
        final String detEpicUri= uri==null ? "": String.format("%sdet/moms/impactt/patreg?redcaptoken=%s&env=%s",uri.getBaseUri().toString(),token,env);
        JsonObject rcJson = new JsonObject();

        // ArrayList<Uni<JsonObject>> itemUnis = new ArrayList<Uni<JsonObject>>();


        MultiMap fw_headers = MultiMap.caseInsensitiveMultiMap();
        if (headers!=null) {
            for (String header : headers.getRequestHeaders().keySet()) {
                fw_headers.set(header,headers.getRequestHeaders().getFirst(header));
                System.out.println("Header:"+header+"Value:"+headers.getRequestHeaders().getFirst(header));
            }
        }
        if (requestParams!=null) {
            if (requestParams.containsKey("redcap_url")) {
                rcUrl = String.valueOf(requestParams.getFirst("redcap_url"));
            }
            if (requestParams.containsKey("project_url")) {
                prjUrl = String.valueOf(requestParams.getFirst("project_url"));
            }
            if (requestParams.containsKey("project_id")) {
                prjId = String.valueOf(requestParams.getFirst("project_id"));
            }
            if (requestParams.containsKey("username")) {
                username = String.valueOf(requestParams.getFirst("username"));
            }
            if (requestParams.containsKey("instrument")) {
                instr = String.valueOf(requestParams.getFirst("instrument"));
            }
            if (requestParams.containsKey("redcap_event_name")) {
                event = String.valueOf(requestParams.getFirst("redcap_event_name"));
            }
            if (requestParams.containsKey("record")) {
                record = String.valueOf(requestParams.getFirst("record"));
            }
            MultiMap form = MultiMap.caseInsensitiveMultiMap();
            for (String key : requestParams.keySet()) {
                form.set(key,String.valueOf(requestParams.getFirst(key)));
                System.out.println("FormData - Key:"+key+" Value:"+String.valueOf(requestParams.getFirst(key)));
                if (StringUtils.endsWithIgnoreCase(key,"_complete")) {
                    formCompleteName = key;
                    final String value = String.valueOf(requestParams.getFirst(key));
                    if (StringUtils.equalsIgnoreCase(value, "0")) {
                        formCompleteStatus="incomplete";
                    } else if (StringUtils.equalsIgnoreCase(value, "1")) {
                        formCompleteStatus="unverified";
                    } else if (StringUtils.equalsIgnoreCase(value, "2")) {
                        formCompleteStatus="complete";
                    }
                }
            }
            rcJson.put("redcap_url", rcUrl).put("project_url", prjUrl).put("project_id", prjId).put("username", username).put("instrument", instr).put("redcap_event_name", event).put("record", record).put("Form", formCompleteName).put("FormStatus", formCompleteStatus);

            if (requestParams.containsKey(INSTR1) || requestParams.containsKey(INSTR2) ) {
                final String landingStatus = String.valueOf(requestParams.getFirst(INSTR1));
                final String patRegStatus = String.valueOf(requestParams.getFirst(INSTR2));
                final Optional<Integer> ilps = wdUtils.str2Int(landingStatus);
                final Optional<Integer> iprs = wdUtils.str2Int(patRegStatus);
                if ( (ilps.isPresent() && ilps.get() == 2 ) || (iprs.isPresent() && iprs.get() == 2 )) {
                    detBeenCalledEpic = true;
                    //System.out.println("EpicPatReg URL:"+detEpicUri+" env:"+env);
                    //Uni<JsonObject> uni = callDetEpicPatReg(detBeenCalledEpic, detEpicUri,form, env);
                    //itemUnis.add(uni);
                }
            }
            //if (StringUtils.isNotBlank(anotherEndPoint)) {
                //detBeenCalledAnother = true;
                //System.out.println("Another URL:"+anotherEndPoint);
                //Uni<JsonObject> uni = callDetAnother(detBeenCalledAnother, anotherEndPoint, form);
                //itemUnis.add(uni);
            //}
            if (detBeenCalledAnother && detBeenCalledEpic) {
                return callDetEpicPatReg(detBeenCalledEpic, detEpicUri, form, env)
                    .chain(joepic -> callDetAnother(detBeenCalledAnother, anotherEndPoint,form)
                            .onItem()
                            .transform(joano ->
                                new JsonObject().put("redcap",rcJson).put("det",new JsonArray().add(joepic).add(joano))));
            } else if (detBeenCalledEpic) {
                return callDetEpicPatReg(detBeenCalledEpic, detEpicUri, form, env)
                        .onItem()
                        .transform(jo ->
                            new JsonObject().put("redcap",rcJson).put("det",new JsonArray().add(jo)));

            } else if (detBeenCalledAnother) {
                return callDetAnother(detBeenCalledAnother, anotherEndPoint, form)
                        .onItem()
                        .transform(jo ->
                            new JsonObject().put("redcap",rcJson).put("det",new JsonArray().add(jo)));
            }

            // Requests to the catalog in parallel, followed by a request to the delivery service, and a final payload is assembled
            /*
            Uni<JsonObject>  uniRC = Uni.createFrom().item(rcJson);
            return Uni.combine().all().unis(itemUnis)
              .combinedWith(JsonArray::new)
              chain.(array -> uniRC.onItem().transform(rcUni-> new JsonObject()
                            .put("redcap",rcUni)
                            .put("det",array)));
            */
            // Combine the result of our 2 Unis in a tuple and subscribe to it
            /*
            return Uni.combine().all()
                    .unis(callDetEpicPatReg(detBeenCalledEpic, detEpicUri,form, env), callDetAnother(detBeenCalledAnother, anotherEndPoint,form))
                    .combinedWith(listOfResponses -> {
                            new JsonObject().put("redcap",rcJson).put("det",listOfResponses);
                            //.put("det",new JsonArray().add(listOfResponses.get(0)).add(listOfResponses.get(1));

                    });
            */
        }
        return Uni.createFrom().item(new JsonObject().put("redcap",rcJson));
            /*
                    return client.post(anotherEndPoint).putHeader("content-type", "multipart/form-data")
                    .sendForm(form)
                    .map(anoResp -> {
                        JsonObject detAnotherJson = new JsonObject().put("endpoint", anotherEndPoint);
                        detEpicJson.put("hasbeencalled",true);
                        detAnotherJson.put("response_code", anoResp.statusCode());
                        detAnotherJson.put("response_body", anoResp.bodyAsString());
                        respArray.add(detAnotherJson);
                        resp.put("det",respArray);
                        return resp;
                    });

            return client.post(detEpicUri).putHeader("content-type", "multipart/form-data")
            .sendForm(form)
            .map(epicResp -> {
                JsonObject resp = new JsonObject().put("redcap",rcJson);
                JsonObject detEpicJson = new JsonObject().put("endpoint",detEpicUri);
                detEpicJson.put("env",env);
                detEpicJson.put("hasbeencalled", true);
                detEpicJson.put("response_code", epicResp.statusCode());
                detEpicJson.put("response_body", epicResp.bodyAsString());
                JsonArray respArray = new JsonArray().add(detEpicJson);
                if (StringUtils.isNotBlank(anotherEndPoint)) {
                } else {
                    JsonObject detAnotherJson = new JsonObject().put("endpoint", "");
                    detEpicJson.put("hasbeencalled",false);
                    detAnotherJson.put("response_code", "");
                    detAnotherJson.put("response_body", "");
                    respArray.add(detAnotherJson);
                    resp.put("det",respArray);
                    return resp;
                }
            });
        } else {
            final JsonObject jo = new JsonObject().put("redcap","");
            return Uni.createFrom().item(jo);
        }
        // construct a JSON response like this:
        // {
                    */
        /*
          "redcap": {
            "projectid": "",
            "username": "",
            "record": "",
          },
          "det": [
            {
              "endpoint": "",
              "response_code": "",
              "response_body": ""
            },
            {
              "endpoint": "",
              "response_code": "",
              "response_body": ""
            }
          ]
        }
        boolean env_prod = false;
        JsonObject rcJson = new JsonObject().put("redcap_url", rcUrl).put("project_url", prjUrl).put("project_id", prjId).put("username", username).put("instrument", instr).put("redcap_event_name", event).put("record", record).put("Form", formCompleteName).put("FormStatus", formCompleteStatus);

        JsonObject detEpicJson = new JsonObject().put("endpoint", detEpicUri);
        detEpicJson.put("env",env);
        detEpicJson.put("hasbeencalled",true);
        detEpicJson.put("response_code",200);
        detEpicJson.put("response_body","");
        JsonObject detAnotherJson = new JsonObject().put("endpoint", anotherEndPoint);
        detEpicJson.put("hasbeencalled",true);
        detAnotherJson.put("response_code",200);
        detAnotherJson.put("response_body","");
        JsonArray respArray = new JsonArray().add(detEpicJson);
        respArray.add(detAnotherJson);
        JsonObject resp = new JsonObject().put("redcap",rcJson);
        resp.put("det",respArray);
        Uni<JsonObject> respU = Uni.createFrom().item(resp);

        return respU;
        */
        /*
        for (String key : requestParams.keySet()) {
            for (String value : requestParams.get(key)) {
                Log.info("key:" + key + "  :  value:" + value);
                System.out.println("key:" + key + "  :  value:" + value);

            }
            //if (key.startsWith(COLUMN_TAG)) {
                Log.info("key - substr: " + key);
                System.out.println("key - substr: " + key);


                columnNames.add(key);
            //}
        }


        StringBuilder whereClause = new StringBuilder();

        for (String value : columnNames) {
            Log.info("Column Name: " + value);
            Log.info("Got this operator: " + requestParams.containsKey(value));
            Log.info("Got this operator - value: " + requestParams.getFirst(value));
            Log.info("Got this column: " + requestParams.containsKey(value));
            Log.info("Got this column - value: " + requestParams.get(value));

            try {
                String operatorValue = requestParams.getFirst(value);
                String operatorProcessValue;
            } catch (Exception ex) {
                Log.error("OperatorException: " + ex.getMessage(), ex);
                throw new Exception(ex);
            }

        }

        Log.info("Value whereClause: " + whereClause);

        */

        //Log.infov("token: {0}", token);
        //Log.infov("project_id: {0}", project_id);
        //Log.infov("username: {0}", username);
        //Log.infov("instrument: {0}", instrument);
        //Log.infov("record: {0}", record);
        //Log.infov("redcap_event_name: {0}", redcap_event_name);
        //Log.infov("redcap_data_access_group: {0}", redcap_data_access_group);
        //Log.infov("landing_page_intake_complete: {0}", landing_page_intake_complete);
        //Log.infov("patient_registration_form_complete: {0}", patient_registration_form_complete);
        //Log.infov("redcap_url: {0}", redcap_url);
        //Log.infov("project_url: {0}", project_url);

        // String strResp = "";
        //construct a JSON query like {"redcap": {"project": {"projectid": "","username": "", "record": "", "status"}}
    }


    @GET
    @Path("moms/impactt/patreg")
    // @RolesAllowed({"bi-dev", "bi-admin", "bi-client", "bidev", "biadmin", "biclient",
    // "bmicclient"})
     @PermitAll
    // @Produces(MediaType.APPLICATION_JSON)
    // @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Uni<String> detMomsImpacttGet(
            @Context SecurityContext ctx,
            //        String token,
            @QueryParam("redcaptoken") String token,
            @FormParam("project_id") String project_id,
            @FormParam("username") String username,
            @FormParam("instrument") String instrument,
            @FormParam("record") String record,
            @FormParam("redcap_event_name") String redcap_event_name,
            @FormParam("redcap_data_access_group") String redcap_data_access_group,
            @FormParam("landing_page_intake_complete") String landing_page_intake_complete,
            @FormParam("patient_registration_form_complete")
                    String patient_registration_form_complete,
            @FormParam("redcap_url") String redcapurl,
            @FormParam("project_url") String project_url)
            throws Exception {

        ArrayList<String> array;

        //Log.infov("token: {0}", token);
        //Log.infov("project_id: {0}", project_id);
        //Log.infov("username: {0}", username);
        //Log.infov("instrument: {0}", instrument);
        //Log.infov("record: {0}", record);
        //Log.infov("redcap_event_name: {0}", redcap_event_name);
        //Log.infov("redcap_data_access_group: {0}", redcap_data_access_group);
        //Log.infov("landing_page_intake_complete: {0}", landing_page_intake_complete);
        //Log.infov("patient_registration_form_complete: {0}", patient_registration_form_complete);
        //Log.infov("redcap_url: {0}", redcap_url);
        //Log.infov("project_url: {0}", project_url);

        boolean env_prod = false;

        return Uni.createFrom().item("Complted");
    }

    @POST
    @Path("moms/impactt/patreg")
    // @RolesAllowed({"bi-dev", "bi-admin", "bi-client", "bidev", "biadmin", "biclient",
    // "bmicclient"})
     @PermitAll
    // @Produces(MediaType.APPLICATION_JSON)
    // @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Uni<String> detMomsImpacttPost(
            @Context SecurityContext ctx,
            //        String token,
            @QueryParam("redcaptoken") String token,
            @QueryParam("env") String env,
            @QueryParam("anodet") String anotherEndPoint,
            @FormParam("project_id") String project_id,
            @FormParam("username") String username,
            @FormParam("instrument") String instrument,
            @FormParam("record") String record,
            @FormParam("redcap_event_name") String redcap_event_name,
            @FormParam("redcap_data_access_group") String redcap_data_access_group,
            @FormParam("landing_page_intake_complete") String landing_page_intake_complete,
            @FormParam("patient_registration_form_complete")
                    String patient_registration_form_complete,
            @FormParam("redcap_url") String redcapurl,
            @FormParam("project_url") String project_url)
            throws Exception {

        ArrayList<String> array;

        //Log.infov("token: {0}", token);
        //Log.infov("project_id: {0}", project_id);
        //Log.infov("username: {0}", username);
        //Log.infov("instrument: {0}", instrument);
        //Log.infov("record: {0}", record);
        //Log.infov("redcap_event_name: {0}", redcap_event_name);
        //Log.infov("redcap_data_access_group: {0}", redcap_data_access_group);
        //Log.infov("landing_page_intake_complete: {0}", landing_page_intake_complete);
        //Log.infov("patient_registration_form_complete: {0}", patient_registration_form_complete);
        //Log.infov("redcap_url: {0}", redcap_url);
        //Log.infov("project_url: {0}", project_url);

        boolean env_prod = false;
        // String strResp = "";
        Uni<String> strResp = Uni.createFrom().item("Ok");

        try {
            // if (project_id == "58948") {


            if (project_id != null && !project_id.trim().isEmpty() ) {
                if (Integer.parseInt(project_id) == 58948) {
                    env_prod = true;
                    Log.infov("The Epic environment access request from REDCap: project_id: {0} - record_id: {1} is : {2}",
                            project_id, record, env);
                }
            } else {
                Log.errorv("The dataset came from REDCap: project_id: {0} - record_id: {1} is  empty: {2}",
                            project_id, record, formData.toString());
                return strResp;
            }

            if (StringUtils.isNotBlank(env)) {
                if (StringUtils.equalsIgnoreCase(env,"prod")) {
                    Log.infov("The Epic environment access request from REDCap: project_id: {0} - record_id: {1} is : {2}",
                            project_id, record, env);
                    env_prod = true;
                }
            } else {
                Log.warnv("The Epic environment access request from REDCap: project_id: {0} - record_id: {1} is blank, switch to the Epic TST!",
                            project_id, record);
            }

            formData.token = token;
            formData.content = "record";
            formData.action= "export";
            formData.format = "json";
            formData.type = "flat";
            ArrayList<String> arrayRecords = new ArrayList<String>();
            arrayRecords.add(record);
            //final String recordIDs = arrayRecords.toString();
            final String recordIDs = arrayRecords.stream().map(i -> i.toString()).collect(Collectors.joining(","));
            //formData.records = record;
            formData.records = recordIDs;;
            formData.csvDelimiter = "";
            formData.rawOrLabel = "label";
            formData.rawOrLabelHeaders = "label";
            formData.exportCheckboxLabel = "false";
            formData.exportSurveyFields = "false";
            formData.exportDataAccessGroups = "false";
            formData.returnFormat = "json";

            // Pat Lookup
            if (landing_page_intake_complete != null
                    && StringUtils.equalsIgnoreCase(landing_page_intake_complete, "2")) {
                // Only If the landing page intake is completed
                // if (StringUtils.equalsIgnoreCase(landing_page_intake_complete, "2")) {
                ArrayList<String> arrayFields = new ArrayList<String>();
                arrayFields.add("record_id");
                arrayFields.add("user_type");
                arrayFields.add("provider_interest");
                arrayFields.add("patient_name");
                arrayFields.add("patient_phonenumber");
                arrayFields.add("patient_dob");
                arrayFields.add("patient_zip");
                arrayFields.add("pt_selfrefer_name");
                arrayFields.add("pt_selfrefer_phone_number");
                arrayFields.add("pt_selfrefer_dob");
                arrayFields.add("pt_selfrefer_zip");
                arrayFields.add("landing_page_intake_complete");
                arrayFields.add("patient_registration_form_complete");
                arrayFields.add("admin_patreg_match_status");
                arrayFields.add("patreg_muscmrn");
                //final String fieldNames = arrayFields.toString();
                final String fieldNames = arrayFields.stream().map(i -> i.toString()).collect(Collectors.joining(","));
                formData.fields = fieldNames;

                ArrayList<String> arrayForms = new ArrayList<String>();
                arrayForms.add(instrument);
                //formData.forms = arrayForms.toString();
                formData.forms = arrayForms.stream().map(i -> i.toString()).collect(Collectors.joining(","));

                ArrayList<String> arrayEvents = new ArrayList<String>();
                arrayEvents.add(redcap_event_name);
                //formData.events = arrayEvents.toString();
                formData.events = arrayEvents.stream().map(i -> i.toString()).collect(Collectors.joining(","));
                Log.infov(
                        "redcap x-www-form-urlencoded - {0}",
                                        formData);
                Optional<modelLandingPageIntakeRecord> first = null;

                //Optional<modelLandingPageIntakeRecord> first =

                final boolean fbEnv = env_prod;
                final String sbToken = token;
                final String sbPid= project_id;
                final String sbRec = record;
                detService.exportLandingRec(
                                        formData.token,
                                        formData.content,
                                        formData.action,
                                        formData.format,
                                        formData.type,
                                        formData.csvDelimiter,
                                        formData.records,
                                        formData.fields,
                                        formData.events,
                                        formData.rawOrLabel,
                                        formData.rawOrLabelHeaders,
                                        formData.exportCheckboxLabel,
                                        formData.exportSurveyFields,
                                        formData.exportDataAccessGroups,
                                        formData.returnFormat)
                        //.runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                                .onFailure()
                                .retry()
                                .withBackOff(Duration.ofSeconds(1), Duration.ofSeconds(3)).atMost(3)
                        .subscribe()
                        .with(resp -> {
                            for (modelLandingPageIntakeRecord landingRec: resp) {
                                if ( landingRec != null ) {
                                    // DO NOT TOSTRING IT, will caused
                                    // 2022-04-01 13:54:45 ERROR
                                    // [or.jb.re.re.co.co.AbstractResteasyReactiveContext]
                                    // (executor-thread-0) Request failed:
                                    // java.lang.IncompatibleClassChangeError: Class
                                    // [B does not implement the requested interface java.lang.CharSequence
                                    // strResp = landingRec.toString();
                                    // Log.infov("pt_selfrefer_name: {0}", landingRec.getPt_selfrefer_name());

                                    if (StringUtils.isBlank(landingRec.getPatreg_muscmrn())) {
                                        final modelLandingPageIntakeRecord deepCopy =
                                                modelLandingPageIntakeRecord
                                                        .builder()
                                                        .user_type(landingRec.getUser_type())
                                                        .provider_interest(landingRec.getProvider_interest())
                                                        .patient_name(landingRec.getPatient_name())
                                                        .patient_phonenumber(landingRec.getPatient_phonenumber())
                                                        .patient_dob(landingRec.getPatient_dob())
                                                        .patient_zip(landingRec.getPatient_zip())
                                                        .pt_selfrefer_name(landingRec.getPt_selfrefer_name())
                                                        .pt_selfrefer_phone_number(
                                                                landingRec.getPt_selfrefer_phone_number())
                                                        .pt_selfrefer_dob(landingRec.getPt_selfrefer_dob())
                                                        .pt_selfrefer_zip(landingRec.getPt_selfrefer_zip())
                                                        .landing_page_intake_complete(
                                                                landingRec.getLanding_page_intake_complete())
                                                        .patient_registration_form_complete(
                                                                landingRec.getPatient_registration_form_complete())
                                                        .admin_patreg_match_status(
                                                                landingRec.getAdmin_patreg_match_status())
                                                        .patreg_muscmrn(landingRec.getPatreg_muscmrn())
                                                        .build();
                                        PatLookupFeedbackFromLandingPage(
                                                        fbEnv, sbToken, sbPid, sbRec, deepCopy);
                                    } else {
                                        Log.warnv(
                                                "This record already has the mrn came from REDCap: project_id: {0}"
                                                        + " - record_id: {1} mrn: {2}",
                                                project_id, record, landingRec.getPatreg_muscmrn());
                                        Uni.createFrom().item(String.format("This record already has the mrn came from REDCap: project_id: %s - record_id: %s - mrn: %s",project_id, record, landingRec.getPatreg_muscmrn()));
                                    } // musc_mrn is null
                                } else {// if landing is null
                                        Log.warnv(
                                                "This record {0} from REDCap: project_id: {1} - returned a null dataset for {2}",
                                                project_id, record, formData);
                                        Uni.createFrom().item(String.format("This record %s from REDCap: project_id: %s - returned a null dataset for %s",project_id, record, formData));
                                }
                            } // for set loop
                        },
                        failure -> {
                            Log.errorv("The dataset came from REDCap: project_id: {0} - record_id: {1} is  empty: {2}, failure: {3}",
                                        project_id, record, formData.toString(), failure);
                            Uni.createFrom().failure(new IOException("REDCap API"));
                        }
                ); // with(resp -> {
            } // if (landing_page_intake_complete != null && StringUtils.equalsIgnoreCase(landing_page_intake_complete, "2"))
            else if (patient_registration_form_complete != null
                    && !StringUtils.equalsIgnoreCase(
                            patient_registration_form_complete,
                            "2")) { // triggered by the patient registration form
                ArrayList<String> arrayFields = new ArrayList<String>();
                arrayFields.add("record_id");
                arrayFields.add("patreg_creation_city");
                arrayFields.add("patreg_creation_dob");
                arrayFields.add("patreg_creation_email");
                arrayFields.add("patreg_creation_ethnicity");
                arrayFields.add("patreg_creation_insgroup");
                arrayFields.add("patreg_creation_insurancename");
                arrayFields.add("patreg_creation_language");
                arrayFields.add("patreg_creation_marital");
                arrayFields.add("patreg_creation_mobile");
                arrayFields.add("patreg_creation_mrn");
                arrayFields.add("patreg_creation_name");
                arrayFields.add("patreg_creation_race");
                arrayFields.add("patreg_sel_1");
                arrayFields.add("patreg_sel_2");
                arrayFields.add("patreg_sel_3");
                arrayFields.add("patreg_sel_new");
                arrayFields.add("patreg_match1_mrn");
                arrayFields.add("patreg_match2_mrn");
                arrayFields.add("patreg_match3_mrn");
                arrayFields.add("patreg_creation_state");
                arrayFields.add("patreg_creation_street");
                arrayFields.add("patreg_creation_veteran");
                arrayFields.add("patreg_creation_zip");
                arrayFields.add("patient_registration_form_complete");
                arrayFields.add("admin_patreg_match_status");
                arrayFields.add("patreg_muscmrn");
                //final String fieldNames = arrayFields.toString();
                final String fieldNames = arrayFields.stream().map(i -> i.toString()).collect(Collectors.joining(","));
                formData.fields = fieldNames;

                ArrayList<String> arrayForms = new ArrayList<String>();
                arrayForms.add(instrument);
                //formData.forms = arrayForms.toString();
                formData.forms = arrayForms.stream().map(i -> i.toString()).collect(Collectors.joining(","));

                ArrayList<String> arrayEvents = new ArrayList<String>();
                arrayEvents.add(redcap_event_name);
                //formData.events = arrayEvents.toString();
                formData.events = arrayEvents.stream().map(i -> i.toString()).collect(Collectors.joining(","));

                Log.infov(
                        "redcap x-www-form-urlencoded - {}",
                        formData);
                Optional<modelPatRegRecord> first =
                        detService
                                .exportPatregRec(
                                        formData.token,
                                        formData.content,
                                        formData.action,
                                        formData.format,
                                        formData.type,
                                        formData.csvDelimiter,
                                        formData.records,
                                        formData.fields,
                                        formData.events,
                                        formData.rawOrLabel,
                                        formData.rawOrLabelHeaders,
                                        formData.exportCheckboxLabel,
                                        formData.exportSurveyFields,
                                        formData.exportDataAccessGroups,
                                        formData.returnFormat)
                                .stream()
                                .findFirst();
                if (first.isPresent()) { // get the patient registration form data
                    modelPatRegRecord patregRec = first.get();
                    // DO NOT TOSTRING() IT, will caused
                    // 2022-04-01 13:54:45 ERROR
                    // [or.jb.re.re.co.co.AbstractResteasyReactiveContext]
                    // (executor-thread-0) Request failed:
                    // java.lang.IncompatibleClassChangeError: Class
                    // [B does not implement the requested interface java.lang.CharSequence
                    // strResp = landingRec.toString();
                    Log.infov("patreg musc mrn: {0}", patregRec.getPatreg_muscmrn());
                    Log.infov("patreg new patient: {0}", patregRec.toString());

                    strResp = Uni.createFrom().item(patregRec.toString());
                    final String crtNewPatSelection = patregRec.getPatreg_sel_new();
                    Log.infov("patreg new patient flag: {0}", crtNewPatSelection);

                    final String patSelChoice1 = patregRec.getPatreg_sel_1();
                    Log.infov("patreg patient choice flag - match 1: {0}", patSelChoice1);
                    final String patMRNChoice1 = patregRec.getPatreg_match1_mrn();
                    Log.infov("patreg patient choice mrn - match 1: {0}", patMRNChoice1);

                    final String patSelChoice2 = patregRec.getPatreg_sel_2();
                    Log.infov("patreg patient choice flag - match 2: {0}", patSelChoice2);
                    final String patMRNChoice2 = patregRec.getPatreg_match2_mrn();
                    Log.infov("patreg patient choice mrn - match 2: {0}", patMRNChoice2);

                    final String patSelChoice3 = patregRec.getPatreg_sel_3();
                    Log.infov("patreg patient choice flag - match 3: {0}", patSelChoice3);
                    final String patMRNChoice3 = patregRec.getPatreg_match3_mrn();
                    Log.infov("patreg patient choice mrn - match 3: {0}", patMRNChoice3);

                    final String patSelChoiceNew = patregRec.getPatreg_sel_new();
                    Log.infov("patreg patient choice flag - match 3: {0}", patSelChoice3);
                    final String patMRNChoiceNew = patregRec.getPatreg_creation_mrn();
                    Log.infov("patreg patient choice mrn - match 3: {0}", patMRNChoice3);

                    if (StringUtils.equalsIgnoreCase(crtNewPatSelection, "Yes")) {
                        final String name = patregRec.getPatreg_creation_name();
                        Log.infov("patreg new patient name: {0}", name);
                        final String dob = patregRec.getPatreg_creation_dob();
                        final String zip = patregRec.getPatreg_creation_zip();
                        final String phone = patregRec.getPatreg_creation_mobile();
                        final String email = patregRec.getPatreg_creation_email();
                        final String sex = "female";
                        final String race = patregRec.getPatreg_creation_race();
                        final String ethnic = patregRec.getPatreg_creation_ethnicity();
                        final String insGrp = patregRec.getPatreg_creation_insgroup();
                        final String insName = patregRec.getPatreg_creation_insurancename();
                        final String lang = patregRec.getPatreg_creation_language();
                        final String marital = patregRec.getPatreg_creation_marital();
                        final String veteran = patregRec.getPatreg_creation_veteran();
                        final String street = patregRec.getPatreg_creation_street();
                        final String city = patregRec.getPatreg_creation_city();
                        final String state = patregRec.getPatreg_creation_state();
                        if (!StringUtils.isBlank(name)
                                && !StringUtils.isBlank(dob)
                                && !StringUtils.isBlank(zip)
                                && !StringUtils.isBlank(phone)
                                && !StringUtils.isBlank(marital)
                                && !StringUtils.isBlank(street)
                                && !StringUtils.isBlank(city)
                                && !StringUtils.isBlank(state)) {
                            FullNameParser parserPatCrt = new FullNameParser();
                            ParsedName nameParserPatCrt = parserPatCrt.parse(name);
                            if (nameParserPatCrt != null) {
                                final String lastPatCrt = nameParserPatCrt.getLastName();
                                final String firstPatCrt = nameParserPatCrt.getFirstName();
                                Log.infov("patreg new patient last name: {0}", lastPatCrt);
                                final String middlePatCrt =
                                        CollectionUtils.isEmpty(nameParserPatCrt.getMiddleNames())
                                                ? ""
                                                : nameParserPatCrt.getMiddleNames().get(0);
                                final LocalDateTime curr_ts = LocalDateTime.now();
                                final String client_ts = convertDateTimeToString(curr_ts);
                                final String client_netid = "TST7";

                                modelPatDemogr demo = new modelPatDemogr();
                                demo.setClient_ts(client_ts);
                                demo.setClient_deviceid("");
                                demo.setClient_netid(client_netid);
                                demo.setFirstname(firstPatCrt);
                                demo.setLastname(lastPatCrt);
                                demo.setMiddlename(middlePatCrt);
                                demo.setDob(dob);
                                demo.setSex(sex);
                                demo.setRace(race);
                                demo.setEthnic(ethnic);
                                demo.setAddress(street);
                                demo.setCity(city);
                                demo.setState(state);
                                demo.setZip(zip);
                                demo.setPhone(phone);
                                demo.setEmail(email);
                                demo.setCountry("US");
                                demo.setSsn("000-00-0000");
                                demo.setLanguage("");
                                final String uuid = UUID.randomUUID().toString().replace("-", "");
                                final String client_deviceid = "";
                                Log.infof(
                                        "TransactionID: %s | netid: %s | ClientDeviceID: %s |"
                                                + " Transaction Type: %s | Transaction Event: %s |"
                                                + " Transaction Status: %s",
                                        uuid,
                                        client_netid,
                                        client_deviceid,
                                        "PATLookUp/Begin",
                                        "Lookup patient before Create a new Patient",
                                        "Completion: transaction begin - query");
                                final String mrnCheck =
                                        PatLookupBeforeCrtPat(
                                                env_prod,
                                                client_netid,
                                                client_deviceid,
                                                client_ts,
                                                uuid,
                                                demo);
                                String strMrn = mrnCheck;
                                if (StringUtils.isBlank(mrnCheck)) {
                                    Log.infof(
                                            "TransactionID: %s | netid: %s | ClientDeviceID: %s |"
                                                + " Transaction Type: %s | Transaction Event: %s |"
                                                + " Transaction Status: %s",
                                            uuid,
                                            client_netid,
                                            client_deviceid,
                                            "PATLookUp/Result/PATCreation/Begin",
                                            "Returned None before create a new patient, the patient"
                                                    + " creation process is started!",
                                            "Completion: transaction begin - insert");
                                    crtPat(
                                            env_prod,
                                            client_netid,
                                            client_deviceid,
                                            client_ts,
                                            uuid,
                                            lastPatCrt,
                                            firstPatCrt,
                                            middlePatCrt,
                                            dob,
                                            street,
                                            city,
                                            state,
                                            zip,
                                            "female",
                                            "US",
                                            phone,
                                            email,
                                            "",
                                            "",
                                            "000-00-0000",
                                            0);
                                    Log.infof(
                                            "TransactionID: %s | netid: %s | ClientDeviceID: %s |"
                                                + " Transaction Type: %s | Transaction Event: %s |"
                                                + " Transaction Status: %s",
                                            uuid,
                                            client_netid,
                                            client_deviceid,
                                            "PATCreation/End/PATLookUp/Begin",
                                            "created a new patient, the patient"
                                                    + " creation process is done!",
                                            "Completion: transaction end - inserted | transaction"
                                                    + " begin - search");
                                    strMrn =
                                            PatLookupBeforeCrtPat(
                                                    env_prod,
                                                    client_netid,
                                                    client_deviceid,
                                                    client_ts,
                                                    uuid,
                                                    demo);
                                    Log.infof(
                                            "TransactionID: %s | netid: %s | ClientDeviceID: %s |"
                                                + " Transaction Type: %s | Transaction Event: %s |"
                                                + " Transaction Status: %s | Response: %s",
                                            uuid,
                                            client_netid,
                                            client_deviceid,
                                            "PATLookUp/Request",
                                            "Lookup Patient's MRN after created a new patient.",
                                            "Completion: transaction begin - query",
                                            strMrn);
                                }
                                if (!StringUtils.isBlank(strMrn)) {
                                    Log.infof(
                                            "TransactionID: %s | netid: %s | ClientDeviceID: %s |"
                                                + " Transaction Type: %s | Transaction Event: %s |"
                                                + " Transaction Status: %s | Response: %s",
                                            uuid,
                                            client_netid,
                                            client_deviceid,
                                            "PATLookUp/Result/REDCapPATMRNUpdate",
                                            "Got new patient's MRN, update it back to redcap's"
                                                    + " patient registration form.",
                                            "Completion: transaction begin - update",
                                            strMrn);
                                    patRegFeedback2RedcapWithMatchReturns(
                                            env_prod,
                                            token,
                                            project_id,
                                            record,
                                            "",
                                            "",
                                            "",
                                            "Yes",
                                            "",
                                            "",
                                            "",
                                            strMrn);
                                    Log.infof(
                                            "TransactionID: %s | netid: %s | ClientDeviceID: %s |"
                                                + " Transaction Type: %s | Transaction Event: %s |"
                                                + " Transaction Status: %s | Response: MRN: %s |"
                                                + " Record ID: %s",
                                            uuid,
                                            client_netid,
                                            client_deviceid,
                                            "REDCapPATMRNUpdate",
                                            "Got new patient's MRN, update it back to redcap's"
                                                    + " patient registration form.",
                                            "Completion: transaction end - updated",
                                            strMrn,
                                            record);
                                }
                            } else {
                                Log.error("the nameParserPatCrt has an parse name related issue.");
                            }
                        } else { // if (!StringUtils.isBlank(name)
                            Log.error("Some Demogr. fields are required.");
                            strResp = Uni.createFrom().item(String.format("Failed: required Demogr. fields are missing: %s", patregRec.toString()));
                        }
                    } else { // Confirmed a match from multiple matches
                        return patRegFeedback2RedcapWithMatchReturns(
                                env_prod,
                                token,
                                project_id,
                                record,
                                patSelChoice1,
                                patSelChoice2,
                                patSelChoice3,
                                patSelChoiceNew,
                                patMRNChoice1,
                                patMRNChoice2,
                                patMRNChoice3,
                                patMRNChoiceNew);
                    } // match types
                } // get the patient registration form data
            } // landing is completed or triggered by the patient registration form
        } catch (ValidationException ve) {
            // Deal with a 412 response
            Log.error("Failed to handle Validation", ve);
            strResp = Uni.createFrom().item(String.format("Failed to handle Validation: %s", ve.getMessage()));
        } catch (RedirectionException re) {
            // Deal with a 412 response
            Log.error("Failed to handle Redirection Exception", re);
            strResp = Uni.createFrom().item(String.format("Failed to handle Redirection Exception: %s", re.getMessage()));
        } catch (Exception e) {
            // Deal with other cases
            Log.error("Failed to handle Exception", e);
            strResp = Uni.createFrom().item(String.format("Failed to handle Redirection Exception: %s", e.getMessage()));
        }
        return strResp;
    }

    private final String PatLookupBeforeCrtPat(
            final boolean env_prod,
            final String client_netid,
            final String client_deviceid,
            final String client_ts,
            final String uuid,
            modelPatDemogr pat) {
        String strMRN = "";

        final String env = env_prod ? "prod" : "test";
        final String type = "full";
        try {
            Log.infof(
                    "TransactionID: %s | ENV: %s | netid: %s | ClientDeviceID: %s | Transaction"
                            + " Type: %s | Transaction Event: %s | Transaction Status: %s",
                    uuid,
                    env,
                    client_netid,
                    client_deviceid,
                    "PATLookUP/Request",
                    "PATLookUp before create a new patient to prevent the duplicates!",
                    "Completion: transaction begin - query");

            if (fcRes == null) {
                fcRes = new fhirclientResource();
            }
            biApiResp mrnResp = fcRes.patLookupByDemogr(env, type, pat);
            if (mrnResp != null) {
                strMRN = mrnResp.toString();
                final String typeResp = mrnResp.getType();
                if (typeResp.equalsIgnoreCase("UNIQUE")) {
                    modelPatLookupResult[] patList = toPatLookupResult(mrnResp.getMessage());
                    strMRN = patList[0].getMrn();
                    Log.infof(
                            "TransactionID: %s | ENV: %s | netid: %s | ClientDeviceID: %s |"
                                    + " Transaction Type: %s  | Transaction Event: %s | Transaction"
                                    + " Status: %s | Response: %s",
                            uuid,
                            env,
                            client_netid,
                            client_deviceid,
                            "PATLookUp/Result",
                            "Before create a new patient to prevent the duplicates!",
                            "Completion: transaction end - query response - One Return",
                            strMRN);
                } else if (typeResp.equalsIgnoreCase("NOTFOUND")) {
                    strMRN = "";
                    Log.infof(
                            "TransactionID: %s | ENV: %s | netid: %s | ClientDeviceID: %s |"
                                    + " Transaction Type: %s | Transaction Event: %s | Transaction"
                                    + " Status: %s | Response: %s",
                            uuid,
                            env,
                            client_netid,
                            client_deviceid,
                            "PATLookUp/Result",
                            "Before create a new patient to prevent the duplicates!",
                            "Completion: transaction end - query response - None Return",
                            strMRN);
                }
            }
        } catch (Exception e) {
            // Deal with other cases
            Log.errorv(
                    "ENV: {0} - Failed to handle patLookupByDemogr: {1}, error: {2}",
                    env, pat.toString(), e);
        }
        return strMRN;
    }

    private boolean crtPat(
            final boolean env_prod,
            final String client_netid,
            final String client_deviceid,
            final String client_ts,
            final String uuid,
            final String last,
            final String first,
            final String middle,
            final String dob,
            final String addr,
            final String city,
            final String state,
            final String zip,
            final String sex,
            final String country,
            final String phone,
            final String email,
            final String race,
            final String ethnic,
            final String ssn,
            int retry) {
        try {
            String env = "test";
            if (env_prod) {
                env = "prod";
            }

            modelPatDemogr demo = new modelPatDemogr();
            demo.setClient_ts(client_ts);
            demo.setClient_deviceid("");
            demo.setClient_netid(client_netid);
            demo.setFirstname(first);
            demo.setLastname(last);
            demo.setMiddlename(middle);
            demo.setDob(dob);
            demo.setSex(sex);
            demo.setRace(race);
            demo.setEthnic(ethnic);
            demo.setAddress(addr);
            demo.setCity(city);
            demo.setState(state);
            demo.setZip(zip);
            demo.setPhone(phone);
            demo.setEmail(email);
            demo.setCountry("US");
            demo.setSsn("000-00-0000");
            demo.setLanguage("");
            demo.setIdc_override("");
            String fullname =
                    last + ", " + first + (StringUtils.isBlank(middle) ? "" : " " + middle);
            if (retry == 0) {
                Log.infof(
                        "TransactionID: %s | ENV: %s | netid: %s | ClientDeviceID: %s |"
                                + " TransactionType: %s | Transaction Status: %s",
                        uuid,
                        env,
                        client_netid,
                        client_deviceid,
                        "PATCreation/Begin",
                        "Completion: " + fullname);

                if (fcRes == null) {
                    fcRes = new fhirclientResource();
                }
                fcRes.crtPAT(env, demo);
                Log.infof(
                        "TransactionID: %s | ENV: %s | netid: %s | ClientDeviceID: %s |"
                                + " TransactionType: %s | Transaction Status: %s",
                        uuid,
                        env,
                        client_netid,
                        client_deviceid,
                        "PATCreation/End",
                        "Completion: " + fullname);
            }
        } catch (Exception e) {
            // throw new RuntimeException(e);
            Log.error("Create Patient Failed!", e);
            e.printStackTrace();
        }
        return false;
    }

    private final Uni<String> PatLookupFeedbackFromLandingPage(
            final boolean env_prod,
            final String token,
            final String project_id,
            final String record_id,
            modelLandingPageIntakeRecord landingRec) {
        String strMRN = "";

        final modelPatDemogr demo = new modelPatDemogr();
        final String env = env_prod ? "prod" : "test";
        final String type = "basic";
        try {
            final String user_type = landingRec.getUser_type();
            final String provider_interest = landingRec.getProvider_interest();
            final String patName_patRef = landingRec.getPatient_name();
            final String patZip_patRef = landingRec.getPatient_zip();
            final String patDob_patRef = landingRec.getPatient_dob();
            final String patPhone_patRef = formatPhone(landingRec.getPatient_phonenumber());
            final String patName_self = landingRec.getPt_selfrefer_name();
            final String patZip_self = landingRec.getPt_selfrefer_zip();
            final String patDob_self = landingRec.getPt_selfrefer_dob();
            final String patPhone_self = formatPhone(landingRec.getPt_selfrefer_phone_number());
            final String patient_registration_form_complete =
                    landingRec.getPatient_registration_form_complete();
            final String patGender = "f";
            final String country = "US";
            final String client_netid = "TST7";
            final String client_deviceid = "";
            String patName = "";
            String patDob = "";
            String patPhone = "";
            String patZip = "";

            if (StringUtils.equalsIgnoreCase(user_type, "Patient")) {
                patName = patName_self;
                patZip = patZip_self;
                patDob = patDob_self;
                patPhone = patPhone_self;
            }
            if (StringUtils.equalsIgnoreCase(user_type, "Provider")
                    && StringUtils.containsIgnoreCase(provider_interest, "Patient Referral")) {
                patName = patName_patRef;
                patZip = patZip_patRef;
                patDob = patDob_patRef;
                patPhone = patPhone_patRef;
            }
            if (StringUtils.equalsIgnoreCase(patient_registration_form_complete, "2")) {
                Log.infov(
                        "ENV: {0} - \n"
                                + "project_id: {1}, \n"
                                + "record_id: {2}, \n"
                                + " - The Patient Registration Form is completed!",
                        env, project_id, record_id);
                return Uni.createFrom().item(strMRN);
            }
            final String fsPatName = patName;
            if (StringUtils.isBlank(patName)) {
                Log.errorv(
                        "ENV: {0} - Project ID: {1} -- Record ID: {2} - the survey respondent did not entry their name: {3}!",
                        env, project_id, record_id, landingRec.toString());
            } else {
                FullNameParser parser = new FullNameParser();
                ParsedName nameParser = parser.parse(patName);
                if (nameParser == null) {
                    Log.errorv(
                            "ENV: {0} - Project ID: {1} -- Record ID: {2} - Failure to parser patient name: {3}!",
                            env, project_id, record_id, patName);
                } else {
                    final String last = nameParser.getLastName();
                    final String first = nameParser.getFirstName();
                    final String middle =
                            CollectionUtils.isEmpty(nameParser.getMiddleNames())
                                    ? ""
                                    : nameParser.getMiddleNames().get(0);
                    Log.infov(
                            "ENV: {0} - "
                                    + "project_id: {1}, "
                                    + "record_id: {2}, "
                                    + "patname: {3}, "
                                    + "first: {4}, "
                                    + "last: {5}, "
                                    + "middle: {6} "
                                    + "dob: {7}, "
                                    + "zip: {8}, "
                                    + "phone: {9}"
                                    + "fcRes: {10}",
                            env,
                            project_id,
                            record_id,
                            patName,
                            first,
                            last,
                            middle,
                            patDob,
                            patZip,
                            patPhone,
                            fcRes);
                    final LocalDateTime curr_ts = LocalDateTime.now();
                    final String client_ts = convertDateTimeToString(curr_ts);
                    demo.setClient_ts(client_ts);
                    demo.setClient_netid(client_netid);
                    demo.setFirstname(first);
                    demo.setLastname(last);
                    demo.setMiddlename(middle);
                    demo.setDob(patDob);
                    demo.setSex(patGender);
                    demo.setZip(patZip);
                    demo.setPhone(patPhone);
                    demo.setCountry(country);
                    if (fcRes == null) {
                        fcRes = new fhirclientResource();
                    }
                    Uni<biApiResp> respPatLookup = fcRes.patLookupCoreByDemogr_Async(env, type, demo);

                    respPatLookup.onFailure().retry()
                        .withBackOff(Duration.ofSeconds(1), Duration.ofSeconds(3)).atMost(3)
                        .subscribe()
                        .with(mrnResp -> {
                            String return_code = String.format("PATIENT FOUND: %s %s %s | MRN is sending to: ",demo.getFirstname(), demo.getMiddlename(), demo.getLastname());
                            String uuid = UUID.randomUUID().toString().replace("-", "");
                            Log.infof("Patient Lookup return: %s", mrnResp== null ? "" : mrnResp.toString());
                            if (mrnResp == null) {
                                Log.errorv(
                                        "ENV: {0} - Project ID: {1} -- Record ID: {2} - Failure to look up : {3} in Epic!",
                                        env, project_id, record_id, demo.toString());
                            } else {
                                final String strMRNResp = mrnResp.toString();
                                final String typeResp = mrnResp.getType();
                                if (typeResp.equalsIgnoreCase("UNIQUE")) {
                                    return_code = String.format("PATIENT FOUND: %s %s %s | MRN is sending to: ",demo.getFirstname(), demo.getMiddlename(), demo.getLastname());
                                    Log.infof(
                                            "TransactionID: %s | ENV: %s | netid: %s | ClientDeviceID: %s |"
                                                    + " TransactionType: %s | Transaction Status: %s",
                                            uuid,
                                            env,
                                            client_netid,
                                            client_deviceid,
                                            "PATLookUp/Unique/Commit",
                                            "Completion: " + mrnResp.getMessage());
                                    modelPatLookupResult[] patList =
                                            toPatLookupResult(mrnResp.getMessage());
                                    patRegLandingFeedback2Redcap(
                                            env_prod,
                                            token,
                                            project_id,
                                            record_id,
                                            fsPatName,
                                            demo.getDob(),
                                            demo.getZip(),
                                            demo.getPhone(),
                                            patList);
                                } else if (typeResp.equalsIgnoreCase("DUPS")) {
                                    return_code = "PATIENT DUPLICATES: " + fsPatName;
                                    Log.infov(
                                            "TransactionID: {0} | ENV: {1} | netid: {2} | ClientDeviceID:"
                                                + " {3} | TransactionType: {4} | Transaction Status: {5}",
                                            uuid,
                                            env,
                                            client_netid,
                                            client_deviceid,
                                            "PATLookUp/NoPerfectMatch/WaitingforParticipantConfirms",
                                            "Compensation Log records: Multiple results returned from API");
                                    modelPatLookupResult[] patList =
                                            toPatLookupResult(mrnResp.getMessage());
                                    patRegLandingFeedback2Redcap(
                                            env_prod,
                                            token,
                                            project_id,
                                            record_id,
                                            fsPatName,
                                            demo.getDob(),
                                            demo.getZip(),
                                            demo.getPhone(),
                                            patList);
                                } else if (typeResp.equalsIgnoreCase("NOTFOUND")) {
                                    uuid = UUID.randomUUID().toString().replace("-", "");
                                    return_code =
                                            "PATIENT NOT FOUND,  NO RETURNS TO REDCAP... : " + fsPatName;
                                    Log.infof(
                                            "TransactionID: %s | ENV: %s | netid: %s | ClientDeviceID: %s |"
                                                    + " TransactionType: %s | Transaction Status: %s",
                                            uuid,
                                            env,
                                            client_netid,
                                            client_deviceid,
                                            "PATLookUp/NOTFOUND/INSERT/Completion",
                                            "Completion: " + demo.getPhone());
                                    patRegLandingFeedback2Redcap(
                                            env_prod,
                                            token,
                                            project_id,
                                            record_id,
                                            fsPatName,
                                            demo.getDob(),
                                            demo.getZip(),
                                            demo.getPhone(),
                                            null);
                                }
                            } // mrnResp is null
                        },
                        failure -> {
                            Log.errorv("The dataset came from Gateway for REDCap request: project_id: {0} - record_id: {1} : {2}, failure: {3}",
                                        project_id, record_id, demo.toString(), failure);
                            Uni.createFrom().failure(new IOException("Gateway API"));
                        });

                } //if (nameParser != null)
            } // if (StringUtils.isBlank(patName))
        } catch (Exception e) {
            // Deal with other cases
            Log.errorv(
                    "ENV: {0} - Failed to handle Name Parser: {1}, error: {2}",
                    env, landingRec.toString(), e);
        }
        return Uni.createFrom().item(strMRN);
    }

    private Uni<String> patRegLandingFeedback2Redcap(
            final boolean env_prod,
            final String token,
            final String project_id,
            final String record_id,
            final String patName_selfInput,
            final String patDob_selfInput,
            final String patZip_selfInput,
            final String patPhone_selfInput,
            final modelPatLookupResult[] patList) {
        Uni <String> respPush = Uni.createFrom().item("Failed");
        try {
            ObjectNode root = mapper.createObjectNode();

            final ObjectNode rec_data = mapper.createObjectNode();
            rec_data.put("record_id", record_id);
            rec_data.put("admin_patreg_match_status", 7);
            rec_data.put("patreg_creation_name", patName_selfInput);
            rec_data.put("patreg_creation_dob", patDob_selfInput);
            rec_data.put("patreg_creation_zip", patZip_selfInput);
            rec_data.put("patreg_creation_mobile", patPhone_selfInput);
            rec_data.put("patreg_match1_mrn", "");
            rec_data.put("patreg_match1_demogr", "");
            rec_data.put("patreg_match2_mrn", "");
            rec_data.put("patreg_match2_demogr", "");
            rec_data.put("patreg_match3_mrn", "");
            rec_data.put("patreg_match3_demogr", "");
            rec_data.put("patreg_muscmrn", "");
            if (patList != null) {
                if (patList.length == 1) { // unqiue
                    rec_data.put("patreg_sel_1", 1);
                    rec_data.put("patient_registration_form_complete", 2);
                    rec_data.put("admin_patreg_match_status", 1);
                } else { // muplitple
                    rec_data.put("admin_patreg_match_status", 2);
                    rec_data.put("patient_registration_form_complete", 1);
                }
                for (int i = 0; i < patList.length; i++) {
                    final modelPatLookupResult pat = patList[i];
                    if (i == 0) {
                        rec_data.put("patreg_muscmrn", pat.getMrn());
                    } else {
                        rec_data.put("patreg_muscmrn", "");
                    }
                    final String recName_mrn = String.format("patreg_match%s_mrn", i + 1);
                    final String recName_Demogr = String.format("patreg_match%s_demogr", i + 1);

                    // Log.infov("pat list: {0}", pat.toString());
                    // create a JSON object
                    rec_data.put(recName_mrn, pat.getMrn());
                    try (StringWriter stringWriter = new StringWriter()) {
                        // Converting stringWriter to bufferedWriter
                        BufferedWriter buffWriter = new BufferedWriter(stringWriter);
                        buffWriter.write("First Name: " + pat.getFirstname());
                        buffWriter.newLine(); // Using newLine() method
                        buffWriter.write("Last Name: " + pat.getLastname());
                        buffWriter.newLine(); // Using newLine() method
                        buffWriter.write("Middle Name: " + pat.getMiddlename());
                        buffWriter.newLine(); // Using newLine() method
                        buffWriter.write("DOB: " + pat.getDob());
                        buffWriter.newLine(); // Using newLine() method
                        buffWriter.write("Gender: " + pat.getSex());
                        buffWriter.newLine(); // Using newLine() method
                        buffWriter.write("Address: " + pat.getAddress());
                        buffWriter.newLine(); // Using newLine() method
                        buffWriter.write("City: " + pat.getCity());
                        buffWriter.newLine(); // Using newLine() method
                        buffWriter.write("State: " + pat.getState());
                        buffWriter.newLine(); // Using newLine() method
                        buffWriter.write("ZIP: " + pat.getZip());
                        buffWriter.newLine(); // Using newLine() method
                        buffWriter.write("Phone: " + pat.getPhone());
                        buffWriter.newLine(); // Using newLine() method
                        buffWriter.write("Email: " + pat.getEmail());
                        buffWriter.flush();
                        rec_data.put(recName_Demogr, stringWriter.toString());
                    }
                }
            } else { // no match
                rec_data.put("admin_patreg_match_status", 4);
                rec_data.put("patreg_sel_new", 1);
                rec_data.put("patient_registration_form_complete", 0);
            }
            ArrayNode dataArray = mapper.createArrayNode(); // your outer array
            dataArray.add(rec_data);

            // convert `ObjectNode` to pretty-print JSON
            // without pretty-print, use `user.toString()` method
            // final String json =
            //        mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataArray);
            final String json = dataArray.toString();
            Log.info(json);

            detService.patRegFeedback2Redcap(
                            token, "record", "json", "flat", "overwrite", json)
                                .onFailure()
                                .retry()
                                .withBackOff(Duration.ofSeconds(1), Duration.ofSeconds(3)).atMost(3)
                        .subscribe()
                        .with(resp -> {
                            Uni.createFrom().item(resp);
                        },
                        failure -> {
                            Log.errorv("The import record function failed: project_id: {0} - record_id: {1} : {2}, failure: {3}",
                                        project_id, record_id, rec_data.toString(), failure);
                            Uni.createFrom().failure(new IOException("REDCap API Import Record BOOM!"));
                        });

        } catch (Exception e) {
            // Deal with other cases
            Log.errorv(
                    "ENV: {0} - Failed to write back the patient lookup result! Erorr: {1}",
                    env_prod, e);
            e.printStackTrace();
        }
        return respPush;
    }

    private Uni<String> patRegFeedback2RedcapWithMatchReturns(
            final boolean env_prod,
            final String token,
            final String project_id,
            final String record_id,
            final String patSelChoice1,
            final String patSelChoice2,
            final String patSelChoice3,
            final String patSelChoiceNew,
            final String mrn1,
            final String mrn2,
            final String mrn3,
            final String mrnnew) {
        Uni<String> strResp = Uni.createFrom().item("patRegFeedback2RedcapWithMatchReturns: Init");
        try {
            ObjectNode root = mapper.createObjectNode();

            ObjectNode rec_data = mapper.createObjectNode();
            rec_data.put("record_id", record_id);
            rec_data.put("admin_patreg_match_status", 3);
            if (StringUtils.equalsIgnoreCase(patSelChoice1, "Yes")) {
                if (!StringUtils.isBlank(mrn1)) {
                    rec_data.put("patreg_muscmrn", mrn1);
                    rec_data.put("patient_registration_form_complete", 2);
                    rec_data.put("admin_patreg_match_status", 3);
                    rec_data.put(
                            "patreg_creation_note",
                            "'You selected the first patient record. The form has been"
                                    + " successful.");
                }
            } else if (StringUtils.equalsIgnoreCase(patSelChoice2, "Yes")) {
                if (!StringUtils.isBlank(mrn2)) {
                    rec_data.put("patreg_muscmrn", mrn2);
                    rec_data.put("patient_registration_form_complete", 2);
                    rec_data.put("admin_patreg_match_status", 3);
                    rec_data.put(
                            "patreg_creation_note",
                            "'You selected the second patient record. The form has been"
                                    + " successful.");
                }
            } else if (StringUtils.equalsIgnoreCase(patSelChoice3, "Yes")) {
                if (!StringUtils.isBlank(mrn3)) {
                    rec_data.put("patreg_muscmrn", mrn3);
                    rec_data.put("patient_registration_form_complete", 2);
                    rec_data.put("admin_patreg_match_status", 3);
                    rec_data.put(
                            "patreg_creation_note",
                            "'You selected the third patient record. The form has been"
                                    + " successful.");
                }
            } else if (StringUtils.equalsIgnoreCase(patSelChoiceNew, "Yes")) {
                if (!StringUtils.isBlank(mrnnew)) {
                    rec_data.put("patreg_muscmrn", mrnnew);
                    rec_data.put("patient_registration_form_complete", 2);
                    rec_data.put("admin_patreg_match_status", 5);
                    rec_data.put(
                            "patreg_creation_note",
                            "New Patient Registration Success. The form has been successful.");
                } else {
                    rec_data.put("admin_patreg_match_status", 6);
                    rec_data.put("patreg_creation_note", "Patient Registration Errors!");
                }
            }
            ArrayNode dataArray = mapper.createArrayNode(); // your outer array
            dataArray.add(rec_data);

            // convert `ObjectNode` to pretty-print JSON
            // without pretty-print, use `user.toString()` method
            // final String json =
            //        mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataArray);
            final String json = dataArray.toString();
            Log.info(json);

            return detService.patRegFeedback2Redcap(
                            token, "record", "json", "flat", "overwrite", json);

        } catch (Exception e) {
            // Deal with other cases
            Log.errorv(
                    "ENV: {0} - Failed to write back the patient lookup result! Erorr: {1}",
                    env_prod, e);
            strResp = Uni.createFrom().item(String.format("Epic ENV: %s - Failed to write back the patient lookup restult: %s - Epic ENV: %s", env_prod, e.getMessage()));
            //e.printStackTrace();
        }
        return strResp;
    }

    protected String convertDateTimeToString(LocalDateTime dt) {
        String strDate = null;
        //
        // Inbuilt format
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        // Format LocalDateTime
        strDate = dt.format(formatter);

        return strDate;
    }

    private final boolean hasJwt() {
        return jwt.getClaimNames() != null;
    }

    protected String convertGoogleTimestampToUTC(Timestamp ts) {

        final String fmt_utc = "yyyy-MM-dd'T'HH:mm:ss.fffZ";
        // ZoneId zoneId = ZoneId.systemDefault();
        ZoneId zoneId = ZoneId.of("America/New_York");

        LocalDateTime ldt =
                Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos())
                        .atZone(zoneId)
                        .toLocalDateTime();
        //
        // This one return a localdatetime in utc format like : 2022-02-11T17:24:00.404280Z.
        // ZonedDateTime utcDate = ldt.atZone(zoneId).withZoneSameInstant(ZoneOffset.UTC);
        // return utcDate.toString();
        //
        // return localtime in utc format
        // return ldt.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toString();
        Instant instant = ldt.atZone(zoneId).toInstant();
        // if you want to return a localtime in utc format, just return
        // ldt.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toString();
        return instant.toString();
    }

    private modelPatLookupResult[] toPatLookupResult(String s) {
        try {
            return mapper.readValue(s, modelPatLookupResult[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private final String formatPhone(final String source) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        String strPhone = "";
        try {
            if (StringUtils.isNotBlank(source)) {
                PhoneNumber NumberProto = phoneUtil.parse(source, "US");
                final boolean isValid = phoneUtil.isValidNumber(NumberProto);
                if (isValid) {
                    strPhone = Long.toString(NumberProto.getNationalNumber());
                }
            }
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
            // Deal with other cases
            Log.errorv(
                    "format phone: NumberParseException was thrown: - phone: {0}, error: {1}",
                    source, e);
        }
        return strPhone;
    }

}
