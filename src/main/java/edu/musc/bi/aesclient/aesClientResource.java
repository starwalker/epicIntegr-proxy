package edu.musc.bi.aesclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import edu.musc.bi.crypto.modelDecryptResp;
import edu.musc.bi.restclient.modelPatReq;
import edu.musc.bi.utils.wdUtils;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestQuery;
import org.jboss.resteasy.reactive.client.api.QuarkusRestClientProperties;

import java.net.URI;
import java.time.*;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// @RequestScoped // important! the CDI framework will evaluate the property everytime this class is
// called and inject its most recent value
@Path("/ext")
@RolesAllowed({
    "iodsc",
    "bi-dev",
    "bi-admin",
    "bi-client",
    "bidev",
    "biadmin",
    "biclient",
    "bmicclient"
})
@ApplicationScoped
public class aesClientResource {

    private static final ObjectMapper mapper = new JsonMapper();
    protected static final AtomicBoolean flag = new AtomicBoolean(false);

    @Inject @RestClient aesClientService fcService;

    @ConfigProperty(name = "bi-gateway.threadpool.size", defaultValue = "50")
    int threadPoolSize;

    @ConfigProperty(name = "egw.aes.baseurl")
    String baseurl;

    @ConfigProperty(name = "bi-key-dlscan-first")
    String keyDLScanFirst;

    @ConfigProperty(name = "bi-key-dlscan")
    String keyDLScan;

    @ConfigProperty(name = "bi-key-notable")
    String keyNotable;

    /*
     *
     * for decrypt token from Mobile App
     *
     */
    public aesClientResource() {

        final Config config = ConfigProvider.getConfig();
        final String baseurl =
                wdUtils.getConfigValueString("egw.aes.baseurl");

        // System.out.println(baseurl);
        if (fcService == null) {
            fcService =
                    RestClientBuilder.newBuilder()
                            .baseUri(URI.create(baseurl))
                            .property("io.quarkus.rest.client.connection-pool-size", 50)
                            .property(QuarkusRestClientProperties.NAME, "bi-gw-client-group")
                            .property(QuarkusRestClientProperties.SHARED, true)
                            .build(aesClientService.class);
        }
    }

    /*
     *
     * for decrypt token from Mobile App
     *
     */
    private modelAESResp toRespAES(String s) {
        try {
            return mapper.readValue(s, modelAESResp.class);
        } catch (JsonProcessingException e) {
            // throw new RuntimeException(e);
            // throw new RuntimeException(e);
            Log.errorf(
                    e,
                    "Rest Client Chain:: toRespAES -- the response message has an issue!! body: %s",
                    s);
            e.printStackTrace();
        }
        return null;
    }

