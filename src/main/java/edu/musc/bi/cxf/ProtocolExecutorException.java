package edu.musc.bi.cxf;

import javax.xml.ws.WebFault;

@WebFault(name = "ProtocolExecutorFault")
public class ProtocolExecutorException extends Exception {

    private String faultInfo;

    public ProtocolExecutorException(String message) {
        super(message);
    }

    public ProtocolExecutorException(String message, String faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public String getFaultInfo() {
        return this.faultInfo;
    }
}
