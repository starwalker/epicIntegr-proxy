package edu.musc.bi;

import static edu.musc.bi.IncomingInterceptor.EXTRA_BLOCKING_HEADER;
import io.quarkus.logging.Log;

import io.smallrye.common.annotation.NonBlocking;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import com.fasterxml.jackson.core.*;

import edu.musc.bi.restclient.patDups;
import edu.musc.bi.restclient.patDup;
import com.google.protobuf.Timestamp;

import edu.musc.bi.pdf417.model.MobileAppRequest;

import io.grpc.Metadata;
//
import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;

import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

//import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;

@Path("/fhirepic")
//@RequestScoped
@ApplicationScoped
public class biFhirEpicEndpoint {

    @GrpcClient("fhirepic")
    biFhirEpicSVCGrpc.biFhirEpicSVCBlockingStub blockingFhirepicService;

    @GrpcClient("fhirepic")
    MutinybiFhirEpicSVCGrpc.MutinybiFhirEpicSVCStub mutinyFhirepicService;

    @Inject HeaderCollectingInterceptor headerCollectingInterceptor;

    @Inject JsonWebToken jwt;

    static final ObjectMapper mapper = new ObjectMapper();

    @POST
    @Path("/pdf417")
    @RolesAllowed({"bi-dev", "bi-admin", "bi-client", "bidev", "biadmin", "biclient", "bmicclient"})
    //@PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @NonBlocking
    public Uni<String> fhirepicPdf417Post(
            @Context SecurityContext ctx,
            @DefaultValue("test") @QueryParam("env") String env,
            @Valid MobileAppRequest maReq) {

        return getRespByPDF417(ctx, env, maReq);
    }

    private final Uni<String> getRespByPDF417(
            final SecurityContext ctx, final String env, final MobileAppRequest maReq) {
        String name;
        String envFromRequest = env;
        if (ctx.getUserPrincipal() == null) {
            name = "anonymous";
        } else if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) {
            throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
        } else {
            name = ctx.getUserPrincipal().getName();
        }

        if ( !StringUtils.equalsIgnoreCase(envFromRequest, "prod")) {
            if ( !StringUtils.equalsIgnoreCase(name, "jwt_prod@musc.edu")) {
                Log.warnv("the JWT's upn: {0} is mismatched!", name);
                envFromRequest = "test";
            }
        }
        final boolean env_prod = StringUtils.equalsIgnoreCase(envFromRequest,"prod") ? true : false;

        final String strCtx =
                String.format(
                        "ENV: %s, "
                                + " env_prod: %s"
                                + " hello: %s,"
                                + " isHttps: %s,"
                                + " authScheme: %s,"
                                + " hasJWT: %s,",
                        envFromRequest, env_prod, name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJwt());

        Log.info(strCtx);
        final String pdf417 = maReq.getPdf417()==null?"":maReq.getPdf417();
        final String phone = maReq.getPhone()==null?"":maReq.getPhone();
        final String email = maReq.getEmail()==null?"":maReq.getEmail();
        final String ssn = maReq.getSsn()==null?"":maReq.getSsn();
        final String idc_override= maReq.getIdc_override()==null?"":maReq.getIdc_override();
        final String linkageid = maReq.getLinkageid()==null?"":maReq.getLinkageid();
        final String netid = maReq.getNetid()==null?"":maReq.getNetid();
        final String deviceid = maReq.getClientPhoneMIN()==null?"":maReq.getClientPhoneMIN();
        final String ts = maReq.getTs()==null?"":maReq.getTs();

        final String strReq=
                String.format(
                        "ENV: %s, "
                                + " env_prod: %s,"
                                + " jwt name:  %s,"
                                + " pdf417: %s,"
                                + " phone: %s,"
                                + " email: %s,"
                                + " ssn: %s,"
                                + " idc_override: %s,"
                                + " linkageid: %s,"
                                + " client ts: %s, "
                                + " netid: %s,"
                                + " ClientPhoneMIN: %s",
                        envFromRequest, env_prod, name, pdf417, phone, email, ssn, idc_override, linkageid, ts, netid, deviceid);
        Log.info(strReq);

        return mutinyFhirepicService
                .ptLookupPDF417(ptLookupRequest.newBuilder().setPdf417(pdf417)
                        .setPhone(phone)
                        .setEnvProd(env_prod)
                        .setEmail(email)
                        .setSsn(ssn)
                        .setLinkageid(linkageid)
                        .setIdcOverride(idc_override)
                        .setNetid(netid)
                        .setDeviceName(deviceid)
                        .build())
                .onItem()
                .transform(this::generateResponse);
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

