package edu.musc.bi.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.net.URI;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import edu.musc.bi.HeaderCollectingInterceptor;
import edu.musc.bi.parseusdl.DriverLicense;
import edu.musc.bi.utils.dingzitime;
import org.jboss.resteasy.reactive.client.api.QuarkusRestClientProperties;

import io.quarkus.logging.Log;
import io.quarkus.runtime.configuration.ProfileManager;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.UUID;
// import java.util.Set;
// import java.util.concurrent.CompletableFuture;
// import java.util.concurrent.CompletionException;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.CompletionStage;

//import javax.annotation.security.RolesAllowed;
// import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
// import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
// import io.smallrye.mutiny.infrastructure.Infrastructure;

@Path("/ext")
//@RolesAllowed({
//    "iodsc",
//    "moms",
//    "bi-dev",
//    "bi-admin",
//    "bi-client",
//    "bidev",
//    "biadmin",
//    "biclient",
//    "bmic-client"
//})
// @RequestScoped
@ApplicationScoped
public class fhirclientResource {

    private static dingzitime dtime = new dingzitime();
    private static final ObjectMapper mapper = new JsonMapper();
    private static String profile = ProfileManager.getActiveProfile();

    @Inject HeaderCollectingInterceptor headerCollectingInterceptor;

    // ExecutorService executor;

    @Inject
    @ConfigProperty(name = "bi-gateway.threadpool.size")
    int threadPoolSize;

    @Inject
    @ConfigProperty(name = "bi-gateway.baseurl")
    String baseurl_bigw;

    @Inject
    @ConfigProperty(name = "iodnasc-consent.baseurl.test")
    String test_consent_iodnasc;

    @Inject
    @ConfigProperty(name = "iodnasc-consent.baseurl.prod")
    String prod_consent_iodnasc;

    /*
     *
     */
    @Inject @RestClient fhirclientService fcService;

    public final String getBaseurl() {
        return baseurl_bigw;
    }

    public fhirclientResource() {
        final Config config = ConfigProvider.getConfig();
        if (fcService == null) {
            baseurl_bigw = config.getValue("bi-gateway.baseurl", String.class);
            fcService =
                    RestClientBuilder.newBuilder()
                            .baseUri(URI.create(baseurl_bigw))
                            .property("io.quarkus.rest.client.connection-pool-size", 50)
                            .property(QuarkusRestClientProperties.NAME, "bi-gw-client-group")
                            .property(QuarkusRestClientProperties.SHARED, true)
                            .build(fhirclientService.class);
        }
        Log.infof("IMPORTANT INFORMATION: The application is starting with profile `%s`! service baseurl: %s = service is: %s", profile, baseurl_bigw, fcService);
        /*
        Log.infof(
                "TransactionID: _%s_%s | TransactionType: %s | TransactionStatus: %s | fcService:"
                        + " %s ",
                profile,
                "fhirclientResource/Init",
                "Init",
                "Failed: url: " + baseurl_bigw,
                fcService);
                */
    }

    /*
     *
     */
    @Blocking
    // @NonBlocking
    @POST
    @Path("/core/pat")
    public biApiResp crtPAT(@QueryParam("env") String env, modelPatDemogr demo) {

        // fhirclientService fcService;
        // if (StringUtils.equalsIgnoreCase(env, "prod")) {
        //    fcService = fcServiceProd;
        // } else {
        //    fcService = fcServiceTest;
        // }
        // System.out.println(env);
        if (fcService == null) {
            return null;
        } else {
            // System.out.println(demo.toString());
            return fcService.crtPAT(env, demo);
            // fcService.crtPAT(env, demo).onFailure().retry().atMost(5);
        }
    }

