package edu.musc.bi.cxf;

import java.io.*;
import java.util.*;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.handler.MessageContext;

@WebService
public interface ProtocolExecutorWebService {

    @WebMethod
    public String getClientIP(final MessageContext msgCtxt);

    @WebMethod
    String reply(@WebParam(name = "text") String text);

    @WebMethod
    @RequestWrapper(
            localName = "EnrollPatientRequestRequest",
            targetNamespace = "urn:ihe:qrph:rpe:2009",
            className = "edu.musc.bi.cxf.EnrollPatientRequestRequest")
    String enrollPatientRequest(
            @WebParam(name = "processState") String status,
            @WebParam(name = "patient") EnrollPatientRequestRequest.Patient patient,
            @WebParam(name = "study") EnrollPatientRequestRequest.Study study)
            throws ProtocolExecutorException;

    @WebMethod
    @RequestWrapper(
            localName = "AlertProtocolState",
            targetNamespace = "urn:ihe:qrph:rpe:2009",
            className = "edu.musc.bi.cxf.AlertProtocolState")
    String alertProtocolState(
            @WebParam(name = "processState") String status,
            @WebParam(name = "patient") AlertProtocolState.Patient patient,
            @WebParam(name = "study") AlertProtocolState.Study study)
            throws ProtocolExecutorException;
}
