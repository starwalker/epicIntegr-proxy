package edu.musc.bi;

import edu.musc.bi.utils.dingzitime;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.protobuf.Timestamp;

import java.util.UUID;
import edu.musc.bi.aesclient.aesClientResource;
import edu.musc.bi.parseusdl.DriverLicense;
import edu.musc.bi.restclient.biApiResp;
import edu.musc.bi.restclient.fhirclientResource;
import edu.musc.bi.restclient.mrnInOurDNASC;
import edu.musc.bi.restclient.patDups;
import edu.musc.bi.restclient.modelPatDemogr;
import edu.musc.bi.sms.twilioLib;

import io.quarkus.arc.Arc;
import io.quarkus.arc.ManagedContext;
import io.quarkus.grpc.GrpcService;
import io.quarkus.grpc.RegisterInterceptor;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;

import org.apache.commons.lang3.StringUtils;

//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@GrpcService
@RegisterInterceptor(IncomingInterceptor.class)
public class biFhirEpicService extends MutinybiFhirEpicSVCGrpc.biFhirEpicSVCImplBase {

    AtomicInteger counter = new AtomicInteger();
    private static final ObjectMapper mapper = new JsonMapper();
    private static dingzitime dtime = new dingzitime();

    @ConfigProperty(name = "bi-key-dlscan")
    String keyDLScan;

    @ConfigProperty(name = "bi-key-notable")
    String keyNotable;

    @ConfigProperty(name = "bi-key-dlscan-first")
    String keyDLScanFirst;


    @Inject ContextChecker contextChecker;

    ManagedContext requestContext;

    @Inject
    fhirclientResource fcRes;
    twilioLib twilio;

    @PostConstruct
    public void setUp() {
        requestContext = Arc.container().requestContext();
        fcRes = new fhirclientResource();
        twilio = new twilioLib();
    }

