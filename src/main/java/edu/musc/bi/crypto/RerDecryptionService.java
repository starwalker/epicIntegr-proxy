package edu.musc.bi.crypto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import edu.musc.bi.aesclient.aesClientResource;
import edu.musc.bi.restclient.biApiResp;
import edu.musc.bi.restclient.fhirclientResource;
import edu.musc.bi.restclient.modelPatReq;
import edu.musc.bi.restclient.nameFromEpic;
import edu.musc.bi.tracking.modelTrackingRequest;
import edu.musc.bi.tracking.trackingResource;
import edu.musc.bi.utils.dingzitime;

import io.smallrye.mutiny.Uni;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.awt.Font.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class RerDecryptionService {
    @ConfigProperty(name = "bi-key-dlscan")
    String keyDLScan;

    @ConfigProperty(name = "bi-key-notable")
    String keyNotable;

    @ConfigProperty(name = "bi-key-dlscan-first")
    String keyDLScanFirst;

    private static final Logger LOGGER = Logger.getLogger(RerDecryptionService.class);
    private static dingzitime dtime = new dingzitime();

    // protected static final AtomicReference<String> rstMsgShared = new AtomicReference<>();
    protected static final AtomicBoolean flag = new AtomicBoolean(false);

    private static final ObjectMapper mapper = new JsonMapper();
    @Inject fhirclientResource fcRes;
    @Inject aesClientResource aesRes;
    @Inject trackingResource trackingRes;

    public RerDecryptionService() {
        if (fcRes == null) {
            fcRes = new fhirclientResource();
        }
        if (aesRes == null) {
            aesRes = new aesClientResource();
        }
        if (trackingRes == null) {
            trackingRes = new trackingResource();
        }
    }

    /*
     *
        call rest client
     *
    */
    private final biApiResp tobiApiResp(String s) {
        try {
            // System.out.println("\n\n\ntobiApiResp input: " + s);
            return mapper.readValue(s, biApiResp.class);
        } catch (JsonProcessingException e) {
            // throw new RuntimeException(e);
            LOGGER.errorf(
                    e,
                    "Rest Client Chain:: tobiApiResp -- the response has an issue!! body: %s",
                    s);
            e.printStackTrace();
        }
        return null;
    }

    /*
    //
    // call rest client
    //
    */
    protected final String setRespDecryptStudy(
            final String env,
            final String mrn,
            final String uuid,
            final String agegroup,
            final String encryptedText,
            final biApiResp barStudy,
            modelDecryptResp dr) {
        String message = "success";
        String errMsg = "failed";
        try {
            if (barStudy == null || !StringUtils.equalsIgnoreCase(barStudy.getType(), "FOUND")) {
                errMsg =
                        String.format(
                                "{\"TransactionID\":"
                                        + " \"%s\",\"TransactionTS\":"
                                        + " \"%s\",\"TransactionEnv\":"
                                        + " \"%s\", \"TransactionJob\":"
                                        + " \"%s\",\"TransactionType\":"
                                        + " \"%s\","
                                        + " \"TransactionStatus\":"
                                        + " \"%s\","
                                        + " \"TransactionContext\":"
                                        + " \"%s_%s\"}",
                                uuid,
                                LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                                env,
                                "Decrypt/EpicToken/FetchStudy/Processing/setRespDecryptStudy/Resp/NULL",
                                "QUERY",
                                "Captured/Authorized/Failed",
                                encryptedText,
                                mrn);
                LOGGER.error(errMsg);
                message =
                        String.format(
                                "ERROR:: FATAL :: time: %s ENV: %s -- Failed or unable to fetch the"
                                        + " patient's Study for an Epic token:: %s",
                                LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                                env,
                                encryptedText);
                dr.setCollect_type("home");
            } else { // response is not null and the message body is not empty
                final String RespBody = barStudy.getMessage();
                final String type = barStudy.getType();
                if (type.equalsIgnoreCase("FOUND")) {
                    dr.setCollect_type("clinic");
                } else {
                    dr.setCollect_type("home");
                }
            }
        } catch (Exception e) {
            message =
                    String.format(
                            "ERROR:: FATAL :: time: %s ENV: %s -- Failed or unable to fetch the"
                                    + " patient's Study for an Epic token:: %s, ErrMsg:: %s",
                            LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                            env,
                            encryptedText,
                            e);

            dr.setMsg(message);
            LOGGER.errorf(e, "Rest Client Chain has an issue!! setRespDecryptStudy: %s", mrn);
            e.printStackTrace();
        }
        dr.setMsg(message);
        return message;
    }

    /*
    //
    // call rest client
    //
    */
    protected final String setRespDecryptName(
            final String env,
            final String mrn,
            final String uuid,
            final String agegroup,
            final String encryptedText,
            final biApiResp barName,
            modelDecryptResp dr) {
        String message = "success";
        String errMsg = "failed";
        try {
            if (barName == null || !StringUtils.equalsIgnoreCase(barName.getType(), "FOUND")) {
                errMsg =
                        String.format(
                                "{\"TransactionID\":"
                                        + " \"%s\",\"TransactionTS\":"
                                        + " \"%s\",\"TransactionEnv\":"
                                        + " \"%s\", \"TransactionJob\":"
                                        + " \"%s\",\"TransactionType\":"
                                        + " \"%s\","
                                        + " \"TransactionStatus\":"
                                        + " \"%s\","
                                        + " \"TransactionContext\":"
                                        + " \"%s\"}",
                                uuid,
                                LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                                env,
                                "Decrypt/EpicToken/FetchName/Processing/setRespDecryptName/Resp/NULL",
                                "QUERY",
                                "Captured/Authorized/Failed",
                                encryptedText);
                LOGGER.error(errMsg);
                message =
                        String.format(
                                "ERROR:: FATAL :: time: %s ENV: %s -- Failed or unable to fetch the"
                                        + " patient's MRN for an Epic token:: %s",
                                LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                                env,
                                encryptedText);
            } else { // response is not null and the message body is not empty
                final String RespBody = barName.getMessage();
                final String type = barName.getType();
                final int code = barName.getCode();
                nameFromEpic name = toName(RespBody, uuid);
                if (name == null
                        || (StringUtils.isBlank(name.getFirst())
                                && StringUtils.isBlank(name.getLast()))) {
                    errMsg =
                            String.format(
                                    "{\"TransactionID\":"
                                            + " \"%s\",\"TransactionTS\":"
                                            + " \"%s\",\"TransactionEnv\":"
                                            + " \"%s\", \"TransactionJob\":"
                                            + " \"%s\",\"TransactionType\":"
                                            + " \"%s\","
                                            + " \"TransactionStatus\":"
                                            + " \"%s\","
                                            + " \"TransactionContext\":"
                                            + " \"%s\"}",
                                    uuid,
                                    LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                                    env,
                                    "Decrypt/EpicToken/FetchName/Processing/setRespDecryptName/Resp/Name/NULL",
                                    "QUERY",
                                    "Captured/Authorized/Failed",
                                    encryptedText);
                    LOGGER.error(errMsg);
                    message =
                            String.format(
                                    "ERROR:: time: %s ENV: %s -- Failed or unable to fetch the"
                                            + " patient's name based on this Epic token:: %s",
                                    LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                                    env,
                                    encryptedText);
                } else { // name is not null and the patient has last and first name
                    final String first = name.getFirst();
                    final String last = name.getLast();
                    final String dob = name.getDob();
                    final String decryptedString = "mrn=" + mrn;
                    dr.setMrn(mrn);
                    dr.setDecrypted(decryptedString);
                    dr.setFirst(first);
                    dr.setLast(last);
                    if (!StringUtils.isBlank(dob)
                            && !StringUtils.equalsIgnoreCase(agegroup.trim(), "all")) {
                        final int age = dtime.calculateAge(dob);
                        if (age > -1) {
                            if (StringUtils.equalsIgnoreCase(agegroup.trim(), "adult")) {
                                if (age < 18) {
                                    dr.setMrn("");
                                    dr.setDecrypted("");
                                    dr.setFirst("");
                                    dr.setLast("");
                                    message =
                                            String.format(
                                                    "ERROR:"
                                                            + " Age-group"
                                                            + " issue:"
                                                            + " Only"
                                                            + " individuals"
                                                            + " 18 and"
                                                            + " older"
                                                            + " will"
                                                            + " be considered"
                                                            + " enrolled"
                                                            + " into"
                                                            + " this"
                                                            + " study!"
                                                            + " Note:"
                                                            + " Your"
                                                            + " encrypted"
                                                            + " id: %s;"
                                                            + " Your"
                                                            + " age:"
                                                            + " %s",
                                                    encryptedText, age);
                                    LOGGER.warnf(
                                            "TransactionID: %s"
                                                    + " | TransactionTS: %s"
                                                    + " | TransactionEnv:"
                                                    + " %s |"
                                                    + " TransactionJob:"
                                                    + " %s |"
                                                    + " TransactionType:"
                                                    + " %s |"
                                                    + " TransactionStatus:"
                                                    + " %s TransactionContext:"
                                                    + " %s",
                                            uuid,
                                            LocalDateTime.ofInstant(
                                                    Instant.now(), ZoneId.systemDefault()),
                                            env,
                                            "Decrypt/Agegroup/Child",
                                            "QUERY",
                                            "Captured/UnAuthorized/Failed",
                                            message);
                                }
                            } else if (StringUtils.equalsIgnoreCase(agegroup.trim(), "child")) {
                                if (age >= 18) {
                                    dr.setMrn("");
                                    dr.setDecrypted("");
                                    dr.setFirst("");
                                    dr.setLast("");
                                    message =
                                            String.format(
                                                    "ERROR:"
                                                            + " Age-group"
                                                            + " issue:"
                                                            + " Only"
                                                            + " individuals"
                                                            + " under"
                                                            + " the age"
                                                            + " of 18"
                                                            + " will"
                                                            + " be considered"
                                                            + " enrolled"
                                                            + " into"
                                                            + " this"
                                                            + " study!"
                                                            + " Note:"
                                                            + " Your"
                                                            + " encrypted"
                                                            + " id: %s;"
                                                            + " Your"
                                                            + " age:"
                                                            + " %s",
                                                    encryptedText, age);
                                    LOGGER.warnf(
                                            "TransactionID: %s"
                                                    + " | TransactionTS: %s"
                                                    + " | TransactionEnv:"
                                                    + " %s |"
                                                    + " TransactionJob:"
                                                    + " %s |"
                                                    + " TransactionType:"
                                                    + " %s |"
                                                    + " TransactionStatus:"
                                                    + " %s TransactionContext:"
                                                    + " %s",
                                            uuid,
                                            LocalDateTime.ofInstant(
                                                    Instant.now(), ZoneId.systemDefault()),
                                            env,
                                            "Decrypt/Agegroup/Child",
                                            "QUERY",
                                            "Captured/UnAuthorized/Failed",
                                            message);
                                } // age >= 18
                            } // if agegroup is child
                        } //  if age>-1
                    } // if (!StringUtils.isBlank(dob)
                } // if (!StringUtils.isBlank(name.getFirst()))

                // modelDecryptResp respDecrypt =
                //        new modelDecryptResp(
                //                message, encryptedText, paramter, mrn, first, last, collect_type);
                LOGGER.infof(
                        "TransactionID: %s"
                                + " | TransactionTS: %s"
                                + " | TransactionEnv: %s"
                                + " | TransactionJob: %s"
                                + " | TransactionType: %s"
                                + " | TransactionStatus: %s"
                                + " | TransactionContext: %s",
                        uuid,
                        LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                        env,
                        "Decrypt/ReturnMessage/setMRNandName/Final",
                        "QUERY",
                        "Captured/Authorized/Success",
                        encryptedText);
                // return Uni.createFrom()
                //        .item(Response.ok(resp).build());
            } // biApiResp is null
        } catch (Exception e) {
            message =
                    String.format(
                            "Time: %s ENV: %s -- Failed or unable to fetch the patient's MRN and"
                                    + " name for an Epic token:: %s, ErrMsg:: %s",
                            LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                            env,
                            encryptedText,
                            e);

            dr.setMsg(message);
            LOGGER.errorf(e, "Rest Client Chain has an issue!! setRespDecryptName: %s", mrn);
            e.printStackTrace();
        }
        dr.setMsg(message);
        return message;
    }

    /*
    //
    // call rest client
    //
    */
    protected final Response getRespDecrypt(
            final modelPatReq patreq,
            final String name,
            final String study,
            final boolean rtnsrc, // all, adult, child
            final boolean rtnenrollstatus) { // all, adult, child

        String msgName = "success";
        String msgStudy = "success";
        String rstErrorMsg = "failed";
        // String decrypted = "";
        final String mrn = patreq.getMrn();
        final String env = patreq.getEnv();
        final String uuid = patreq.getUuid();
        final String encryptedText = patreq.getEncrypted();
        final String agegroup = patreq.getAgegroup();

        Response resp = Response.status(400).entity(rstErrorMsg).build();

        modelDecryptResp dr = new modelDecryptResp();
        dr.setEncrypted(encryptedText);
        dr.setIss(patreq.getIss());
        try {
            if (patreq != null && StringUtils.isNotBlank(patreq.getRefsource()) && rtnsrc) {
                dr.setRefsource(patreq.getRefsource());
            }
            if (StringUtils.isNotBlank(name)) {
                // System.out.println("\n\n\nName Return: " + name);
                biApiResp barName = tobiApiResp(name);
                // System.out.println("\n\n\nName Return type: " + barName.getType());
                // System.out.println("\n\n\nName Return message: " + barName.getMessage());
                // System.out.println("\n\n\nName Return code: " + barName.getCode());
                msgName = setRespDecryptName(env, mrn, uuid, agegroup, encryptedText, barName, dr);
            }
            if (StringUtils.isNotBlank(study)) {
                // System.out.println("\n\n\nStudy RReturn: " + study);
                biApiResp barStudy = tobiApiResp(study);
                // System.out.println("\n\n\nStudy Return type: " + barStudy.getType());
                // System.out.println("\n\n\nStudy RReturn message: " + barStudy.getMessage());
                // System.out.println("\n\n\nStudy RReturn code: " + barStudy.getCode());
                msgStudy =
                        setRespDecryptStudy(env, mrn, uuid, agegroup, encryptedText, barStudy, dr);
                // final String strMsgStudy = barStudy.getMessage();
                // if (!StringUtils.isBlank(strMsgStudy)) {}
            }

            if (StringUtils.equalsIgnoreCase(msgName, "success")
                    && StringUtils.equalsIgnoreCase(msgStudy, "success")) {
                resp = Response.ok(dr).build();
            } else {
                final String strErrResp =
                        String.format(
                                "ERROR:: FATAL :: TS: %s ENV: %s -- Failed or unable to fetch the"
                                    + " patient's MRN, name or study info for an Epic token:: %s."
                                    + " GetName:: %s GetStudyStatus:: %s",
                                LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                                env,
                                encryptedText,
                                msgName,
                                msgStudy);
                dr.setMsg(strErrResp);
                resp = Response.status(206).entity(dr).build();
            }

            LOGGER.infof(
                    "TransactionID: %s "
                            + " | TransactionTS: %s"
                            + " | TransactionEnv: %s | TransactionJob: %s | TransactionType:"
                            + " %s | TransactionStatus: %s, %s",
                    uuid,
                    LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                    env,
                    "Decrypt/getRespDecrypt/End",
                    "QUERY",
                    "Captured/Authorized/Success",
                    resp);
        } catch (Exception e) {
            LOGGER.errorf(e, "Rest Client Chain has an issue!! getRespDecrypt: %s", mrn);
            e.printStackTrace();
        }
        return resp;
    }

    /*
     *
     */
    private nameFromEpic toName(final String s, final String uuid) {
        try {
            return mapper.readValue(s, nameFromEpic.class);
        } catch (JsonProcessingException e) {
            LOGGER.errorf(e, "Rest Client Chain - toName has an issue!! String: %s", s);
            // throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    /*
     *
     */
    public Uni<Response> encrypt(
            @DefaultValue("test") @QueryParam("env") String env,
            @DefaultValue("adult") @QueryParam("agegroup") String agegroup, // all, adult, child
            @Valid RerDecryption body)
            throws Exception {

        final String uuid = UUID.randomUUID().toString().replace("-", "");
        final String encryptedText = body.epicToken;
        if (encryptedText == null) {
            return Uni.createFrom().item(Response.status(400).build());
        }

        Decrypt decrypt = new Decrypt(encryptedText);
        String message = "success";
        String rstErrorMsg = "failed";
        String paramter = "";
        String decrypted = "";
        String mrn = "";
        String first = "";
        String last = "";
        String dob = "";
        String collect_type = "home";

        try {
            if (encryptedText != null) {
                return Uni.createFrom().item(Response.ok(encryptedText).build());
            }
        } catch (Exception e) {
            rstErrorMsg =
                    String.format(
                            "TransactionID: %s "
                                    + " | TransactionTS: %s"
                                    + "| TransactionEnv: %s | TransactionJob: %s |"
                                    + " TransactionType: %s | TransactionStatus: %s |"
                                    + " TransactionContext: %s",
                            uuid,
                            LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                            env,
                            "Decrypt/Processing/Exception",
                            "QUERY",
                            "Captured/Authorized/Failed",
                            encryptedText);
            LOGGER.errorf(e, rstErrorMsg);
            // e.printStackTrace(System.out);
            // throw new RuntimeException(e.getCause());
        }
        return Uni.createFrom().item(Response.status(400).entity(rstErrorMsg).build());
    }

    /*
     *
     *
     *
     *
     */
    private final modelDecryptResp getMrnFromEncryptedStr_MobileApp(
            final String uuid,
            final String env,
            final String agegroup, // all, adult, child
            final String encryptedText,
            final modelDecryptResp id)
            throws Exception {
        String rstErrorMsg = "failed";
        String paramter = "";
        String mrn = "";
        try {
            if (StringUtils.isNotBlank(id.getMrn())) {
                // String[] arrOfMsg = id.split(Pattern.quote("|"));
                // if (arrOfMsg.length > 0) {
                //    mrn = arrOfMsg[0];
                // }
                mrn = id.getMrn();
                paramter = "mrn=" + mrn;
                LOGGER.infof(
                        "TransactionID: %s "
                                + " | TransactionTS: %s"
                                + " | TransactionEnv: %s | TransactionJob: %s"
                                + " | TransactionType: %s | TransactionStatus: %s"
                                + " | TransactionContext: %s",
                        uuid,
                        LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                        env,
                        "Decrypt_Vendor/EpicToken/MobileApp/Enroll/aesRes.getMessage",
                        "QUERY",
                        "Captured/Authorized/Success",
                        encryptedText);
            } else { // if (!StringUtils.isBlank(id)) {
                mrn = "";
                paramter = "mrn=" + mrn;

                rstErrorMsg =
                        String.format(
                                "{\"TransactionID\": \"%s\","
                                        + "\"TransactionTS\": \"%s\","
                                        + "\"TransactionEnv\": \"%s\","
                                        + " \"TransactionJob\": \"%s\",\"TransactionType\":"
                                        + " \"%s\", \"TransactionStatus\": \"%s\","
                                        + " \"TransactionContext\": \"%s\"}",
                                uuid,
                                LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                                env,
                                "Decrypt_Vendor/EpicToken/MobileApp/Enroll/Processing/MRN/NULL",
                                "QUERY",
                                "Captured/Authorized/Failed",
                                id.getMsg());
                LOGGER.error(rstErrorMsg);
                mrn = id.getMrn();
            }
        } catch (Exception e) {
            rstErrorMsg =
                    String.format(
                            "TransactionID: %s "
                                    + " | TransactionTS: %s"
                                    + "| TransactionEnv: %s | TransactionJob: %s |"
                                    + " TransactionType: %s | TransactionStatus: %s |"
                                    + " TransactionContext: %s",
                            uuid,
                            LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                            env,
                            "Decrypt_Vendor/MobileApp/Enroll/Processing/Exception",
                            "QUERY",
                            "Captured/Authorized/Failed",
                            encryptedText);
            LOGGER.errorf(e, rstErrorMsg);
            // e.printStackTrace(System.out);
            // throw new RuntimeException(e.getCause());
        }
        // return Uni.createFrom().item(mrn);
        return id;
    }

    /*
     *
     */
    private final Uni<modelDecryptResp> decrypt_Epic_Async(
            final String uuid,
            final String env,
            final String agegroup, // all, adult, child
            final String encryptedText) {
        String rstErrorMsg = "failed";
        String paramter = "";
        String mrn = "";
        String decrypted = "";
        String message = "";
        modelDecryptResp respDecrypt = new modelDecryptResp();
        respDecrypt.setIss(0);
        try {
            // System.out.println(encryptedText);
            LOGGER.debug("this message is came from Epic: " + encryptedText);
            final Decrypt decryptMyChart = new Decrypt(encryptedText);
            /*
            1, SMS Campaign Option 1
            2, SMS Campaign Option 2
            3, SMS Campaign Option 3
            4, SMS Campaign Option 4
            5, In Our DNA SC Landing Page
            6, In Our DNA SC Landing Page - ES
            7, MyChart Initial Recruitment Message Consent Link
            8, MyChart Reminder Recruitment  Consent Link
            9, MyChart Research List Link - Being Identified
            10, MyChart Interesting Consent Link
            11, Interested Text Message
            12, MUSC Horseshoe Employee Sign-Up Link
            13, Community Event
            14, Notable
            15, DL Scan App
            16, Paper Consented
            17, SMS Campaign 2022
            18, SMS Campaign Option 5 (Consent5)
            19, SMS Campaign Option 6 (dna1)
            20, SMS Campaign Option 7 (230919 dna)
            21, Mammogram and Colonoscopy Cohorts
            */
            final List<Integer> recruitmentList =
                    Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 14, 18, 21);
            // for (int i = 0; i < recruitmentList.size(); i++) {
            for (int i : recruitmentList) {
                try {
                    decrypted = decryptMyChart.getDecryptedString(i);
                    paramter = decrypted;
                    mrn = decryptMyChart.getMRN(decrypted);
                    // if (StringUtils.isNotBlank(mrn) && StringUtils.isAlphanumeric(mrn)) {
                    if (StringUtils.isAlphanumeric(mrn)) {
                        respDecrypt.setMrn(mrn);
                        respDecrypt.setRefsource(String.format("MyChartRecruitment%s", i));
                        LOGGER.infof(
                                "TransactionID: %s "
                                        + " | TransactionTS: %s"
                                        + "| TransactionEnv: %s | TransactionJob: %s |"
                                        + " TransactionType: %s | TransactionStatus: %s"
                                        + " TransactionContext: %s_%s_%s",
                                uuid,
                                LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                                env,
                                "Decrypt_Epic/MyChart/getDecryptedString",
                                "QUERY",
                                "Captured/Authorized/Success",
                                encryptedText,
                                mrn,
                                i);
                        break;
                    }
                } catch (Exception e) {
                    message =
                            String.format(
                                    "ERROR:: FATAL WITH Exception :: time: %s ENV: %s --"
                                            + " Failed or unable to fetch the patient's MRN for"
                                            + " an Epic/MyChart token:: %s_%s. Exception: %s",
                                    LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                                    env,
                                    encryptedText,
                                    i,
                                    e);
                    rstErrorMsg =
                            String.format(
                                    "TransactionID: %s  | TransactionTS: %s|"
                                            + " TransactionEnv: %s | TransactionJob: %s |"
                                            + " TransactionType: %s | TransactionStatus: %s |"
                                            + " TransactionContext: %s_%s",
                                    uuid,
                                    LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                                    env,
                                    "Decrypt_Epic_Async/EpicToken/MyChart/Processing/Exception",
                                    "QUERY",
                                    "Captured/Authorized/Failed",
                                    encryptedText,
                                    i);
                    LOGGER.errorf(e, rstErrorMsg);
                    continue;
                }
            } // for
            if (!StringUtils.isAlphanumeric(mrn)) {
                message =
                        String.format(
                                "ERROR:: FATAL :: time: %s ENV: %s -- Failed or unable to fetch the"
                                        + " patient's MRN for an Epic/MyChart token:: %s",
                                LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                                env,
                                encryptedText);

                rstErrorMsg =
                        String.format(
                                "{\"TransactionID\": \"%s\","
                                        + "\"TransactionTS\": \"%s\","
                                        + "\"TransactionEnv\": \"%s\","
                                        + " \"TransactionJob\": \"%s\",\"TransactionType\":"
                                        + " \"%s\", \"TransactionStatus\": \"%s\","
                                        + " \"TransactionContext\": \"%s\"}",
                                uuid,
                                LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                                env,
                                "Decrypt_Epic/EpicToken/MyChart/Processing/MRN/NULL",
                                "QUERY",
                                "Captured/Authorized/Failed",
                                message);
                LOGGER.error(rstErrorMsg);
                respDecrypt.setMsg(message);
            }
        } catch (Exception e) {
            message =
                    String.format(
                            "ERROR:: FATAL WITH Exception :: time: %s ENV: %s -- Failed or unable"
                                    + " to fetch the patient's MRN for an Epic/MyChart token:: %s."
                                    + " Exception: %s",
                            LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                            env,
                            encryptedText,
                            e);
            rstErrorMsg =
                    String.format(
                            "TransactionID: %s "
                                    + " | TransactionTS: %s"
                                    + "| TransactionEnv: %s | TransactionJob: %s |"
                                    + " TransactionType: %s | TransactionStatus: %s |"
                                    + " TransactionContext: %s",
                            uuid,
                            LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                            env,
                            "Decrypt_Epic_Async/EpicToken/MyChart/Processing/Exception",
                            "QUERY",
                            "Captured/Authorized/Failed",
                            encryptedText);
            LOGGER.errorf(e, rstErrorMsg);
            respDecrypt.setMsg(message);
            // e.printStackTrace(System.out);
            // throw new RuntimeException(e.getCause());
        }
        return Uni.createFrom().item(respDecrypt);
    }

    /*
     *
     */
    private Uni<modelDecryptResp> decryptTokenChain_Async(
            final String uuid,
            final String env,
            final String agegroup, // all, adult, child
            final boolean rtnsrc, // all, adult, child
            final boolean rtnenrollstatus, // all, adult, child
            final String encryptedText)
            throws Exception {
        final String ts = dtime.getCurrTS();

        return decrypt_Epic_Async(uuid, env, agegroup, encryptedText)
                .flatMap(
                        resultEpicToken -> {
                            System.out.println("\n\n\nReturn: " + resultEpicToken);
                            if (StringUtils.isNotBlank(resultEpicToken.getMrn())) {
                                System.out.println("\n\n\nMRN Return: " + resultEpicToken.getMrn());
                                LOGGER.tracef(
                                        "This epictoken request is from the Mychart referral"
                                                + " source: %s",
                                        encryptedText);
                                if (StringUtils.isBlank(resultEpicToken.getRefsource())) {
                                    resultEpicToken.setRefsource("MyChartRecruitment");
                                }
                                return Uni.createFrom().item(resultEpicToken);
                            } else {
                                System.out.println(
                                        "\n\n\nTry the DLScan Mobile first App referral source: "
                                                + encryptedText);
                                LOGGER.errorf(
                                        "It's not from the Mychart referral source, try the DLSCan"
                                                + " Mobile first App referral source: %s",
                                        encryptedText);
                                return aesRes.getMrnEpicToken_Async(
                                        encryptedText, keyDLScanFirst, 0, "DLSCanApp");
                            }
                        })
                .chain(
                        resultEpicToken -> {
                            // System.out.println(
                            //        "\n\n\nThe DLScan Mobile First App Token Return: "
                            //                + resultEpicToken);
                            if (StringUtils.isAlphanumeric(resultEpicToken.getMrn())) {
                                // System.out.println("\n\n\nMRN Return: " +
                                // resultEpicToken.getMrn());
                                LOGGER.tracef(
                                        "This epictoken request is from the DLScan mobile first app"
                                                + " referral source: %s",
                                        encryptedText);
                                if (StringUtils.isBlank(resultEpicToken.getRefsource())) {
                                    resultEpicToken.setRefsource("DLSCanApp");
                                }
                                return Uni.createFrom().item(resultEpicToken);
                            } else {
                                LOGGER.errorf(
                                        "It's not from the DLScan mobile first app referral source,"
                                            + " try the DLScan mobile second referral source: %s",
                                        encryptedText);
                                return aesRes.getMrnEpicToken_Async(
                                        encryptedText, keyDLScan, 0, "DLScanApp");
                            }
                        })
                .chain(
                        resultEpicToken -> {
                            if (StringUtils.isAlphanumeric(resultEpicToken.getMrn())) {
                                // System.out.println(
                                //        "\n\n\nThe DLScan Mobile Second App Token Return: "
                                //                + resultEpicToken);
                                LOGGER.tracef(
                                        "This epictoken request is from the DLScan mobile second"
                                                + " app referral source: %s",
                                        encryptedText);
                                if (StringUtils.isBlank(resultEpicToken.getRefsource())) {
                                    resultEpicToken.setRefsource("DLSCanApp");
                                }
                                return Uni.createFrom().item(resultEpicToken);
                            } else {
                                // System.out.println("\n\n\nDLScan Return: " + encryptedText);
                                LOGGER.errorf(
                                        "It's not from the DLScan mobile second app referral"
                                                + " source, try the NOTABLE referral source: %s",
                                        encryptedText);
                                return aesRes.getMrnEpicToken_Async(
                                        encryptedText, keyNotable, 0, "NOTABLE");
                            }
                        })
                .chain(
                        resultEpicToken -> {
                            if (StringUtils.isAlphanumeric(resultEpicToken.getMrn())) {
                                // System.out.println("\n\n\nNOTABLE Token Return: " +
                                // resultEpicToken);
                                // System.out.println(
                                //        "\n\n\nNOTABLE MRN Return: " + resultEpicToken.getMrn());
                                LOGGER.tracef(
                                        "This epictoken request is from the NOTABLE referral"
                                                + " source: %s",
                                        encryptedText);

                                if (StringUtils.isBlank(resultEpicToken.getRefsource())) {
                                    resultEpicToken.setRefsource("NOTABLE");
                                }
                                return Uni.createFrom().item(resultEpicToken);
                            } else {
                                // System.out.println("\n\n\nNOTABLE Return: " + encryptedText);
                                LOGGER.errorf(
                                        "NOTABLE EPICTOKEN REQUEST FAILED: %s", encryptedText);
                                return Uni.createFrom().item(resultEpicToken);
                            }
                        })
                .chain(
                        resultEpicToken -> {
                            if (StringUtils.isAlphanumeric(resultEpicToken.getMrn())
                                    && StringUtils.isNotBlank(resultEpicToken.getRefsource())) {
                                final String source = resultEpicToken.getRefsource();
                                final String mrn = resultEpicToken.getMrn();
                                int option = -1;
                                /*
                                1, SMS Campaign Option 1
                                2, SMS Campaign Option 2
                                3, SMS Campaign Option 3
                                4, SMS Campaign Option 4
                                5, In Our DNA SC Landing Page
                                6, In Our DNA SC Landing Page - ES
                                7, MyChart Initial Recruitment Message Consent Link
                                8, MyChart Reminder Recruitment  Consent Link
                                9, MyChart Research List Link - Being Identified
                                10, MyChart Interesting Consent Link
                                11, Interested Text Message
                                12, MUSC Horseshoe Employee Sign-Up Link
                                13, Community Event
                                14, Notable
                                15, DL Scan App
                                16, Paper Consented
                                17, SMS Campaign 2022
                                18, SMS Campaign Option 5 (Consent5)
                                19, SMS Campaign Option 6 (dna1)
                                20, SMS Campaign Option 7 (230919 dna)
                                21, Mammogram and Colonoscopy Cohorts
                                */

                                if (StringUtils.equalsIgnoreCase(source, "MyChartRecruitment")) {
                                    option = 5;
                                } else if (StringUtils.equalsIgnoreCase(
                                        source, "MyChartRecruitment0")) {
                                    option = 5;
                                } else if (StringUtils.equalsIgnoreCase(
                                        source, "MyChartRecruitment1")) {
                                    option = 1;
                                } else if (StringUtils.equalsIgnoreCase(
                                        source, "MyChartRecruitment2")) {
                                    option = 2;
                                } else if (StringUtils.equalsIgnoreCase(
                                        source, "MyChartRecruitment3")) {
                                    option = 3;
                                } else if (StringUtils.equalsIgnoreCase(
                                        source, "MyChartRecruitment4")) {
                                    option = 4;
                                } else if (StringUtils.equalsIgnoreCase(
                                        source, "MyChartRecruitment5")) {
                                    option = 5;
                                } else if (StringUtils.equalsIgnoreCase(
                                        source, "MyChartRecruitment6")) {
                                    option = 6;
                                } else if (StringUtils.equalsIgnoreCase(
                                        source, "MyChartRecruitment7")) {
                                    option = 7;
                                } else if (StringUtils.equalsIgnoreCase(
                                        source, "MyChartRecruitment8")) {
                                    option = 8;
                                } else if (StringUtils.equalsIgnoreCase(
                                        source, "MyChartRecruitment9")) {
                                    option = 9;
                                } else if (StringUtils.equalsIgnoreCase(
                                        source, "MyChartRecruitment10")) {
                                    option = 10;
                                } else if (StringUtils.equalsIgnoreCase(
                                        source, "MyChartRecruitment11")) {
                                    option = 11;
                                } else if (StringUtils.equalsIgnoreCase(
                                        source, "MyChartRecruitment14")) {
                                    option = 14;
                                } else if (StringUtils.equalsIgnoreCase(
                                        source, "MyChartRecruitment18")) {
                                    option = 18;
                                } else if (StringUtils.equalsIgnoreCase(
                                        source, "MyChartRecruitment21")) {
                                    option = 21;
                                }

                                modelTrackingRequest track = new modelTrackingRequest();
                                track.setUuid(uuid);
                                track.setTs(ts);
                                track.setMrn(mrn);
                                track.setEnv(env);
                                track.setToken(encryptedText);
                                track.setRecsource(source);
                                track.setOption(option);
                                /*
                                 * type: 0: send text message
                                 * type  1: recruitment
                                 * type  2: enroll
                                 * type  3: collect
                                 * type  4: ordered status change
                                 * type  5: follow up message
                                 */
                                track.setTimelinetype(2);
                                /*1, Identified
                                2, Interested
                                3, Enrolled
                                4, Declined
                                5, Withdrawn
                                6, Completed
                                7, Attempt
                                8, Enrolled but failed to update status in Epic*/
                                track.setEnrollstatus("7");
                                final String rstMsg =
                                        String.format(
                                                "{\"TransactionID\": \"%s\",\"TransactionTS\":"
                                                        + " \"%s\",\"TransactionEnv\": \"%s\","
                                                        + " \"TransactionJob\":"
                                                        + " \"%s\",\"TransactionType\": \"%s\","
                                                        + " \"TransactionStatus\": \"%s\","
                                                        + " \"TransactionContext\": \"%s-%s\"}",
                                                uuid,
                                                LocalDateTime.ofInstant(
                                                        Instant.now(), ZoneId.systemDefault()),
                                                env,
                                                "Decrypt/EpicToken/Tracking/Attempt",
                                                "UPDATE",
                                                "Captured/Authorized/Success",
                                                encryptedText,
                                                source);

                                LOGGER.tracef(rstMsg);
                                // LOGGER.tracef(
                                //        "This epictoken:%s request is from the referral source:
                                // %s;"
                                //                + " send the info to the tracking message queue.",
                                //        encryptedText, source);
                                trackingRes
                                        .Tracking_Async(env, track)
                                        .subscribe()
                                        .with(
                                                item -> System.out.println(item),
                                                failure ->
                                                        System.out.println(
                                                                "Failed with " + failure));

                            } else {
                                // System.out.println("\n\n\nNOTABLE Return: " + encryptedText);
                                LOGGER.errorf(
                                        "Could not find recruitment source: %s", encryptedText);
                            }
                            return Uni.createFrom().item(resultEpicToken);
                        })
                .onFailure()
                .recoverWithUni(
                        x -> {
                            System.out.println("\n\n\nRecover Return: " + x);
                            return aesRes.getMrnEpicToken_Async(
                                    encryptedText, keyNotable, 0, "NOTABLE");
                        });
    }

    /*
     *
     */
    private final modelPatReq setPatReq(
            final String uuid,
            final String env,
            final String agegroup,
            final String encryptedText,
            final boolean rtnsrc,
            final boolean rtnenrollstatus,
            final modelDecryptResp mrn) {
        modelPatReq patreq = new modelPatReq();
        if (mrn != null && StringUtils.isNotBlank(mrn.getMrn())) {
            patreq.setMrn(mrn.getMrn());
            patreq.setIss(mrn.getIss());
        } else if (mrn != null && StringUtils.isBlank(mrn.getMrn())) {
            patreq.setMsg(mrn.getMsg());
            patreq.setIss(mrn.getIss());
        }

        if (mrn != null && StringUtils.isNotBlank(mrn.getRefsource()) && rtnsrc) {
            patreq.setRefsource(mrn.getRefsource());
        }
        patreq.setDob("1900-01-01");
        patreq.setAgegroup(agegroup);
        patreq.setEnv(env);
        patreq.setUuid(uuid);
        patreq.setEncrypted(encryptedText);
        // Combine the result of our 2 Unis in a tuple
        // return Uni.createFrom().item(patreq);
        return patreq;
    }

    /*
     *
     */
    public Uni<Response> decrypt(
            @DefaultValue("test") @QueryParam("env") String env,
            @DefaultValue("adult") @QueryParam("agegroup") String agegroup, // all, adult, child
            @DefaultValue("false") @QueryParam("rtnsrc") boolean rtnsrc, // all, adult, child
            @DefaultValue("false") @QueryParam("rtnenrollstatus")
                    boolean rtnenrollstatus, // all, adult, child
            @Valid RerDecryption body)
            throws Exception {

        final String uuid = UUID.randomUUID().toString().replace("-", "");
        final String encryptedText = body.epicToken;
        if (encryptedText == null) {
            return Uni.createFrom().item(Response.status(400).build());
        }

        String message = "success";
        String rstErrorMsg = "failed";
        // String decrypted = "";
        String mrn = "";

        modelDecryptResp respDecrypt = new modelDecryptResp();
        respDecrypt.setEncrypted(encryptedText);

        try {
            LOGGER.infof(
                    "TransactionID: %s"
                            + " | TransactionTS: %s"
                            + " | TransactionEnv: %s"
                            + " | TransactionJob: %s"
                            + " | TransactionType: %s"
                            + " | TransactionStatus: %s"
                            + " | TransactionContext: %s",
                    uuid,
                    LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                    env,
                    "Decrypt/decrypt/Beginning",
                    "QUERY",
                    "Captured/Authorized/Begin",
                    encryptedText);
            if (encryptedText == null) {
                rstErrorMsg =
                        String.format(
                                "TransactionID: %s "
                                        + " | TransactionTS: %s"
                                        + "| TransactionEnv: %s | TransactionJob: %s |"
                                        + " TransactionType: %s | TransactionStatus: %s |"
                                        + " TransactionContext: %s",
                                uuid,
                                LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                                env,
                                "Decrypt/Processing/Exception",
                                "QUERY",
                                "Captured/Authorized/Failed/encrypted String is null",
                                encryptedText);
                LOGGER.error(rstErrorMsg);
            } else { // encryptedText != null
                var result = new AtomicReference<>();
                try {
                    /*
                       Try it with your specified algorithm, block size, IV size, and see what size output you get :-)
                       First it depends on the encoding of the input text. Is it UTF8? UTF16? Lets assume UTF8 so 1 Byte per character means 50 Bytes of input data to your encryption algorithm. (100 Bytes if UTF16)
                       Then you will pad to the Block Size for the algorithm. AES, regardless of key size is a block of 16 Bytes. So we will be padded out to 64 Bytes (Or 112 for UTF 16)
                       Then we need to store the IV and header information. So that is (usually, with default settings/IV sizes) another 16Bytes so we are at 80 Bytes (Or 128 for UTF16)
                       Finally we are encoding to Base64. I assume you want string length, since otherwise it is wasteful to make it into a string. So Base 64 bloats the string using the following formula: Ceil(bytes/3) * 4. So for us that is Ceil(80/3) = 27 * 4 = 108 characters (Or 172 for UTF 16)
                       Again this is all highly dependent on your choices of how you encrypt, what the text is encoded as, etc.
                       I would try it with your scenario before relying on these numbers for anything useful.
                    *
                    */
                    // From the mobile App
                    return decryptTokenChain_Async(
                                    uuid, env, agegroup, rtnsrc, rtnenrollstatus, encryptedText)
                            .flatMap(
                                    respDecrMRN -> {
                                        if (respDecrMRN != null
                                                && StringUtils.isAlphanumeric(
                                                        respDecrMRN.getMrn())) {
                                            modelPatReq patreq =
                                                    setPatReq(
                                                            uuid,
                                                            env,
                                                            agegroup,
                                                            encryptedText,
                                                            rtnsrc,
                                                            rtnenrollstatus,
                                                            respDecrMRN);
                                            // System.out.println(
                                            //        "\n\n\n"
                                            //            + "decrypt after decryptTokenChain_Async:"
                                            //            + " patreq: "
                                            //                + patreq
                                            //                + "env: "
                                            //                + env);
                                            return Uni.combine()
                                                    .all()
                                                    .unis(
                                                            Uni.createFrom().item(patreq),
                                                            fcRes.getNameByMRN(env, uuid, patreq),
                                                            fcRes.GetStudyStatusByMRN(
                                                                    env, uuid, patreq))
                                                    .combinedWith(
                                                            (s1, s2, s3) ->
                                                                    getRespDecrypt(
                                                                            s1,
                                                                            s2,
                                                                            s3,
                                                                            rtnsrc,
                                                                            rtnenrollstatus));

                                        } else {
                                            return Uni.createFrom()
                                                    .item(
                                                            Response.status(400)
                                                                    .entity(respDecrMRN)
                                                                    .build());
                                        }
                                    });

                } catch (IllegalBlockSizeException e) {
                    rstErrorMsg =
                            String.format(
                                    "TransactionID: %s "
                                            + " | TransactionTS: %s"
                                            + "| TransactionEnv: %s | TransactionJob: %s |"
                                            + " TransactionType: %s | TransactionStatus: %s |"
                                            + " TransactionContext: %s",
                                    uuid,
                                    LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                                    env,
                                    "Decrypt/Processing/IllegalBlockSizeException",
                                    "QUERY",
                                    "Captured/Authorized/Failed",
                                    encryptedText);
                    LOGGER.errorf(e, rstErrorMsg);
                    // e.printStackTrace(System.out);
                    // throw new RuntimeException(e.getCause());
                } catch (BadPaddingException e) {
                    rstErrorMsg =
                            String.format(
                                    "TransactionID: %s | "
                                            + " | TransactionTS: %s"
                                            + "TransactionEnv: %s | TransactionJob: %s |"
                                            + " TransactionType: %s | TransactionStatus: %s |"
                                            + " TransactionContext: %s",
                                    uuid,
                                    LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                                    env,
                                    "Decrypt/Processing/BadPaddingException",
                                    "QUERY",
                                    "Captured/Authorized/Failed",
                                    encryptedText);
                    LOGGER.errorf(e, rstErrorMsg);
                    // e.printStackTrace(System.out);
                    // throw new RuntimeException(e.getCause());
                } catch (Exception e) {
                    rstErrorMsg =
                            String.format(
                                    "TransactionID: %s "
                                            + "| TransactionEnv: %s | TransactionJob: %s |"
                                            + " TransactionType: %s | TransactionStatus: %s |"
                                            + " TransactionContext: %s",
                                    uuid,
                                    LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                                    env,
                                    "Decrypt/Processing/Exception",
                                    "QUERY",
                                    "Captured/Authorized/Failed",
                                    encryptedText);
                    LOGGER.errorf(e, rstErrorMsg);
                    // e.printStackTrace(System.out);
                    // throw new RuntimeException(e.getCause());
                }
            } // encryptedText is not null
        } catch (Exception e) {
            rstErrorMsg =
                    String.format(
                            "{\"TransactionID\": \"%s\","
                                    + "\"TransactionTS\": \"%s\","
                                    + "\"TransactionEnv\": \"%s\","
                                    + " \"TransactionJob\": \"%s\",\"TransactionType\": \"%s\","
                                    + " \"TransactionStatus\": \"%s\","
                                    + " \"TransactionContext\": \"%s\"}",
                            uuid,
                            LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()),
                            env,
                            "Decrypt/EpicToken/Processing/Exception",
                            "QUERY",
                            "Captured/Authorized/Failed",
                            encryptedText);
            LOGGER.errorf(e, rstErrorMsg);
            e.printStackTrace();
            // throw new RuntimeException(e.getCause());
        } // decrypt try
        return Uni.createFrom().item(Response.status(400).entity(respDecrypt).build());
    } // decrypt
}
