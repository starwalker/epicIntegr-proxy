
package edu.musc.bi.crtPatClient;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "use",
    "system",
    "value"
})
@Generated("jsonschema2pojo")
public class Identifier {

    @JsonProperty("use")
    private String use;
    @JsonProperty("system")
    private String system;
    @JsonProperty("value")
    private String value;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Identifier() {
    }

    /**
     * 
     * @param system
     * @param use
     * @param value
     */
    public Identifier(String use, String system, String value) {
        super();
        this.use = use;
        this.system = system;
        this.value = value;
    }

    @JsonProperty("use")
    public String getUse() {
        return use;
    }

    @JsonProperty("use")
    public void setUse(String use) {
        this.use = use;
    }

    @JsonProperty("system")
    public String getSystem() {
        return system;
    }

    @JsonProperty("system")
    public void setSystem(String system) {
        this.system = system;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Identifier.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("use");
        sb.append('=');
        sb.append(((this.use == null)?"<null>":this.use));
        sb.append(',');
        sb.append("system");
        sb.append('=');
        sb.append(((this.system == null)?"<null>":this.system));
        sb.append(',');
        sb.append("value");
        sb.append('=');
        sb.append(((this.value == null)?"<null>":this.value));
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
        result = ((result* 31)+((this.system == null)? 0 :this.system.hashCode()));
        result = ((result* 31)+((this.value == null)? 0 :this.value.hashCode()));
        result = ((result* 31)+((this.use == null)? 0 :this.use.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Identifier) == false) {
            return false;
        }
        Identifier rhs = ((Identifier) other);
        return ((((this.system == rhs.system)||((this.system!= null)&&this.system.equals(rhs.system)))&&((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value))))&&((this.use == rhs.use)||((this.use!= null)&&this.use.equals(rhs.use))));
    }

}