    /*
     *
     * for decrypt token from Mobile App
     *
     */
    private final Uni<modelDecryptResp> getMRNfromAES4Crypto(
            final modelAESResp respAES,
            final modelPatReq patreq,
            final int expdays,
            final String refsource) {
        modelDecryptResp respDecrypt = new modelDecryptResp();
        System.out.println("\r\n\r\n\r\nOUTPUT LOG");
        System.out.println(respAES);
        System.out.println(patreq);

        respDecrypt.setIss(1);
        String res = "";
        try {
            final String encrypted = patreq.getEncrypted();
            respDecrypt.setEncrypted(encrypted);
            List<String> msg = respAES.getMessage();
            if (msg.isEmpty()) {
                res =
                        String.format(
                                "Epic Token Request from External Apps: This encrypted string %s is"
                                        + " invalid! the msg list is empty! the response body: %s",
                                encrypted, respAES);
                System.out.println(res);
                Log.error(res);
            } else {
                final String strMsg = msg.get(0);
                String mrn = "";
                String strTS = "";
                String name = "";
                System.out.println(strMsg);
                if (StringUtils.isBlank(strMsg)) {
                    res =
                            String.format(
                                    "Epic Token Request from External Apps: This encrypted string"
                                            + " %s is invalid! the first item of list is empty! the"
                                            + " response body: %s",
                                    encrypted, respAES);

                    System.out.println(res);
                    Log.error(res);
                    respDecrypt.setMsg(res);
                } else {
                    if (strMsg.contains("|")) {
                        String[] arrOfMsg = strMsg.split(Pattern.quote("|"));
                        if (arrOfMsg.length > 2) {
                            mrn = arrOfMsg[0];
                            strTS = arrOfMsg[1];
                            name = arrOfMsg[2] == null ? "" : arrOfMsg[2];
                        } else if (arrOfMsg.length == 2) {
                            mrn = arrOfMsg[0];
                            strTS = arrOfMsg[1];
                        }

                        LocalDateTime to = LocalDateTime.now();
                        LocalDateTime from = convertStringToLocalDT(strTS);
                        Duration duration = Duration.between(from, to);
                        long diffInMins = duration.toMinutes();
                        long diffInHours = duration.toHours();

                        // x days
                        if (expdays > 0) {
                            if (duration.toHours() < expdays * 24) {
                                respDecrypt.setMrn(mrn);
                                respDecrypt.setIat(strTS);
                                respDecrypt.setName(name);
                                respDecrypt.setRefsource(refsource);
                                res =
                                        String.format(
                                                "Epic Token Request from External Apps: Got MRN %s"
                                                    + " from the encrypted string (the timestamp of"
                                                    + " creation of the Epic token: %s) from the"
                                                    + " mobile app: %s",
                                                mrn, strTS, encrypted);
                                Log.info(res);
                                System.out.println(res);
                                respDecrypt.setMsg(res);
                            } else { // if time out more than 30 mins
                                res =
                                        String.format(
                                                "This encrypted string %s has passed the"
                                                        + " expiration date: %s. The time"
                                                        + " difference is %s hours/8",
                                                encrypted, strTS, diffInHours);
                                System.out.println(res);
                                Log.error(res);
                                respDecrypt.setMsg(res);
                            } // token expires
                        } else { // expdays
                            respDecrypt.setMrn(mrn);
                            respDecrypt.setIat(strTS);
                            respDecrypt.setName(name);
                            respDecrypt.setRefsource(refsource);
                        }
                    } // if mesage contains |
                } // if message is empty
            } // if message is empty
        } catch (Exception e) {
            res =
                    String.format(
                            "Rest Client Chain:: toRespAES -- the response message has an issue!!"
                                    + " body: %s -- Reason: %s",
                            respAES, e);
            Log.errorf(
                    e,
                    "Rest Client Chain:: toRespAES -- the response message has an issue!! body: %s",
                    respAES);
            e.printStackTrace();
            respDecrypt.setMsg(res);
            // throw new RuntimeException(e);
        }
        return Uni.createFrom().item(respDecrypt);
    }
    /*
     *
     *
     *
     */
    public final modelAESReq setReqAES(final String encrypted, final String key) {
        modelAESReq reqAES = new modelAESReq();
        reqAES.setHost("0.0.0.0");
        reqAES.setService("svcs-aes256");
        reqAES.setAction("DAEdecrypt");
        reqAES.setVersion("0.1.0");

        Data data = new Data();
        data.setMode("CBC");
        // data.setSecret("wHQ@R0x3gW@2uu7R");
        // data.setSecret("7of@37y#riWfP36ox8X");
        // data.setSecret("9M45b*dgDwVP#MpXAe");
        data.setSecret(key);
        data.setKeysize(256);

        try {
            final String inputString =
                    // URLDecoder.decode(encrypted, StandardCharsets.UTF_8.toString());
                    java.net.URLDecoder.decode(encrypted, "UTF-8");
            // URLDecoder.decode(encrypted, StandardCharsets.UTF_8.name());
            // encrypted;

            List<String> list = Arrays.asList(inputString);
            // List<String> list = Arrays.asList(encrypted);
            data.setInput(list);
            reqAES.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
            final String errMsg =
                    String.format("Failed or unable to decrypt the Epic token: %s", encrypted);
            Log.error(errMsg);
            reqAES = null;
        }
        return reqAES;
    }
    /*
     *
     * Non Blocking -- get mrn from the Mobile app request.
     *
     */
    public Uni<modelDecryptResp> getMrnEpicToken(
            final String encrypted, final String key, final int expdays, final String refsource) {

        modelPatReq patreq = new modelPatReq();
        patreq.setEncrypted(encrypted);

        modelAESReq reqAES = setReqAES(encrypted, key);

        modelDecryptResp respDecrypt = new modelDecryptResp();
        respDecrypt.setMsg("");
        respDecrypt.setIss(1);
        System.out.println("\n\n\ngetMrnEpicToken: " + encrypted);

        return fcService
                .getMessage_Async(reqAES)
                .onItem()
                .ifNotNull()
                .transform(this::toRespAES)
                .chain(dataResp -> getMRNfromAES4Crypto(dataResp, patreq, expdays, refsource))
                .onItem()
                .ifNotNull()
                .transform(result -> result)
                .onItem()
                .ifNull()
                .continueWith(respDecrypt)
                .onFailure()
                .retry()
                .atMost(3);
    }

