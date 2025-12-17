package edu.musc.bi.aesclient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"host", "service", "action", "version", "data"})
@Generated("jsonschema2pojo")
public class modelAESReq {

    @JsonProperty("host")
    private String host;

    @JsonProperty("service")
    private String service;

    @JsonProperty("action")
    private String action;

    @JsonProperty("version")
    private String version;

    @JsonProperty("data")
    private Data data;

    /** No args constructor for use in serialization */
    public modelAESReq() {}

    /**
     * @param data
     * @param service
     * @param host
     * @param action
     * @param version
     */
    public modelAESReq(String host, String service, String action, String version, Data data) {
        super();
        this.host = host;
        this.service = service;
        this.action = action;
        this.version = version;
        this.data = data;
    }

    @JsonProperty("host")
    public String getHost() {
        return host;
    }

    @JsonProperty("host")
    public void setHost(String host) {
        this.host = host;
    }

    @JsonProperty("service")
    public String getService() {
        return service;
    }

    @JsonProperty("service")
    public void setService(String service) {
        this.service = service;
    }

    @JsonProperty("action")
    public String getAction() {
        return action;
    }

    @JsonProperty("action")
    public void setAction(String action) {
        this.action = action;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    @JsonProperty("data")
    public Data getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(modelAESReq.class.getName())
                .append('@')
                .append(Integer.toHexString(System.identityHashCode(this)))
                .append('[');
        sb.append("host");
        sb.append('=');
        sb.append(((this.host == null) ? "<null>" : this.host));
        sb.append(',');
        sb.append("service");
        sb.append('=');
        sb.append(((this.service == null) ? "<null>" : this.service));
        sb.append(',');
        sb.append("action");
        sb.append('=');
        sb.append(((this.action == null) ? "<null>" : this.action));
        sb.append(',');
        sb.append("version");
        sb.append('=');
        sb.append(((this.version == null) ? "<null>" : this.version));
        sb.append(',');
        sb.append("data");
        sb.append('=');
        sb.append(((this.data == null) ? "<null>" : this.data));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.host == null) ? 0 : this.host.hashCode()));
        result = ((result * 31) + ((this.action == null) ? 0 : this.action.hashCode()));
        result = ((result * 31) + ((this.data == null) ? 0 : this.data.hashCode()));
        result = ((result * 31) + ((this.version == null) ? 0 : this.version.hashCode()));
        result = ((result * 31) + ((this.service == null) ? 0 : this.service.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof modelAESReq) == false) {
            return false;
        }
        modelAESReq rhs = ((modelAESReq) other);
        return ((((((this.host == rhs.host) || ((this.host != null) && this.host.equals(rhs.host)))
                                        && ((this.action == rhs.action)
                                                || ((this.action != null)
                                                        && this.action.equals(rhs.action))))
                                && ((this.data == rhs.data)
                                        || ((this.data != null) && this.data.equals(rhs.data))))
                        && ((this.version == rhs.version)
                                || ((this.version != null) && this.version.equals(rhs.version))))
                && ((this.service == rhs.service)
                        || ((this.service != null) && this.service.equals(rhs.service))));
    }
}
