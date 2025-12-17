
package edu.musc.bi.crtPatClient;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "url",
    "valueCodeableConcept",
    "valueCode"
})
@Generated("jsonschema2pojo")
public class Extension {

    @JsonProperty("url")
    private String url;
    @JsonProperty("valueCodeableConcept")
    private ValueCodeableConcept valueCodeableConcept;
    @JsonProperty("valueCode")
    private String valueCode;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Extension() {
    }

    /**
     * 
     * @param valueCodeableConcept
     * @param valueCode
     * @param url
     */
    public Extension(String url, ValueCodeableConcept valueCodeableConcept, String valueCode) {
        super();
        this.url = url;
        this.valueCodeableConcept = valueCodeableConcept;
        this.valueCode = valueCode;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("valueCodeableConcept")
    public ValueCodeableConcept getValueCodeableConcept() {
        return valueCodeableConcept;
    }

    @JsonProperty("valueCodeableConcept")
    public void setValueCodeableConcept(ValueCodeableConcept valueCodeableConcept) {
        this.valueCodeableConcept = valueCodeableConcept;
    }

    @JsonProperty("valueCode")
    public String getValueCode() {
        return valueCode;
    }

    @JsonProperty("valueCode")
    public void setValueCode(String valueCode) {
        this.valueCode = valueCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Extension.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("url");
        sb.append('=');
        sb.append(((this.url == null)?"<null>":this.url));
        sb.append(',');
        sb.append("valueCodeableConcept");
        sb.append('=');
        sb.append(((this.valueCodeableConcept == null)?"<null>":this.valueCodeableConcept));
        sb.append(',');
        sb.append("valueCode");
        sb.append('=');
        sb.append(((this.valueCode == null)?"<null>":this.valueCode));
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
        result = ((result* 31)+((this.valueCodeableConcept == null)? 0 :this.valueCodeableConcept.hashCode()));
        result = ((result* 31)+((this.valueCode == null)? 0 :this.valueCode.hashCode()));
        result = ((result* 31)+((this.url == null)? 0 :this.url.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Extension) == false) {
            return false;
        }
        Extension rhs = ((Extension) other);
        return ((((this.valueCodeableConcept == rhs.valueCodeableConcept)||((this.valueCodeableConcept!= null)&&this.valueCodeableConcept.equals(rhs.valueCodeableConcept)))&&((this.valueCode == rhs.valueCode)||((this.valueCode!= null)&&this.valueCode.equals(rhs.valueCode))))&&((this.url == rhs.url)||((this.url!= null)&&this.url.equals(rhs.url))));
    }

}