    @Override
    public Uni<ptLookupReply> ptLookupPDF417(ptLookupRequest request) {
        final int MyChartStatus = 1;
        final boolean isProxyOnly = false;

        String uuid = UUID.randomUUID().toString().replace("-", "");

        final boolean env_prod = request.getEnvProd();

        final LocalDateTime curr_ts = LocalDateTime.now();
        final Timestamp activation_ts_start = dtime.convertLocalDateTimeToGoogleTimestamp(curr_ts);
        final Timestamp activation_ts_end =
                dtime.convertLocalDateTimeToGoogleTimestamp(curr_ts.plusMinutes(60));

        final String client_ts = dtime.convertDateTimeToString(curr_ts);
        final String client_deviceid = request.getDeviceName() == null ? "" : request.getDeviceName();
        final String client_netid = "";
        final String client_race = request.getRace() == null ? "" : request.getRace();
        final String client_ethnic = request.getEthnic() == null ? "" : request.getEthnic();

        final String musc_mrn = "WAIT";
        final String pdf417_or = request.getPdf417() == null ? "" : request.getPdf417();
        final String encryptedID = "WAIT";
        final String linkageID_Reply = "WAIT";
        final String return_code_default = "PARSE ERROR: " + request.getPdf417();
        final String phone = request.getPhone() == null ? "" : request.getPhone();
        final String email = request.getEmail() == null ? "" : request.getEmail();
        final String ssn = request.getSsn() == null ? "" : request.getSsn();
        final String idcOR = request.getIdcOverride() == null ? "" : request.getIdcOverride();
        final String activation_url = "WAIT";
        final String activation_code = "WAIT";
        final String activation_pre_populated_username = "WAIT";
        final int activation_duration_seconds = counter.incrementAndGet();
        Uni<ptLookupReply> res =
                Uni.createFrom()
                        .item(
                                ptLookupReply
                                        .newBuilder()
                                        .setMycMyChartStatus(MyChartStatus)
                                        .setMycIsProxyOnly(isProxyOnly)
                                        .setPdf417Or(pdf417_or)
                                        .setIdMuscMrn(musc_mrn)
                                        .setIdEncryptedID(encryptedID)
                                        .setIdLinkageID(linkageID_Reply)
                                        .setPhone(phone)
                                        .setEmail(email)
                                        .setReturnCode(return_code_default)
                                        .setActivationUrl(activation_url)
                                        .setActivationCode(activation_code)
                                        .setActivationPrePopulatedUsername(
                                                activation_pre_populated_username)
                                        .setActivationTsStart(activation_ts_start)
                                        .setActivationTsEnd(activation_ts_end)
                                        .setActivationDurationSeconds(activation_duration_seconds)
                                        .build());

        String last = "";
        String first = "";
        String middle = "";
        String dob = "";
        String addr = "";
        String city = "";
        String zip = "";
        String country = "";
        String sex = "";
        String fullname = "";
        String dups = "";
        String return_code = "";

        final String linkageid_clientSel =
                StringUtils.isBlank(request.getLinkageid()) ? "" : request.getLinkageid();
        if (!StringUtils.isBlank(linkageid_clientSel)) {
            fullname = sendSMS(null, linkageid_clientSel, env_prod, phone, email, "", 0);
            if (!StringUtils.isBlank(fullname)) {
                return_code = "PATIENT FOUND: " + fullname + " | Message sent to: " + phone;
            } else {
                return_code =
                        "PATIENT FOUND: "
                                + fullname
                                + " | the text message failed to send to "
                                + phone;
            }
            Log.infof("TransactionID: %s | netid: %s | ClientDeviceID: %s | TransactionType: %s | TransactionStatus: %s",
                    uuid, client_netid, client_deviceid, "PATLookUp/NoPerfectMatch/OneOfReturnsConfirmedByParticipant", "Completion: "+linkageid_clientSel);
        } else {
            final DriverLicense license = parseUSDL(request.getPdf417());
            if (license != null) {
                //Log.infof("The pdf417: {%s} is valid!", pdf417_or);
                last = license.getLastName() == null ? "" : license.getLastName();
                first = license.getFirstName() == null ? "" : license.getFirstName();
                middle = license.getMiddleName() == null ? "" : license.getMiddleName();
                dob = dtime.convertDateToString(license.getDOB());
                addr = license.getAddress() == null ? "" : license.getAddress();
                city = license.getCity() == null ? "" : license.getCity();
                zip = license.getZipCode() == null ? "" : license.getZipCode();
                country = license.getCountry() == null ? "" : license.getCountry();
                sex = license.getSex() == null ? "" : license.getSex();
                fullname = last + ", " + first + (StringUtils.isBlank(middle) ? "" : " " + middle);
                int retry = 0;
                final boolean crtIfNotExists = true;
                uuid = UUID.randomUUID().toString().replace("-", "");
                Uni<biApiResp> uniMrnResp = fcRes.patLookup_pdf417_SMS_Async(uuid, env_prod
                        , crtIfNotExists, license
                        , pdf417_or, phone
                        , email, client_race, client_ethnic
                        , ssn, idcOR, client_ts
                        , client_deviceid, client_netid);
            }
        }
        res =
                Uni.createFrom()
                        .item(
                                ptLookupReply
                                        .newBuilder()
                                        .setMycMyChartStatus(MyChartStatus)
                                        .setMycIsProxyOnly(isProxyOnly)
                                        .setPdf417Or(pdf417_or)
                                        .setIdMuscMrn(musc_mrn)
                                        .setIdEncryptedID(encryptedID)
                                        .setIdLinkageID(linkageID_Reply)
                                        .setPhone(phone)
                                        .setEmail(email)
                                        .setReturnCode(return_code)
                                        .setPatDups(dups)
                                        .setActivationUrl(activation_url)
                                        .setActivationCode(activation_code)
                                        .setActivationPrePopulatedUsername(
                                                activation_pre_populated_username)
                                        .setActivationTsStart(activation_ts_start)
                                        .setActivationTsEnd(activation_ts_end)
                                        .setActivationDurationSeconds(activation_duration_seconds)
                                        .setFirstname(first)
                                        .setLastName(last)
                                        .setMiddlename(middle)
                                        .setSex(sex)
                                        .setDob(dob)
                                        .setAddress(addr)
                                        .setCity(city)
                                        .setZip(zip)
                                        .setCountry(country)
                                        .build());
        return res;
    }
    //
    private boolean crtPat(
            DriverLicense license,
            final boolean env_prod,
            final String phone,
            final String email,
            final String race,
            final String ethnic,
            final String ssn,
            final String idcOR,
            final String client_ts,
            final String client_deviceid,
            final String client_netid,
            int retry) {
        try {
            String env = "test";
            String baseurl_Consent = "https://bmic-consent-s.musc.edu/inourdnasc/?epic_token=";

            if (env_prod) {
                env = "prod";
                baseurl_Consent = "https://consent.musc.edu/inourdnasc/inourdnasc/?epic_token=";
            }
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
            modelPatDemogr demo = new modelPatDemogr();
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
            demo.setSsn(ssn);
            demo.setLanguage("");
            demo.setIdc_override(idcOR);
            String fullname =
                    last + ", " + first + (StringUtils.isBlank(middle) ? "" : " " + middle);
            if (retry == 0) fcRes.crtPAT(env, demo);

            if (retry < 3) {
                final biApiResp mrnResp = getMRN(license, env_prod, phone, email, race, ethnic, ssn, "NO", client_ts, client_deviceid, client_netid, retry);

                if (mrnResp != null) {
                    final String type = mrnResp.getType();
                    if (type.equalsIgnoreCase("UNIQUE")) {
                        final String patIdRespBody = mrnResp.getMessage();
                        mrnInOurDNASC uniMrn = toMRN(patIdRespBody);
                        if (uniMrn != null) {
                            // System.out.println("uniMrn:" + uniMrn.getMrn());
                            final String mrn = uniMrn.getMrn();
                            final String linkageID = uniMrn.getLinkageid();
                            if (!StringUtils.isBlank(mrn) && !StringUtils.isBlank(linkageID)) {
                                sendSMS(mrnResp, "", env_prod, phone, email, first, retry);
                                Log.info(
                                        "The Message sent to : "
                                                + phone
                                                + " with link: "
                                                + linkageID);
                                return true;
                            } // linkageid is null
                        } // uniMrn is null
                    } else { // got mrn
                        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

                        exec.schedule(
                                new Runnable() {
                                    public void run() {
                                        if (retry < 3)
                                            crtPat(
                                                    license, env_prod, phone, email, race, ethnic,  ssn, "NO", client_ts, client_deviceid, client_netid,
                                                    retry + 1);
                                    }
                                },
                                1,
                                TimeUnit.SECONDS);
                    } // retry
                }
            }
        } catch (Exception e) {
            // throw new RuntimeException(e);
            Log.error("Create Patient Failed!", e);
            e.printStackTrace();
        }
        return false;
    }
    //
    //
    protected final String sendSMS(
            final biApiResp mrnResp,
            final String linkageid,
            final boolean env_prod,
            final String phone,
            final String email,
            final String name,
            int retry) {
        String res = "";
        String nameSMS = name;
        String baseurl_Consent = "https://bmic-consent-s.musc.edu/inourdnasc/?epic_token=";
        String url = "";
        if (env_prod) {
            baseurl_Consent = "https://consent.musc.edu/inourdnasc/?epic_token=";
        }
        try {
            // url = baseurl_Consent + URLEncoder.encode(linkageid, StandardCharsets.UTF_8.name());

            if (StringUtils.isBlank(name) && !StringUtils.isBlank(linkageid)) {
                final aesClientResource aesRes = new aesClientResource();
                final String id = aesRes.getMessage(linkageid, keyDLScan);
                //System.out.println("linkage message:" + id);
                if (!StringUtils.isBlank(id)) {
                    if (id.contains("|")) {
                        String[] arrOfMsg = id.split(Pattern.quote("|"));
                        if (arrOfMsg.length > 1) {
                            nameSMS = arrOfMsg[1];
                            //System.out.println("linkage message name:" + nameSMS);
                        }
                    }
                }
            }
            if (!StringUtils.isBlank(linkageid) && mrnResp == null) {
                //url = baseurl_Consent + URLEncoder.encode(linkageid, StandardCharsets.UTF_8.name());
                url = baseurl_Consent + linkageid;

                res = twilio.sendSMS(phone, url, nameSMS, env_prod);
                Log.info(
                        "ENV: "
                                + env_prod
                                + " - Patient: "
                                + nameSMS
                                + " found, message "
                                + url
                                + " has sent to "
                                + phone);
            } else if (mrnResp != null) {
                final String patIdRespBody = mrnResp.getMessage();
                mrnInOurDNASC uniMrn = toMRN(patIdRespBody);
                if (uniMrn != null) {
                    // System.out.println("uniMrn:" + uniMrn.getMrn());
                    final String mrn = uniMrn.getMrn();
                    final String linkageID = uniMrn.getLinkageid();
                    // System.out.println("uniMrn Linkageid:" + uniMrn.getLinkageid());
                    url =
                            baseurl_Consent
                                    + linkageID;
                    if (!StringUtils.isBlank(mrn) && !StringUtils.isBlank(linkageID)) {
                        res = twilio.sendSMS(phone, url, nameSMS, env_prod);
                        Log.info(
                                "ENV: "
                                        + env_prod
                                        + " - Patient: "
                                        + nameSMS
                                        + " found, message "
                                        + url
                                        + " has sent to "
                                        + phone);
                    }
                }
            } else {
                Log.error(
                        "SMS Failed, NO LINKAGE ID FOUND, NO MRN RESP FOUND! ENV: "
                                + env_prod
                                + " - Patient: "
                                + nameSMS
                                + " found, message "
                                + url
                                + " should be send to "
                                + phone);
            }

        } catch (Exception e) {
            Log.error(
                    "SMS Failed! ENV: "
                            + env_prod
                            + " - Patient: "
                            + nameSMS
                            + " found, message "
                            + url
                            + " should be send to "
                            + phone, e);
            e.printStackTrace();
        }
        return res;
    }

