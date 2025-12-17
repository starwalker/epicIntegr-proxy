package edu.musc.bi.pdf417.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "ts",
    "client_phone_MIN",
    "ip",
    "phone",
    "email",
    "ssn",
    "pdf417",
    "linkageid",
    "dl_front",
    "dl_back",
    "pic",
    "idc_override",
    "netid",
})
@Generated("bmic-dev")
public class MobileAppRequest {

    @JsonProperty("ts")
    private String ts;

    @JsonProperty("client_phone_MIN")
    private String clientPhoneMIN;

    @JsonProperty("ip")
    private String ip;

    @Size(min = 10, max = 20, message = "Phone should have size [{min},{max}]")
    @NotNull(message = "The phone value must not be null")
    @NotBlank(message = "The phone value must not be blank")
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")
    @JsonProperty("phone")
    private String phone;

    @Email(message = "Please provide a valid email address")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "The PDF417 code must not be blank")
    @NotNull(message = "The PDF417 code cannot be null")
    @JsonProperty("pdf417")
    private String pdf417;

    @JsonProperty("linkageid")
    private String linkageid;

    @Pattern(regexp = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$")
    @JsonProperty("ssn")
    private String ssn;

    @JsonProperty("dl_front")
    private String dlFront;

    @JsonProperty("dl_back")
    private String dlBack;

    @JsonProperty("pic")
    private String pic;

    @JsonProperty("idc_override")
    private String idc_override;

    @JsonProperty("netid")
    private String netid;

    @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @param phone
     * @param ip
     * @param pdf417
     * @param linkageid
     * @param dlBack
     * @param dlFront
     * @param pic
     * @param clientPhoneMIN
     * @param email
     * @param ts
     * @param ssn
     * @param idc_override
     * @param netid
     */
    public MobileAppRequest(
            String phone,
            String ip,
            String pdf417,
            String linkageid,
            String dlBack,
            String dlFront,
            String pic,
            String clientPhoneMIN,
            String email,
            String ts,
            String ssn,
            String idc_override,
            String netid) {
        super();
        this.ts = ts;
        this.clientPhoneMIN = clientPhoneMIN;
        this.ip = ip;
        this.phone = phone;
        this.email = email;
        this.ssn = ssn;
        this.pdf417 = pdf417;
        this.linkageid = linkageid;
        this.dlFront = dlFront;
        this.dlBack = dlBack;
        this.pic = pic;
        this.idc_override = idc_override;
        this.netid = netid;
    }

    @JsonProperty("ts")
    public String getTs() {
        return ts;
    }

    @JsonProperty("ts")
    public void setTs(String ts) {
        this.ts = ts;
    }

    public MobileAppRequest withTs(String ts) {
        this.ts = ts;
        return this;
    }

    @JsonProperty("client_phone_MIN")
    public String getClientPhoneMIN() {
        return clientPhoneMIN;
    }

    @JsonProperty("client_phone_MIN")
    public void setClientPhoneMIN(String clientPhoneMIN) {
        this.clientPhoneMIN = clientPhoneMIN;
    }

    public MobileAppRequest withClientPhoneMIN(String clientPhoneMIN) {
        this.clientPhoneMIN = clientPhoneMIN;
        return this;
    }

    @JsonProperty("ip")
    public String getIp() {
        return ip;
    }

    @JsonProperty("ip")
    public void setIp(String ip) {
        this.ip = ip;
    }

    public MobileAppRequest withIp(String ip) {
        this.ip = ip;
        return this;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public MobileAppRequest withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public MobileAppRequest withEmail(String email) {
        this.email = email;
        return this;
    }

    @JsonProperty("ssn")
    public String getSsn() {
        return ssn;
    }

    @JsonProperty("ssn")
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public MobileAppRequest withSsn(String ssn) {
        this.ssn = ssn;
        return this;
    }

    @JsonProperty("pdf417")
    public String getPdf417() {
        return pdf417;
    }

    @JsonProperty("pdf417")
    public void setPdf417(String pdf417) {
        this.pdf417 = pdf417;
    }

    public MobileAppRequest withPdf417(String pdf417) {
        this.pdf417 = pdf417;
        return this;
    }

    @JsonProperty("linkageid")
    public String getLinkageid() {
        return linkageid;
    }

    @JsonProperty("linkageid")
    public void setLinkageid(String linkageid) {
        this.linkageid = linkageid;
    }

    public MobileAppRequest withLinkageid(String linkageid) {
        this.linkageid = linkageid;
        return this;
    }

    @JsonProperty("dl_front")
    public String getDlFront() {
        return dlFront;
    }

    @JsonProperty("dl_front")
    public void setDlFront(String dlFront) {
        this.dlFront = dlFront;
    }

    public MobileAppRequest withDlFront(String dlFront) {
        this.dlFront = dlFront;
        return this;
    }

    @JsonProperty("dl_back")
    public String getDlBack() {
        return dlBack;
    }

    @JsonProperty("dl_back")
    public void setDlBack(String dlBack) {
        this.dlBack = dlBack;
    }

    public MobileAppRequest withDlBack(String dlBack) {
        this.dlBack = dlBack;
        return this;
    }

    @JsonProperty("pic")
    public String getPic() {
        return pic;
    }

    @JsonProperty("pic")
    public void setPic(String pic) {
        this.pic = pic;
    }

    public MobileAppRequest withPic(String pic) {
        this.pic = pic;
        return this;
    }

    @JsonProperty("idc_override")
    public String getIdc_override() {
        return idc_override;
    }

    @JsonProperty("idc_override")
    public void setIdc_override(String idc_override) {
        this.idc_override = idc_override;
    }

    public MobileAppRequest withIdc_override(String idc_override) {
        this.idc_override = idc_override;
        return this;
    }

    @JsonProperty("netid")
    public String getNetid() {
        return netid;
    }

    @JsonProperty("netid")
    public void setNetid(String netid) {
        this.netid = netid;
    }

    public MobileAppRequest withNetid(String netid) {
        this.netid = netid;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public MobileAppRequest withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MobileAppRequest.class.getName())
                .append('@')
                .append(Integer.toHexString(System.identityHashCode(this)))
                .append('[');
        sb.append("ts");
        sb.append('=');
        sb.append(((this.ts == null) ? "<null>" : this.ts));
        sb.append(',');
        sb.append("clientPhoneMIN");
        sb.append('=');
        sb.append(((this.clientPhoneMIN == null) ? "<null>" : this.clientPhoneMIN));
        sb.append(',');
        sb.append("ip");
        sb.append('=');
        sb.append(((this.ip == null) ? "<null>" : this.ip));
        sb.append(',');
        sb.append("phone");
        sb.append('=');
        sb.append(((this.phone == null) ? "<null>" : this.phone));
        sb.append(',');
        sb.append("email");
        sb.append('=');
        sb.append(((this.email == null) ? "<null>" : this.email));
        sb.append(',');
        sb.append("ssn");
        sb.append('=');
        sb.append(((this.ssn == null) ? "<null>" : this.ssn));
        sb.append(',');
        sb.append("pdf417");
        sb.append('=');
        sb.append(((this.pdf417 == null) ? "<null>" : this.pdf417));
        sb.append(',');
        sb.append("linkageid");
        sb.append('=');
        sb.append(((this.linkageid == null) ? "<null>" : this.linkageid));
        sb.append(',');
        sb.append("dlFront");
        sb.append('=');
        sb.append(((this.dlFront == null) ? "<null>" : this.dlFront));
        sb.append(',');
        sb.append("dlBack");
        sb.append('=');
        sb.append(((this.dlBack == null) ? "<null>" : this.dlBack));
        sb.append(',');
        sb.append("pic");
        sb.append('=');
        sb.append(((this.pic == null) ? "<null>" : this.pic));
        sb.append("idc_override");
        sb.append('=');
        sb.append(((this.idc_override == null) ? "<null>" : this.idc_override));
        sb.append(',');
        sb.append("netid");
        sb.append('=');
        sb.append(((this.netid == null) ? "<null>" : this.netid));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null) ? "<null>" : this.additionalProperties));
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
        result = ((result * 31) + ((this.phone == null) ? 0 : this.phone.hashCode()));
        result = ((result * 31) + ((this.ip == null) ? 0 : this.ip.hashCode()));
        result = ((result * 31) + ((this.pdf417 == null) ? 0 : this.pdf417.hashCode()));
        result = ((result * 31) + ((this.linkageid == null) ? 0 : this.linkageid.hashCode()));
        result = ((result * 31) + ((this.dlBack == null) ? 0 : this.dlBack.hashCode()));
        result = ((result * 31) + ((this.dlFront == null) ? 0 : this.dlFront.hashCode()));
        result = ((result * 31) + ((this.pic == null) ? 0 : this.pic.hashCode()));
        result = ((result * 31) + ((this.idc_override == null) ? 0 : this.idc_override.hashCode()));
        result = ((result * 31) + ((this.netid == null) ? 0 : this.netid.hashCode()));
        result =
                ((result * 31)
                        + ((this.additionalProperties == null)
                                ? 0
                                : this.additionalProperties.hashCode()));
        result =
                ((result * 31)
                        + ((this.clientPhoneMIN == null) ? 0 : this.clientPhoneMIN.hashCode()));
        result = ((result * 31) + ((this.email == null) ? 0 : this.email.hashCode()));
        result = ((result * 31) + ((this.ts == null) ? 0 : this.ts.hashCode()));
        result = ((result * 31) + ((this.ssn == null) ? 0 : this.ssn.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MobileAppRequest) == false) {
            return false;
        }
        MobileAppRequest rhs = ((MobileAppRequest) other);
        return ((((((((((((this.phone == rhs.phone)
                                                                                                || ((this
                                                                                                                        .phone
                                                                                                                != null)
                                                                                                        && this
                                                                                                                .phone
                                                                                                                .equals(
                                                                                                                        rhs.phone)))
                                                                                        && ((this.ip
                                                                                                        == rhs.ip)
                                                                                                || ((this
                                                                                                                        .ip
                                                                                                                != null)
                                                                                                        && this
                                                                                                                .ip
                                                                                                                .equals(
                                                                                                                        rhs.ip))))
                                                                                && ((this.pdf417
                                                                                                == rhs.pdf417)
                                                                                        || ((this
                                                                                                                .pdf417
                                                                                                        != null)
                                                                                                && this
                                                                                                        .pdf417
                                                                                                        .equals(
                                                                                                                rhs.pdf417))))
                                                                        && ((this.linkageid
                                                                                        == rhs.linkageid)
                                                                                || ((this.linkageid
                                                                                                != null)
                                                                                        && this
                                                                                                .linkageid
                                                                                                .equals(
                                                                                                        rhs.linkageid)))
                                                                        && ((this.dlBack
                                                                                        == rhs.dlBack)
                                                                                || ((this.dlBack
                                                                                                != null)
                                                                                        && this
                                                                                                .dlBack
                                                                                                .equals(
                                                                                                        rhs.dlBack))))
                                                                && ((this.dlFront == rhs.dlFront)
                                                                        || ((this.dlFront != null)
                                                                                && this.dlFront
                                                                                        .equals(
                                                                                                rhs.dlFront))))
                                                        && ((this.pic == rhs.pic)
                                                                || ((this.pic != null)
                                                                        && this.pic.equals(
                                                                                rhs.pic))))
                                                && ((this.idc_override == rhs.idc_override)
                                                        || ((this.idc_override != null)
                                                                && this.idc_override.equals(
                                                                        rhs.idc_override)))
                                                && ((this.netid == rhs.netid)
                                                        || ((this.netid != null)
                                                                && this.netid.equals(rhs.netid)))
                                                && ((this.additionalProperties
                                                                == rhs.additionalProperties)
                                                        || ((this.additionalProperties != null)
                                                                && this.additionalProperties.equals(
                                                                        rhs.additionalProperties))))
                                        && ((this.clientPhoneMIN == rhs.clientPhoneMIN)
                                                || ((this.clientPhoneMIN != null)
                                                        && this.clientPhoneMIN.equals(
                                                                rhs.clientPhoneMIN))))
                                && ((this.email == rhs.email)
                                        || ((this.email != null) && this.email.equals(rhs.email))))
                        && ((this.ts == rhs.ts) || ((this.ts != null) && this.ts.equals(rhs.ts))))
                && ((this.ssn == rhs.ssn) || ((this.ssn != null) && this.ssn.equals(rhs.ssn))));
    }
}