    /*
     *
     * Non Blocking -- get mrn from the Mobile app request.
     *
     */
    public Uni<modelDecryptResp> getMrnEpicToken_Async(
            final String encrypted, final String key, final int expdays, final String refsource) {

        modelPatReq patreq = new modelPatReq();
        patreq.setEncrypted(encrypted);

        modelAESReq reqAES = setReqAES(encrypted, key);

        modelDecryptResp respDecrypt = new modelDecryptResp();
        respDecrypt.setMsg("");
        respDecrypt.setIss(1);
        System.out.println(
                "\n\n\ngetMrnEpicTokenMobileApp_Async: " + encrypted + "    -- key: " + key);

        return fcService
                .getMessage_Async(reqAES)
                .onItem()
                .ifNotNull()
                .transform(this::toRespAES)
                .chain(dataResp -> getMRNfromAES4Crypto(dataResp, patreq, expdays, refsource))
                .onItem()
                .ifNotNull()
                .transform(result -> result)
                .onItem()
                .ifNull()
                .continueWith(respDecrypt)
                .onFailure()
                .retry()
                .atMost(3);
    }

    /*
     *
     *
     *
     */
    public Uni<modelDecryptResp> getCryptoMsg_Async(final String encrypted, final String key) {

        modelPatReq patreq = new modelPatReq();
        patreq.setEncrypted(encrypted);

        modelAESReq reqAES = setReqAES(encrypted, key);

        String res = "";
        if (fcService == null) {
            res =
                    String.format(
                            "Failed or unable to decrypt the Epic token: %s. -- Reason: the"
                                    + " fcService is down.",
                            encrypted);

            Log.error(res);
            return null;
        } else {
            return fcService
                    .getMessage_Async(reqAES)
                    .onItem()
                    .ifNotNull()
                    .transform(this::toRespAES)
                    .chain(dataResp -> getMRNfromAES4Crypto(dataResp, patreq, 0, "unknown"))
                    .onFailure()
                    .retry()
                    .atMost(3);
        }
    }

