package edu.musc.bi.cxf;

// import io.quarkiverse.cxf.annotation.CXFClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import edu.musc.bi.restclient.biApiResp;
import edu.musc.bi.restclient.fhirclientResource;
import edu.musc.bi.restclient.modelPatReq;
import edu.musc.bi.restclient.phoneFromEpic;
import edu.musc.bi.sms.twilioLib;

import io.quarkus.logging.Log;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
// import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.*;
import javax.xml.stream.*;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

@WebService(
        endpointInterface = "edu.musc.bi.cxf.ProtocolExecutorWebService",
        serviceName = "ProtocolExecutorWebService")
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
// @ApplicationScoped
public class ProtocolExecutorWebServiceImpl implements ProtocolExecutorWebService {

    private static final ObjectMapper mapper = new JsonMapper();

    @Inject fhirclientResource fcRes;

    twilioLib twilio;
    String hostname;

    @Resource WebServiceContext wsCtxt;

    @PostConstruct
    public void setUp() {
        // fcRes = new fhirclientResource();
        twilio = new twilioLib();
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            Log.error("Failed to get the hostname!", e);
            hostname = "UNKNOWN";
        }
    }

    private phoneFromEpic toPhone(String s) {
        try {
            return mapper.readValue(s, phoneFromEpic.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public final String getClientIP(final MessageContext msgCtxt) {
        HttpServletRequest req = (HttpServletRequest) msgCtxt.get(MessageContext.SERVLET_REQUEST);
        final String clientIP = req.getRemoteAddr();
        return clientIP;
    }

    @Override
    public String reply(@WebParam(name = "AlertProtocolState") String text) {
        return text;
    }

    private static final String getStudyCode(AlertProtocolState.Study study) {
        String rst = "";
        if (study != null) {
            System.out.println(study);
            AlertProtocolState.Study.Instantiation instantiation = study.getInstantiation();
            if (instantiation != null) {
                AlertProtocolState.Study.Instantiation.PlannedStudy plannedStudy =
                        instantiation.getPlannedStudy();
                if (plannedStudy != null) {
                    AlertProtocolState.Study.Instantiation.PlannedStudy.Id id =
                            plannedStudy.getId();
                    if (id != null) {
                        final String root = id.getRoot();
                        if (!StringUtils.isBlank(root)) {
                            if (StringUtils.equalsIgnoreCase(root, "1.1.8.7.7")) {
                                final String extension = id.getExtension();
                                if (!StringUtils.isBlank(extension)) {
                                    rst = extension;
                                }
                            }
                        }
                    }
                }
            }
        }
        return rst;
    }

    @Override
    public String enrollPatientRequest(
            @WebParam(name = "processState") String status,
            @WebParam(name = "patient") EnrollPatientRequestRequest.Patient patient,
            @WebParam(name = "study") EnrollPatientRequestRequest.Study study)
            throws ProtocolExecutorException {
        String strReply = "";
        MessageContext mctx = wsCtxt.getMessageContext();
        // get detail from request headers
        Map http_headers = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);

        try {
            if (StringUtils.equalsIgnoreCase(status, "Identified")) {
                final String ip = getClientIP(mctx);
                final String studycode = study.getStudyCode();
                String dob = "";
                if (StringUtils.equalsIgnoreCase(studycode, "STUDY220015")) {
                    Log.infov("STUDYCODE:{0}", studycode);
                    if (patient.getDob() != null) {
                        dob = patient.getDob().getValue();
                    }
                    EnrollPatientRequestRequest.Patient.CandidateID cid = patient.getCandidateID();
                    if (cid != null) {
                        final String root = cid.getRoot();
                        if (!StringUtils.isBlank(root)) {
                            if (StringUtils.equalsIgnoreCase(
                                    root, "1.2.840.114350.1.13.307.3.7.5.737384.14")) {
                                final String mrn = cid.getExtension();
                                String env = "";
                                if (StringUtils.equalsIgnoreCase(ip, "128.23.191.165")
                                        || StringUtils.containsIgnoreCase(hostname, "c3po-gungi")
                                        || StringUtils.containsIgnoreCase(hostname, ".local")
                                        || StringUtils.containsIgnoreCase(hostname, "wlan.musc.edu")
                                        || StringUtils.equalsIgnoreCase(ip, "127.0.0.1")
                                        || StringUtils.equalsIgnoreCase(ip, "10.33.178.59")) {
                                    env = "test";
                                    Log.infov(
                                            "ENV: {0} - Endpoint hostname: {1} - IP Address: {2} -"
                                                    + " STUDY:{3} - MRN:{4} - DOB:{5} -  The env is"
                                                    + " test!",
                                            env, hostname, ip, studycode, mrn, dob);
                                } else if (StringUtils.equalsIgnoreCase(ip, "128.23.110.156")
                                        || StringUtils.equalsIgnoreCase(ip, "128.23.191.233")
                                        || StringUtils.containsIgnoreCase(hostname, "ctms-api")) {
                                    env = "prod";
                                } else {
                                    env = "";
                                    Log.errorv(
                                            "ENV: {0} - Endpoint hostname: {1} - IP Address: {2} -"
                                                    + " STUDY:{3} - MRN:{4} - DOB:{5} -  The env is"
                                                    + " null!",
                                            env, hostname, ip, studycode, mrn, dob);
                                }
                            } // find mrn
                        } // !StringUtils.isBlank(root)
                    } // cid is null
                }
            }
        } catch (Exception e) {
            Log.error("Failed to send the text!", e);
        }
        strReply = "ALERT_RECEIVED";
        return strReply;
    }

    @Override
    public String alertProtocolState(
            @WebParam(name = "processState") String status,
            @WebParam(name = "patient") AlertProtocolState.Patient patient,
            @WebParam(name = "study") AlertProtocolState.Study study)
            throws ProtocolExecutorException {
        String strReply = "";
        MessageContext mctx = wsCtxt.getMessageContext();
        // get detail from request headers
        Map http_headers = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);

        try {
            if (StringUtils.equalsIgnoreCase(status, "Interested")) {
                final String ip = getClientIP(mctx);
                final String studycode = study.getStudyCode();
                String dob = "";
                if (StringUtils.equalsIgnoreCase(studycode, "STUDY220015")) {
                    Log.infov("STUDYCODE:{0}", studycode);
                    if (patient.getDob() != null) {
                        dob = patient.getDob().getValue();
                    }
                    AlertProtocolState.Patient.CandidateID cid = patient.getCandidateID();
                    if (cid != null) {
                        final String root = cid.getRoot();
                        if (!StringUtils.isBlank(root)) {
                            if (StringUtils.equalsIgnoreCase(
                                    root, "1.2.840.114350.1.13.307.3.7.5.737384.14")) {
                                final String mrn = cid.getExtension();
                                String env = "";
                                if (StringUtils.equalsIgnoreCase(ip, "128.23.191.165")
                                        || StringUtils.containsIgnoreCase(hostname, "c3po-gungi")
                                        || StringUtils.containsIgnoreCase(hostname, ".local")
                                        || StringUtils.containsIgnoreCase(hostname, "wlan.musc.edu")
                                        || StringUtils.equalsIgnoreCase(ip, "127.0.0.1")
                                        || StringUtils.equalsIgnoreCase(ip, "10.33.178.59")) {
                                    env = "test";
                                } else if (StringUtils.equalsIgnoreCase(ip, "128.23.110.156")
                                        || StringUtils.equalsIgnoreCase(ip, "128.23.191.233")
                                        || StringUtils.containsIgnoreCase(hostname, "ctms-api")) {
                                    env = "prod";
                                } else {
                                    env = "";
                                    Log.errorv(
                                            "ENV: {0} - Endpoint hostname: {1} - IP Address: {2} -"
                                                    + " STUDY:{3} - MRN:{4} - DOB:{5} -  The env is"
                                                    + " null!",
                                            env, hostname, ip, studycode, mrn, dob);
                                }
                                if (!StringUtils.isBlank(mrn) && !StringUtils.isBlank(env)) {
                                    Log.infov(
                                            "ENV: {0} - Endpoint hostname: {1} - IP Address: {2} -"
                                                    + " STUDY:{3} - MRN:{4} - DOB:{5}"
                                                    + " -  Calling API to send text message",
                                            env, hostname, ip, studycode, mrn, dob);
                                    final boolean env_prod =
                                            StringUtils.equalsIgnoreCase(env, "prod")
                                                    ? true
                                                    : false;
                                    final biApiResp phoneResp = getPhone(env_prod, mrn, dob);
                                    final String RespBody = phoneResp.getMessage();
                                    final String type = phoneResp.getType();
                                    if (type.equalsIgnoreCase("FOUND")) {
                                        phoneFromEpic phones = toPhone(RespBody);
                                        Log.infov(
                                                "Patient Lookup return: {0}",
                                                phoneResp == null ? "" : phoneResp.toString());
                                        if (!StringUtils.isBlank(phones.getPhone())) {
                                            final String phone = phones.getPhone();
                                            final String first = phones.getFirst();
                                            // sendSMS(env_prod, phone, first);
                                            strReply = "ALERT_RECEIVED";
                                            Log.infov(
                                                    "ENV: {0} - Endpoint hostname: {1} - IP"
                                                        + " Address: {2} - STUDY:{3} - MRN:{4} -"
                                                        + " DOB:{5} - The text has been sent to"
                                                        + " {6}",
                                                    env, hostname, ip, studycode, mrn, dob, phone);
                                        } else {
                                            Log.errorv(
                                                    "ENV: {0} - Endpoint hostname: {1} - IP"
                                                        + " Address: {2} - STUDY:{3} - MRN:{4} -"
                                                        + " DOB:{5} -  No phone is found!",
                                                    env, hostname, ip, studycode, mrn, dob);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (patient != null) {}
            }

        } catch (Exception e) {
            Log.error("Failed to send the text!", e);
        }

        return strReply;
    }

    //
    //
    protected final String sendSMS(final boolean env_prod, final String phone, final String name) {
        String res = "";
        String nameSMS = name;
        try {
            if (!StringUtils.isBlank(phone)) {
                res = twilio.sendSMSwithMyChartLink(phone, nameSMS, env_prod);
                Log.info(
                        "ENV: "
                                + env_prod
                                + " - Practitioner: "
                                + nameSMS
                                + " is found, message "
                                + " has sent to "
                                + phone);
            } else {
                Log.error(
                        "ENV: "
                                + env_prod
                                + " - Practitioner: "
                                + nameSMS
                                + " is found, the message "
                                + " is failed to send to "
                                + phone);
            }
        } catch (Exception e) {
            Log.error(
                    "SMS Failed! ENV: "
                            + env_prod
                            + " - Practitioner: "
                            + nameSMS
                            + " found, message "
                            + " can not be send to "
                            + phone,
                    e);
            e.printStackTrace();
        }
        return res;
    }
    //
    //
    protected final biApiResp getPhone(final boolean env_prod, final String mrn, final String dob) {
        biApiResp resp = null;
        try {
            String env = "test";
            if (env_prod) {
                env = "prod";
            }
            modelPatReq patreq = new modelPatReq();
            patreq.setMrn(mrn);
            patreq.setDob(dob);
            if (fcRes == null) {
                fcRes = new fhirclientResource();
            }
            resp = fcRes.getPhone(env, patreq);
        } catch (Exception e) {
            Log.error("Chain has issue", e);
            e.printStackTrace();
        }
        return resp;
    }
}
