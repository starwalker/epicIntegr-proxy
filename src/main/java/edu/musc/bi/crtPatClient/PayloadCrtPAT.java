
package edu.musc.bi.crtPatClient;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "resourceType",
    "identifier",
    "active",
    "name",
    "telecom",
    "gender",
    "birthDate",
    "address",
    "maritalStatus",
    "generalPractitioner",
    "extension"
})
@Generated("jsonschema2pojo")
public class PayloadCrtPAT {

    @JsonProperty("resourceType")
    private String resourceType;
    @JsonProperty("identifier")
    private List<Identifier> identifier = new ArrayList<Identifier>();
    @JsonProperty("active")
    private String active;
    @JsonProperty("name")
    private List<Name> name = new ArrayList<Name>();
    @JsonProperty("telecom")
    private List<Telecom> telecom = new ArrayList<Telecom>();
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("birthDate")
    private String birthDate;
    @JsonProperty("address")
    private List<Address> address = new ArrayList<Address>();
    @JsonProperty("maritalStatus")
    private MaritalStatus maritalStatus;
    @JsonProperty("generalPractitioner")
    private List<GeneralPractitioner> generalPractitioner = new ArrayList<GeneralPractitioner>();
    @JsonProperty("extension")
    private List<Extension> extension = new ArrayList<Extension>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public PayloadCrtPAT() {
    }

    /**
     * 
     * @param identifier
     * @param extension
     * @param address
     * @param gender
     * @param generalPractitioner
     * @param name
     * @param active
     * @param telecom
     * @param birthDate
     * @param maritalStatus
     * @param resourceType
     */
    public PayloadCrtPAT(String resourceType, List<Identifier> identifier, String active, List<Name> name, List<Telecom> telecom, String gender, String birthDate, List<Address> address, MaritalStatus maritalStatus, List<GeneralPractitioner> generalPractitioner, List<Extension> extension) {
        super();
        this.resourceType = resourceType;
        this.identifier = identifier;
        this.active = active;
        this.name = name;
        this.telecom = telecom;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
        this.maritalStatus = maritalStatus;
        this.generalPractitioner = generalPractitioner;
        this.extension = extension;
    }

    @JsonProperty("resourceType")
    public String getResourceType() {
        return resourceType;
    }

    @JsonProperty("resourceType")
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    @JsonProperty("identifier")
    public List<Identifier> getIdentifier() {
        return identifier;
    }

    @JsonProperty("identifier")
    public void setIdentifier(List<Identifier> identifier) {
        this.identifier = identifier;
    }

    @JsonProperty("active")
    public String getActive() {
        return active;
    }

    @JsonProperty("active")
    public void setActive(String active) {
        this.active = active;
    }

    @JsonProperty("name")
    public List<Name> getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(List<Name> name) {
        this.name = name;
    }

    @JsonProperty("telecom")
    public List<Telecom> getTelecom() {
        return telecom;
    }

    @JsonProperty("telecom")
    public void setTelecom(List<Telecom> telecom) {
        this.telecom = telecom;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("birthDate")
    public String getBirthDate() {
        return birthDate;
    }

    @JsonProperty("birthDate")
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @JsonProperty("address")
    public List<Address> getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(List<Address> address) {
        this.address = address;
    }

    @JsonProperty("maritalStatus")
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    @JsonProperty("maritalStatus")
    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    @JsonProperty("generalPractitioner")
    public List<GeneralPractitioner> getGeneralPractitioner() {
        return generalPractitioner;
    }

    @JsonProperty("generalPractitioner")
    public void setGeneralPractitioner(List<GeneralPractitioner> generalPractitioner) {
        this.generalPractitioner = generalPractitioner;
    }

    @JsonProperty("extension")
    public List<Extension> getExtension() {
        return extension;
    }

    @JsonProperty("extension")
    public void setExtension(List<Extension> extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(PayloadCrtPAT.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("resourceType");
        sb.append('=');
        sb.append(((this.resourceType == null)?"<null>":this.resourceType));
        sb.append(',');
        sb.append("identifier");
        sb.append('=');
        sb.append(((this.identifier == null)?"<null>":this.identifier));
        sb.append(',');
        sb.append("active");
        sb.append('=');
        sb.append(((this.active == null)?"<null>":this.active));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("telecom");
        sb.append('=');
        sb.append(((this.telecom == null)?"<null>":this.telecom));
        sb.append(',');
        sb.append("gender");
        sb.append('=');
        sb.append(((this.gender == null)?"<null>":this.gender));
        sb.append(',');
        sb.append("birthDate");
        sb.append('=');
        sb.append(((this.birthDate == null)?"<null>":this.birthDate));
        sb.append(',');
        sb.append("address");
        sb.append('=');
        sb.append(((this.address == null)?"<null>":this.address));
        sb.append(',');
        sb.append("maritalStatus");
        sb.append('=');
        sb.append(((this.maritalStatus == null)?"<null>":this.maritalStatus));
        sb.append(',');
        sb.append("generalPractitioner");
        sb.append('=');
        sb.append(((this.generalPractitioner == null)?"<null>":this.generalPractitioner));
        sb.append(',');
        sb.append("extension");
        sb.append('=');
        sb.append(((this.extension == null)?"<null>":this.extension));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.identifier == null)? 0 :this.identifier.hashCode()));
        result = ((result* 31)+((this.extension == null)? 0 :this.extension.hashCode()));
        result = ((result* 31)+((this.address == null)? 0 :this.address.hashCode()));
        result = ((result* 31)+((this.gender == null)? 0 :this.gender.hashCode()));
        result = ((result* 31)+((this.generalPractitioner == null)? 0 :this.generalPractitioner.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.active == null)? 0 :this.active.hashCode()));
        result = ((result* 31)+((this.telecom == null)? 0 :this.telecom.hashCode()));
        result = ((result* 31)+((this.birthDate == null)? 0 :this.birthDate.hashCode()));
        result = ((result* 31)+((this.maritalStatus == null)? 0 :this.maritalStatus.hashCode()));
        result = ((result* 31)+((this.resourceType == null)? 0 :this.resourceType.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PayloadCrtPAT) == false) {
            return false;
        }
        PayloadCrtPAT rhs = ((PayloadCrtPAT) other);
        return ((((((((((((this.identifier == rhs.identifier)||((this.identifier!= null)&&this.identifier.equals(rhs.identifier)))&&((this.extension == rhs.extension)||((this.extension!= null)&&this.extension.equals(rhs.extension))))&&((this.address == rhs.address)||((this.address!= null)&&this.address.equals(rhs.address))))&&((this.gender == rhs.gender)||((this.gender!= null)&&this.gender.equals(rhs.gender))))&&((this.generalPractitioner == rhs.generalPractitioner)||((this.generalPractitioner!= null)&&this.generalPractitioner.equals(rhs.generalPractitioner))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.active == rhs.active)||((this.active!= null)&&this.active.equals(rhs.active))))&&((this.telecom == rhs.telecom)||((this.telecom!= null)&&this.telecom.equals(rhs.telecom))))&&((this.birthDate == rhs.birthDate)||((this.birthDate!= null)&&this.birthDate.equals(rhs.birthDate))))&&((this.maritalStatus == rhs.maritalStatus)||((this.maritalStatus!= null)&&this.maritalStatus.equals(rhs.maritalStatus))))&&((this.resourceType == rhs.resourceType)||((this.resourceType!= null)&&this.resourceType.equals(rhs.resourceType))));
    }

}