    //
    protected final biApiResp getMRN(
            DriverLicense license,
            final boolean env_prod,
            final String phone,
            final String email,
            final String race,
            final String ethnic,
            final String ssn,
            final String idcOR,
            final String client_ts,
            final String client_deviceid,
            final String client_netid,
            int retry) {
        biApiResp resp;
        try {
            String env = "test";
            String baseurl_Consent = "https://bmic-consent-s.musc.edu/inourdnasc/?epic_token=";

            if (env_prod) {
                env = "prod";
                baseurl_Consent = "https://consent.musc.edu/inourdnasc/?epic_token=";
            }
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
            modelPatDemogr demo = new modelPatDemogr();
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
            biApiResp mrnResp = fcRes.getMRN(env, demo);
            return mrnResp;
        } catch (Exception e) {
            Log.error("Chain has issue", e);
            e.printStackTrace();
        }
        return null;
    }

    private final mrnInOurDNASC toMRN(final String s) {
        try {
            if (checkArrayfromString(s)) {
                mrnInOurDNASC[] arr = mapper.readValue(s, mrnInOurDNASC[].class);
                if ( arr != null || arr.length == 0) {
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

    private biApiResp tobiApiResp(String s) {
        try {
            return mapper.readValue(s, biApiResp.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private patDups topatDups(String s) {
        try {
            return mapper.readValue(s, patDups.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //
    protected final DriverLicense parseUSDL(final String pdf417) {
        String barCode = "";
        String barCodeWithNo = "";
        DriverLicense license = null;
        try {
            license = new DriverLicense(pdf417);
        } catch (Exception e) {
            Log.error("PDF417 format issue");
            e.printStackTrace();
        }
        return license;
    }

    protected final boolean checkArray(final Object jsonObj) {
        boolean x = jsonObj.getClass().isArray();
        return x;
    }

    protected final boolean checkArrayfromString(final String jsonStr) {
        final String trimmed = StringUtils.trim(jsonStr);
        if (StringUtils.startsWith(trimmed, "[") && StringUtils.endsWith(trimmed, "]")) {
            return true;
        } else {
            return false;
        }
    }

}