    /*
     *
     */
    // @Blocking
    // @NonBlocking
    @POST
    @Path("/core/patlookup")
    public biApiResp patLookupByDemogr(
            @QueryParam("env") String env,
            @QueryParam("type") String type,
            final modelPatDemogr demo) {

        // fhirclientService fcService;
        // if (StringUtils.equalsIgnoreCase(env, "prod")) {
        //    fcService = fcServiceProd;
        // } else {
        //    fcService = fcServiceTest;
        // }
        System.out.println(env);
        if (fcService == null) {
            // System.out.println(env);
            return null;
        } else {
            // System.out.println(demo.toString());
            // return fcService.patLookupByDemogr(env, type, demo).onFailure().retry().atMost(5);
            return fcService.patLookupByDemogr(env, type, demo);
            // .stream().findFirst().get();
            // .onFailure().retry().atMost(10);
        }
    }
    //
    // @NonBlocking
    @POST
    @Path("/core/patlookup_async")
    public Uni<biApiResp> patLookupCoreByDemogr_Async(
            @QueryParam("env") String env,
            @QueryParam("type") String type,
            final modelPatDemogr demo) {

        // fhirclientService fcService;
        // if (StringUtils.equalsIgnoreCase(env, "prod")) {
        //    fcService = fcServiceProd;
        // } else {
        //    fcService = fcServiceTest;
        // }
        Log.infof(
                "TransactionID: _%s_%s_%s | TransactionType: %s | TransactionStatus: %s |"
                        + " fcService: %s ",
                profile,
                env,
                "patLookupCoreByDemogr_Async",
                "BeforeCall",
                "url: " + baseurl_bigw,
                fcService);
        if (fcService == null) {
            // System.out.println(env);
            Log.errorf(
                    "TransactionID: _%s_%s | TransactionType: %s | TransactionStatus: %s",
                    profile,
                    "patLookupCoreByDemogr_Async",
                    "fcServiceIsNul",
                    "Failed: \r\n\r\n\r\n" + demo.toString());
            return null;
        } else {
            // System.out.println(demo.toString());
            // return fcService.patLookupByDemogr(env, type, demo).onFailure().retry().atMost(5);
            return fcService.patLookupCoreByDemogr_Async(env, type, demo);
            // .stream().findFirst().get();
            // .onFailure().retry().atMost(10);
        }
    }