    /*
     *
     * for decrypt token from Mobile App
     *
     */
    @POST
    @Path("/inourdnasc/vendor/getmrnbytoken")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public final String getMessage(final String encrypted, @RestQuery final String key) {

        modelAESReq reqAES = new modelAESReq();
        reqAES.setHost("0.0.0.0");
        reqAES.setService("svcs-aes256");
        reqAES.setAction("DAEdecrypt");
        reqAES.setVersion("0.1.0");

        Data data = new Data();
        data.setMode("CBC");
        // data.setSecret("wHQ@R0x3gW@2uu7R");
        // data.setSecret("7of@37y#riWfP36ox8X");
        data.setSecret(key);
        data.setKeysize(256);

        try {
            final String inputString =
                    // URLDecoder.decode(encrypted, StandardCharsets.UTF_8.toString());
                    java.net.URLDecoder.decode(encrypted, "UTF-8");
            // URLDecoder.decode(encrypted, StandardCharsets.UTF_8.name());
            // encrypted;

            List<String> list = Arrays.asList(inputString);
            // List<String> list = Arrays.asList(encrypted);
            data.setInput(list);
            reqAES.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
            final String errMsg =
                    String.format("Failed or unable to decrypt the Epic token: %s", encrypted);
            Log.error(errMsg);
        }
        String res = "";
        if (fcService == null) {
            res =
                    String.format(
                            "Failed or unable to decrypt the Epic token: %s. -- Reason: the"
                                    + " fcService is down.",
                            encrypted);
        } else {
            System.out.format(
                    "aesClientResource - getMessage - encrypted: %s - before fcservice.getmessage :"
                            + " %s",
                    encrypted, reqAES);
            final String strResp = fcService.getMessage(reqAES);
            System.out.format(
                    "aesClientResource - getMessage - after fcservice.getmessage : %s", strResp);

            if (StringUtils.isBlank(strResp)) {
                res =
                        String.format(
                                "This encrypted string %s is invalid! no response! or the original"
                                        + " mrn is an empty string!",
                                encrypted);
                Log.error(res);
            } else {
                modelAESResp respAES = toRespAES(strResp);
                if (respAES == null) {
                    res =
                            String.format(
                                    "This encrypted string %s is invalid! Format issue!  the"
                                            + " response body: %s",
                                    encrypted, strResp);
                    Log.error(res);
                } else {
                    List<String> msg = respAES.getMessage();
                    // final String msg = res;
                    if (msg.isEmpty()) {
                        res =
                                String.format(
                                        "This encrypted string %s is invalid! No response list! the"
                                                + " response body: %s",
                                        encrypted, strResp);
                        Log.error(res);
                    } else {
                        final String strMsg = msg.get(0);
                        String mrn = "";
                        String strTS = "";
                        String name = "";
                        // System.out.println(strMsg);
                        if (StringUtils.isBlank(strMsg)) {
                            res =
                                    String.format(
                                            "This encrypted string %s is invalid! the first item of"
                                                + " response list is empty! the response body: %s",
                                            encrypted, strResp);
                            Log.error(res);
                        } else {
                            if (strMsg.contains("|")) {
                                String[] arrOfMsg = strMsg.split(Pattern.quote("|"));
                                if (arrOfMsg.length > 2) {
                                    mrn = arrOfMsg[0];
                                    strTS = arrOfMsg[1];
                                    name = arrOfMsg[2] == null ? "" : arrOfMsg[2];
                                } else if (arrOfMsg.length == 2) {
                                    mrn = arrOfMsg[0];
                                    strTS = arrOfMsg[1];
                                }
                                // System.out.println(mrn);
                                // System.out.println(strTS);

                                LocalDateTime to = LocalDateTime.now();
                                LocalDateTime from = convertStringToLocalDT(strTS);

                                Duration duration = Duration.between(from, to);

                                long diffInMins = duration.toMinutes();

                                // 8 hours
                                // if (duration.toMinutes() < 480) {
                                if (duration.toHours() < 192) {
                                    res = mrn + "|" + name;
                                    Log.info("got mrn from the encrypted string" + encrypted);
                                } else { // if time out more than 30 mins
                                    res =
                                            String.format(
                                                    "This encrypted string %s has passed the"
                                                            + " expiration date: %s. The time"
                                                            + " difference is %s",
                                                    encrypted, strTS, diffInMins);
                                    Log.error(res);
                                } // token expires
                            } // if mesage contains |
                        } // if message is empty
                    } // if message is empty
                } // if object modelAESResp is null
            } // StringUtils.isBlank(strResp))
        } // fcService == null
        // System.out.println(res);
        return res;
    }
    /*
     *
     * for decrypt token from Mobile App
     *
     */
    @POST
    @Path("/inourdnasc/vendor/bmic/mobile/getmrnbytoken")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public final String getMessage_mobile(final String encrypted) {

        modelAESReq reqAES = new modelAESReq();
        reqAES.setHost("0.0.0.0");
        reqAES.setService("svcs-aes256");
        reqAES.setAction("DAEdecrypt");
        reqAES.setVersion("0.1.0");

        Data data = new Data();
        data.setMode("CBC");
        // data.setSecret("wHQ@R0x3gW@2uu7R");
        // data.setSecret("7of@37y#riWfP36ox8X");
        data.setSecret(keyDLScan);
        data.setKeysize(256);

        try {
            final String inputString =
                    // URLDecoder.decode(encrypted, StandardCharsets.UTF_8.toString());
                    java.net.URLDecoder.decode(encrypted, "UTF-8");
            // URLDecoder.decode(encrypted, StandardCharsets.UTF_8.name());
            // encrypted;

            List<String> list = Arrays.asList(inputString);
            // List<String> list = Arrays.asList(encrypted);
            data.setInput(list);
            reqAES.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
            final String errMsg =
                    String.format("Failed or unable to decrypt the Epic token: %s", encrypted);
            Log.error(errMsg);
        }
        String res = "";
        if (fcService == null) {
            res =
                    String.format(
                            "Failed or unable to decrypt the Epic token: %s. -- Reason: the"
                                    + " fcService is down.",
                            encrypted);
        } else {
            System.out.format(
                    "aesClientResource - getMessage - encrypted: %s - before fcservice.getmessage :"
                            + " %s",
                    encrypted, reqAES);
            final String strResp = fcService.getMessage(reqAES);
            System.out.format(
                    "aesClientResource - getMessage - after fcservice.getmessage : %s", strResp);

            if (StringUtils.isBlank(strResp)) {
                res =
                        String.format(
                                "This encrypted string %s is invalid! no response! or the original"
                                        + " mrn is an empty string!",
                                encrypted);
                Log.error(res);
            } else {
                modelAESResp respAES = toRespAES(strResp);
                if (respAES == null) {
                    res =
                            String.format(
                                    "This encrypted string %s is invalid! Format issue!  the"
                                            + " response body: %s",
                                    encrypted, strResp);
                    Log.error(res);
                } else {
                    List<String> msg = respAES.getMessage();
                    // final String msg = res;
                    if (msg.isEmpty()) {
                        res =
                                String.format(
                                        "This encrypted string %s is invalid! No response list! the"
                                                + " response body: %s",
                                        encrypted, strResp);
                        Log.error(res);
                    } else {
                        final String strMsg = msg.get(0);
                        String mrn = "";
                        String strTS = "";
                        String name = "";
                        // System.out.println(strMsg);
                        if (StringUtils.isBlank(strMsg)) {
                            res =
                                    String.format(
                                            "This encrypted string %s is invalid! the first item of"
                                                + " response list is empty! the response body: %s",
                                            encrypted, strResp);
                            Log.error(res);
                        } else {
                            if (strMsg.contains("|")) {
                                String[] arrOfMsg = strMsg.split(Pattern.quote("|"));
                                if (arrOfMsg.length > 2) {
                                    mrn = arrOfMsg[0];
                                    strTS = arrOfMsg[1];
                                    name = arrOfMsg[2] == null ? "" : arrOfMsg[2];
                                } else if (arrOfMsg.length == 2) {
                                    mrn = arrOfMsg[0];
                                    strTS = arrOfMsg[1];
                                }
                                // System.out.println(mrn);
                                // System.out.println(strTS);

                                LocalDateTime to = LocalDateTime.now();
                                LocalDateTime from = convertStringToLocalDT(strTS);

                                Duration duration = Duration.between(from, to);

                                long diffInMins = duration.toMinutes();

                                // 8 hours
                                // if (duration.toMinutes() < 480) {
                                if (duration.toHours() < 192) {
                                    res = mrn + "|" + name;
                                    Log.info("got mrn from the encrypted string" + encrypted);
                                } else { // if time out more than 30 mins
                                    res =
                                            String.format(
                                                    "This encrypted string %s has passed the"
                                                            + " expiration date: %s. The time"
                                                            + " difference is %s",
                                                    encrypted, strTS, diffInMins);
                                    Log.error(res);
                                } // token expires
                            } // if mesage contains |
                        } // if message is empty
                    } // if message is empty
                } // if object modelAESResp is null
            } // StringUtils.isBlank(strResp))
        } // fcService == null
        // System.out.println(res);
        return res;
    }

    private LocalDateTime convertStringToLocalDT(final String strDate) {
        try {
            // 2022.02.23 09:33:22
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
            // String text = "2011-10-02 18:48:05.123";
            LocalDateTime dateTime = LocalDateTime.parse(strDate, formatter);
            // System.out.println(dateTime);

            return dateTime;
        } catch (Exception e) {
            // System.out.println("Exception :" + e);
            e.printStackTrace();
            return null;
        }
    }
}