    private patDup[] topatDups(String s) {
        try {
            return mapper.readValue(s, patDup[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    /*
    private patDups topatDups(String s) {
        try {
            return mapper.readValue(s, patDups.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    */

    public String generateResponse(ptLookupReply reply) {

        // Creating Object of ObjectMapper define in Jakson Api - an new instance
        //final ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        //mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        String jsonStr = "";
        try {
            // create a JSON object
            ObjectNode root = mapper.createObjectNode();

            final Timestamp ts_act_start = reply.getActivationTsStart();
            final Timestamp ts_act_end = reply.getActivationTsEnd();
            final Timestamp ts_msg_ts = reply.getMsgDeliveryTs();


            final String rc = reply.getReturnCode();
            root.put("return_code", rc);

            String strDupsResp = reply.getPatDups();
            if (!StringUtils.isBlank(strDupsResp)) {
                if (strDupsResp.length()>10) {
                    //System.out.println(strDupsResp);
                    //strDupsResp = strDupsResp.replace("\r","").replace("\n","").replace("\\r\\n", "").replaceAll("(\\r|\\n|\\t)", "");
                    //strDupsResp = strDupsResp.replace("\\\"", "\"");
                    patDup[] dups = topatDups(strDupsResp);
                    /*
                    for (patDup dup : dups) {
                        System.out.println(dup.toString());
                    }
                    */

                    ArrayNode array = mapper.valueToTree(dups);
                    root.putArray("pat_dups").addAll(array);

                    //patDups dups = topatDups(strDupsResp);
                    //for (patDup dup:dups.getPats()) {
                    //    System.out.println(dup.toString());
                    //}
                }
            }



            // Create a child JSON object msg
            ObjectNode msg = mapper.createObjectNode();
            msg.put("Delivery Type", reply.getMsgDeliveryType());
            msg.put("Delivery Status", reply.getMsgDeliveryStatus());
            msg.put("Delivery TS", convertGoogleTimestampToUTC(ts_msg_ts));
            // Append idto the main body
            root.set("Message", msg);
            //

            // Create a child JSON object id
            ObjectNode id = mapper.createObjectNode();
            //id.put("mrn", reply.getIdMuscMrn());
            id.put("pdf417_or", reply.getPdf417Or());
            //id.put("internalID", reply.getIdMuscInternalID());
            //id.put("fhirid", reply.getIdMuscFhirid());
            //id.put("epi", reply.getIdMuscEpi());
            //id.put("mychartAcct", reply.getIdMuscMychartAcct());
            //id.put("encryptedID", reply.getIdEncryptedID());
            id.put("linkageID", reply.getIdLinkageID());
            // Append idto the main body
            root.set("id", id);

            root.put("phone", reply.getPhone());
            root.put("email", reply.getEmail());
            root.put("name", reply.getName());
            root.put("name_hl7", reply.getNameHl7());
            root.put("lastName", reply.getLastName());
            root.put("firstname", reply.getFirstname());
            root.put("middlename", reply.getMiddlename());
            root.put("dob", reply.getDob());
            root.put("sex", reply.getSex());
            root.put("race", reply.getRace());
            root.put("ethnic", reply.getEthnic());
            root.put("DLN", reply.getDln());
            //
            // Create a child JSON object address
            ObjectNode addr = mapper.createObjectNode();
            addr.put("address", reply.getAddress());
            addr.put("city", reply.getCity());
            addr.put("state", reply.getState());
            addr.put("zip", reply.getZip());
            addr.put("country", reply.getCountry());
            // Append address to the main body
            root.set("address", addr);

            // Create a child JSON object MyChart
            ObjectNode study = mapper.createObjectNode();
            study.put("EnrollmentStatus", reply.getStudyEnrollmentStatus());
            study.put("EnrollEventDate", reply.getStudyEnrollEventDate());
            study.put("labOrderDate", reply.getStudyLabOrderDate());
            // Append study to the main body
            root.set("Study", study);

            // Create a child JSON object MyChart
            ObjectNode myc = mapper.createObjectNode();
            myc.put("MyChartStatus", reply.getMycMyChartStatus());
            myc.put("isProxyOnly", reply.getMycIsProxyOnly());
            // Append myc to the main body
            root.set("MyChart", myc);

            root.put("activation_url", reply.getActivationUrl());
            root.put("activation_Code", reply.getActivationCode());
            root.put(
                    "activation_pre_populated_username", reply.getActivationPrePopulatedUsername());
            root.put("activation_ts_start", convertGoogleTimestampToUTC(ts_act_start));
            root.put("activation_ts_end", convertGoogleTimestampToUTC(ts_act_end));
            root.put("activation_duration_seconds", reply.getActivationDurationSeconds());

            root.put("bmicClientType", reply.getBmicClientType());

            // get ptLookupReply object as a json string
            // convert 'root' to pretty-print JSON string
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
            //jsonStr = jsonStr.replace("\r","").replace("\n","").replace("\\r\\n", "").replaceAll("(\\r|\\n|\\t)", "");
            //jsonStr = jsonStr.replace("\\\"", "\"");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    @GET
    @RolesAllowed({"bi-dev", "bi-admin", "bi-client", "bidev", "biadmin", "biclient", "bmicclient"})
    @Path("/blocking/{pdf417}")
    public String fhirepicBlocking(
            @PathParam("pdf417") String pdf417, @QueryParam("headers") boolean headers) {
        Metadata extraHeaders = new Metadata();
        if (headers) {
            extraHeaders.put(EXTRA_BLOCKING_HEADER, "bi-blocking-value");
        }
        ptLookupReply
                reply = // GrpcClientUtils.attachHeaders(blockingFhirepicService, extraHeaders)
                blockingFhirepicService.ptLookupPDF417(
                                ptLookupRequest.newBuilder().setPdf417(pdf417).build());
        return generateResponse(reply);
    }

    @GET
    @RolesAllowed({"bi-dev", "bi-admin", "bi-client", "bidev", "biadmin", "biclient", "bmicclient"})
    @Path("/mutiny/{pdf417}")
    public Uni<String> fhirepicMutiny(@PathParam("pdf417") String pdf417) {
        return mutinyFhirepicService
                .ptLookupPDF417(ptLookupRequest.newBuilder().setPdf417(pdf417).build())
                .onItem()
                .transform(this::generateResponse);
    }

    @GET
    @RolesAllowed({"bi-dev", "bi-admin", "bi-client"})
    @Path("/pdf417/{pdf417}")
    public Uni<String> fhirepicPdf417(
            final SecurityContext ctx, @PathParam("pdf417") String pdf417) {
        /*
        return mutinyFhirepicService
                .ptLookupPDF417(ptLookupRequest.newBuilder().setPdf417(pdf417).build())
                .onItem()
                .transform(this::generateResponse);
                */
        return getRespByPDF417(ctx, pdf417, null);
    }
}