    /*
     *
     *
     */
    protected final boolean checkArrayfromString(final String jsonStr) {
        final String trimmed = StringUtils.trim(jsonStr);
        if (StringUtils.startsWith(trimmed, "[") && StringUtils.endsWith(trimmed, "]")) {
            return true;
        } else {
            return false;
        }
    }
    /*
     *
     *
     */
    private final mrnInOurDNASC toMRN(final String s) {
        try {
            if (checkArrayfromString(s)) {
                mrnInOurDNASC[] arr = mapper.readValue(s, mrnInOurDNASC[].class);
                if (arr != null || arr.length == 0) {
                    return arr[0];
                } else {
                    return null;
                }
            } else {
                return mapper.readValue(s, mrnInOurDNASC.class);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     *
     */
    @NonBlocking
    @POST
    @Path("/inourdnasc/mrn")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<biApiResp> patLookup_dnasc_Async(
            @QueryParam("env") String env, modelPatDemogr demo) {
        // fhirclientService fcService;
        // if (StringUtils.equalsIgnoreCase(env, "prod")) {
        //    fcService = fcServiceProd;
        // } else {
        //    fcService = fcServiceTest;
        // }
        if (fcService == null) {
            return Uni.createFrom().nullItem();
        } else {
            return fcService.patLookupDNASCByDemogr_Async(env, demo).onFailure().retry().atMost(3);
            // .stream().findFirst().get();
            // .onFailure().retry().atMost(10);
        }
    }

    /*
     *
     *
     */
    protected Uni<biApiResp> crtPat_Async(
            final String uuid,
            final String client_ts,
            final String client_deviceid,
            final String client_netid,
            final String env,
            final modelPatDemogr demo) {
        // fhirclientService fcService;
        // if (StringUtils.equalsIgnoreCase(env, "prod")) {
        //    fcService = fcServiceProd;
        // } else {
        //    fcService = fcServiceTest;
        // }
        // System.out.println(env);
        if (fcService == null) {
            return Uni.createFrom().nullItem();
        } else {
            return fcService.crtPAT_Async(env, demo);
            //            .onItem()
            //            .transformToUni( item-> fcService.patLookupByDemogr_Async(env, demo)
            //    ;
        }
    }

    /*
     *
     *
     */
    protected Uni<biApiResp> patLookup_mobileapp_Async(
            final String uuid,
            final String client_ts,
            final String client_deviceid,
            final String client_netid,
            final String env,
            final modelPatDemogr demo) {

        // fhirclientService fcService;
        // if (StringUtils.equalsIgnoreCase(env, "prod")) {
        //    fcService = fcServiceProd;
        // } else {
        //    fcService = fcServiceTest;
        // }
        // System.out.println(env);
        if (fcService == null) {
            return Uni.createFrom().nullItem();
        } else {
            return fcService
                    .patLookupDNASCByDemogr_Async(env, demo)
                    .flatMap(
                            mrnResp -> {
                                if (mrnResp == null) {
                                    Log.errorf(
                                            "TransactionID: _%s_%s | netid: %s | ClientDeviceID: %s"
                                                    + " | TransactionType: %s | TransactionStatus:"
                                                    + " %s",
                                            profile,
                                            uuid,
                                            client_netid,
                                            client_deviceid,
                                            "PATLookUp/Nul",
                                            "Failed: \r\n\r\n\r\n" + demo.getDob());
                                    return Uni.createFrom().nullItem();
                                } else {
                                    // existing user
                                    final biApiResp resp = new biApiResp();
                                    final String type = mrnResp.getType();
                                    if (type.equalsIgnoreCase("NOTFOUND")) {}

                                    resp.setType(type);
                                    resp.setMessage(demo.toString());
                                    return Uni.createFrom().item(resp);
                                }
                            });
        }
    }

    /*
     * https://stackoverflow.com/questions/69143226/reactive-pipeline-using-mutiny-operators
     * https://stackoverflow.com/questions/71410979/how-to-use-condition-and-multiple-steps-in-a-reactive-function
     * https://quarkus.io/guides/context-propagation
     */
    public Uni<biApiResp> patLookup_pdf417_SMS_Async(
            final String uuid,
            final boolean env_prod,
            final boolean crtIfNotExists,
            DriverLicense license,
            final String pdf417_orig,
            final String phone,
            final String email,
            final String race,
            final String ethnic,
            final String ssn,
            final String idcOR,
            final String client_ts,
            final String client_deviceid,
            final String client_netid) {
        // fhirclientService fcService;
        String env = "test";
        String baseurl_Consent = test_consent_iodnasc;
        if (env_prod) {
            env = "prod";
            baseurl_Consent = prod_consent_iodnasc;
        } // else {
        //    fcService = fcServiceTest;
        // }
        // System.out.println(env);
        if (fcService == null || license == null) {
            if (fcService==null) {
                Log.errorf(
                        "TransactionID: _%s_%s | netid: %s | ClientDeviceID: %s"
                                + " | TransactionType: %s | TransactionStatus:"
                                + " %s | %s",
                        profile,
                        uuid,
                        client_netid,
                        client_deviceid,
                        "PATLookUp/NonfcService",
                        "Failed: \r\n\r\n\r\n" + license.toJson(),
                        "Url: \r\n" + baseurl_bigw);
            }
            if (license==null) {
                Log.errorf(
                        "TransactionID: _%s_%s | netid: %s | ClientDeviceID: %s"
                                + " | TransactionType: %s | TransactionStatus:"
                                + " %s | %s",
                        profile,
                        uuid,
                        client_netid,
                        client_deviceid,
                        "PATLookUp/NonLicense",
                        "Failed: \r\n\r\n\r\n" + license.toJson(),
                        "Url: \r\n" + baseurl_bigw);
            }
            return Uni.createFrom().nullItem();
        } else {
            biApiResp resp = new biApiResp();
            resp.setType("NOTFOUND");
            resp.setMessage("{}");
            resp.setCode(200);
            try {
                final String last = license.getLastName();
                final String first = license.getFirstName();
                final String middle = license.getMiddleName();
                final String dob = dtime.convertDateToString(license.getDOB());
                final String addr = license.getAddress();
                final String city = license.getCity();
                final String state = license.getState();
                final String zip = license.getZipCode();
                final String sex = license.getSex();
                final String country = license.getCountry();
                final modelPatDemogr demo = new modelPatDemogr();
                demo.setClient_ts(client_ts);
                demo.setClient_deviceid(client_deviceid);
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
                demo.setCountry(country);
                demo.setRace("");
                demo.setSsn(ssn);
                demo.setLanguage("");
                demo.setIdc_override(idcOR);
                if (demo.getDob().equals("")) {
                    Log.errorf(
                            "TransactionID: _%s_%s | netid: %s | ClientDeviceID: %s"
                                    + " | TransactionType: %s | TransactionStatus:"
                                    + " %s",
                            profile,
                            uuid,
                            client_netid,
                            client_deviceid,
                            "PATLookUp/NoPerfectMatch/OneOfReturnsConfirmedByParticipant",
                            "Failed: \r\n\r\n\r\n" + demo.getLastname());
                    resp.setMessage(demo.toString());
                    return Uni.createFrom().item(resp);
                }

                Log.infof(
                        "TransactionID: _%s_%s | netid: %s |"
                                + " ClientDeviceID: %s | TransactionType:"
                                + " %s | TransactionStatus: %s",
                        profile,
                        uuid,
                        client_netid,
                        client_deviceid,
                        "PATLookUp/LookupAsync/",
                        "url: " + baseurl_bigw);

                fcService
                        .patLookupDNASCByDemogr_Async(env, demo)
                        .flatMap(
                                mrnResp -> {
                                    if (mrnResp == null) {
                                        Log.errorf(
                                                "TransactionID: _%s_%s | netid: %s |"
                                                    + " ClientDeviceID: %s | TransactionType: %s |"
                                                    + " TransactionStatus: %s",
                                                profile,
                                                uuid,
                                                client_netid,
                                                client_deviceid,
                                                "PATLookUp/Nul",
                                                "Failed: \r\n\r\n\r\n" + demo.getDob());
                                        resp.setMessage(demo.toString());
                                        return Uni.createFrom().item(resp);
                                    } else {
                                        // existing user
                                        final String type = mrnResp.getType();
                                        final String msg = mrnResp.getMessage();
                                        resp.setType(type);
                                        if (type.equalsIgnoreCase("UNIQUE")) {
                                            Log.infof(
                                                    "TransactionID: _%s_%s | netid: %s |"
                                                        + " ClientDeviceID: %s | TransactionType:"
                                                        + " %s | TransactionStatus: %s",
                                                    profile,
                                                    uuid,
                                                    client_netid,
                                                    client_deviceid,
                                                    "PATLookUp/PerfectMatch/",
                                                    "Completion: \r\n\r\n\r\n Type: "
                                                            + type
                                                            + "Message: \r\n\r\n\r\n"
                                                            + msg);
                                        } else if (type.equalsIgnoreCase("DUPS")) {
                                            final String uuidCrtPat =
                                                    UUID.randomUUID().toString().replace("-", "");
                                            Log.warnf(
                                                    "TransactionID: _%s_%s | netid: %s |"
                                                        + " ClientDeviceID: %s | TransactionType:"
                                                        + " %s | TransactionStatus: %s %s",
                                                    profile,
                                                    uuid,
                                                    client_netid,
                                                    client_deviceid,
                                                    "PATLookUp/Duplicates",
                                                    "Failed: \r\n\r\n\r\n",
                                                    demo.toString());
                                        } else if (type.equalsIgnoreCase("NOTFOUND")) {
                                            final String uuidCrtPat =
                                                    UUID.randomUUID().toString().replace("-", "");
                                            Log.errorf(
                                                    "TransactionID: _%s_%s | netid: %s |"
                                                        + " ClientDeviceID: %s | TransactionType:"
                                                        + " %s | TransactionStatus: %s %s",
                                                    profile,
                                                    uuid,
                                                    client_netid,
                                                    client_deviceid,
                                                    "PATLookUp/NotFound",
                                                    "Failed: \r\n\r\n\r\n",
                                                    demo.toString());
                                        } else {
                                            final String uuidCrtPat =
                                                    UUID.randomUUID().toString().replace("-", "");
                                            Log.errorf(
                                                    "TransactionID: _%s_%s | netid: %s |"
                                                        + " ClientDeviceID: %s | TransactionType:"
                                                        + " %s | TransactionStatus: %s %s",
                                                    profile,
                                                    uuid,
                                                    client_netid,
                                                    client_deviceid,
                                                    "PATLookUp/NoStatus",
                                                    "Failed: \r\n\r\n\r\n",
                                                    demo.toString());
                                        }

                                        resp.setMessage(demo.toString());
                                        return Uni.createFrom().item(resp);
                                    }
                                })
                        .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                        .subscribe()
                        .with(item -> System.out.println(item)); // Print A B C;
                // .call(item -> Uni.createFrom().item(resp));

                /*
                return fcService
                        .patLookupByDemogr_Async(env, demo)
                        .onItem()
                        .transformToUni(
                                item -> {
                                    if (item != null) {
                                        return Uni.createFrom().item(item);
                                    } else {
                                        // Create an Uni from the save operation
                                        Uni<Void> save =
                                                Uni.createFrom()
                                                        .completionStage(
                                                                () ->
                                                                        creationOrdersRepository
                                                                                .save());
                                        // This uni represents the invocation of the remote service
                                        Uni<String> webService = callWebService();

                                        // we chain the operations save -> web service -> update ->
                                        // Created
                                        return save.chain(webService)
                                                .chain(
                                                        externalServiceCallResult ->
                                                                Uni.createFrom()
                                                                        .completionStage(
                                                                                () ->
                                                                                        creationOrdersRepository
                                                                                                .updateCardNumberAndStatus(
                                                                                                        externalServiceCallResult)))
                                                .replaceWith(CardCreationResult.Created);
                                    }
                                })
                                */

                /*
                .chain(
                        dataResp ->
                                sendSMS(
                                        uuid,
                                        env,
                                        demo,
                                        phone,
                                        email,
                                        idcOR,
                                        client_ts,
                                        client_deviceid,
                                        client_netid))
                .onFailure()
                .retry()
                .atMost(3);
                */

            } catch (Exception e) {
                Log.errorf(
                        "TransactionID: _%s_%s | netid: %s | ClientDeviceID: %s | TransactionType:"
                                + " %s | Transaction Status: %s",
                        profile,
                        uuid,
                        client_netid,
                        client_deviceid,
                        "PATLookUp_MobileApp_Async/Request/Query",
                        "Query failed! Chain has issues!",
                        e);
                e.printStackTrace();
            }
        }
        return null;
    }

    /*
     *
     */
    // @Blocking
    // @NonBlocking
    @POST
    @Path("/inourdnasc/mrn")
    public biApiResp getMRN(@QueryParam("env") String env, modelPatDemogr demo) {

        // fhirclientService fcService;
        // if (StringUtils.equalsIgnoreCase(env, "prod")) {
        //    fcService = fcServiceProd;
        // } else {
        //    fcService = fcServiceTest;
        // }
        System.out.println(env);
        if (fcService == null) {
            // System.out.println(env);
            return null;
        } else {
            // System.out.println(demo.toString());
            // return fcService.getMRN(env, demo).onFailure().retry().atMost(5);
            return fcService.getMRN(env, demo);
            // .stream().findFirst().get();
            // .onFailure().retry().atMost(10);
        }
    }

    /*
     * @NonBlocking
     * @Blocking
     *
     */
    @POST
    @Path("/inourdnasc/getphone")
    public biApiResp getPhone(@QueryParam("env") String env, modelPatReq patreq) {

        // fhirclientService fcService;
        // if (StringUtils.equalsIgnoreCase(env, "prod")) {
        //    fcService = fcServiceProd;
        // } else {
        //    fcService = fcServiceTest;
        // }
        System.out.println(env);
        if (fcService == null) {
            // System.out.println(env);
            return null;
        } else {
            // System.out.println(patreq.toString());
            return fcService.getPhone(env, patreq);
            // return fcService.getPhone(env, patreq).onFailure().retry().atMost(5);
            // .onFailure().retry().atMost(10);
        }
    }

    /*
     *
     */
    // @NonBlocking
    @POST
    @Path("/inourdnasc/getnamebymrn")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<String> getNameByMRN(
            @QueryParam("env") String env, @QueryParam("uuid") String uuid, modelPatReq patreq) {
        biApiResp respDef = new biApiResp();
        respDef.setType("FATAL");
        respDef.setMessage("{}");
        respDef.setCode(400);

        Uni<String> rst = Uni.createFrom().item(respDef.toString());

        // fhirclientService fcService;
        // if (StringUtils.equalsIgnoreCase(env, "prod")) {
        //    fcService = fcServiceProd;
        // } else {
        //    fcService = fcServiceTest;
        // }
        if (fcService == null) {
            Log.errorf(
                    "TransactionID: _%s_%s_%s | TransactionType: %s | TransactionStatus: %s |"
                            + " fcService: %s ",
                    env,
                    profile,
                    uuid,
                    "fhirclientResource/getNameByMRN",
                    "GET",
                    "Failed: the restclient is null");
            return Uni.createFrom().nullItem();
        } else {
            return fcService.getNameByMRN(env, patreq).onFailure().retry().atMost(3);
        }
    }

    /*
     *
     */
    // @Blocking
    // public Uni<biApiResp> GetStudyStatusByMRN(final String env, modelPatReq patreq) {
    @POST
    @Path("/inourdnasc/getstudystatusbymrn")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    // @NonBlocking
    // public Uni<biApiResp> GetStudyStatusByMRN(
    public Uni<String> GetStudyStatusByMRN(
            @QueryParam("env") final String env,
            @QueryParam("uuid") String uuid,
            modelPatReq patreq) {
        biApiResp respDef = new biApiResp();
        respDef.setType("FATAL");
        respDef.setMessage("{}");
        respDef.setCode(400);

        // Uni<biApiResp> rst = Uni.createFrom().item(respDef);

        // fhirclientService fcService;
        // if (StringUtils.equalsIgnoreCase(env, "prod")) {
        //    fcService = fcServiceProd;
        // } else {
        //    fcService = fcServiceTest;
        // }
        if (fcService == null) {
            Log.errorf(
                    "TransactionID: _%s_%s_%s | TransactionType: %s | TransactionStatus: %s |"
                            + " fcService: %s ",
                    env,
                    profile,
                    uuid,
                    "fhirclientResource/getStudyStatusByMRN",
                    "GET",
                    "Failed: the restclient is null");
            return Uni.createFrom().nullItem();
        } else {
            return fcService.getStudyStatusByMRN(env, patreq).onFailure().retry().atMost(3);
            //        .onFailure()
            //        .retry()
            //        .withBackOff(Duration.ofMillis(100), Duration.ofSeconds(3))
            //        .expireIn(5000);
        }
    }

    /*
     *
     */
    // @Blocking
    // public Uni<biApiResp> GetStudyStatusByMRN(final String env, modelPatReq patreq) {
    @POST
    @Path("/inourdnasc/notable/getstudystatusbymrn4upt")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    // @NonBlocking
    // public Uni<biApiResp> GetStudyStatusByMRN(
    public Uni<String> GetStudyStatusByMRN4NotableUpt(
            @QueryParam("env") final String env,
            @QueryParam("uuid") String uuid,
            modelPatReq patreq) {
        biApiResp respDef = new biApiResp();
        respDef.setType("FATAL");
        respDef.setMessage("{}");
        respDef.setCode(400);

        if (fcService == null) {
            Log.errorf(
                    "TransactionID: _%s_%s_%s | TransactionType: %s | TransactionStatus: %s |"
                            + " fcService: %s ",
                    env,
                    profile,
                    uuid,
                    "fhirclientResource/getstudystatusbymrn4upt",
                    "GET",
                    "Failed: the restclient is null");
            return Uni.createFrom().nullItem();
        } else {
            return fcService.GetStudyStatusByMRN4NotableUpt(env, patreq).onFailure().retry().atMost(3);
            //        .onFailure()
            //        .retry()
            //        .withBackOff(Duration.ofMillis(100), Duration.ofSeconds(3))
            //        .expireIn(5000);
        }
    }
}
