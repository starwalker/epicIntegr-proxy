
package edu.musc.bi.crtPatClient;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "use",
    "family",
    "given"
})
@Generated("jsonschema2pojo")
public class Name {

    @JsonProperty("use")
    private String use;
    @JsonProperty("family")
    private String family;
    @JsonProperty("given")
    private List<String> given = new ArrayList<String>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Name() {
    }

    /**
     * 
     * @param given
     * @param use
     * @param family
     */
    public Name(String use, String family, List<String> given) {
        super();
        this.use = use;
        this.family = family;
        this.given = given;
    }

    @JsonProperty("use")
    public String getUse() {
        return use;
    }

    @JsonProperty("use")
    public void setUse(String use) {
        this.use = use;
    }

    @JsonProperty("family")
    public String getFamily() {
        return family;
    }

    @JsonProperty("family")
    public void setFamily(String family) {
        this.family = family;
    }

    @JsonProperty("given")
    public List<String> getGiven() {
        return given;
    }

    @JsonProperty("given")
    public void setGiven(List<String> given) {
        this.given = given;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Name.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("use");
        sb.append('=');
        sb.append(((this.use == null)?"<null>":this.use));
        sb.append(',');
        sb.append("family");
        sb.append('=');
        sb.append(((this.family == null)?"<null>":this.family));
        sb.append(',');
        sb.append("given");
        sb.append('=');
        sb.append(((this.given == null)?"<null>":this.given));
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
        result = ((result* 31)+((this.given == null)? 0 :this.given.hashCode()));
        result = ((result* 31)+((this.family == null)? 0 :this.family.hashCode()));
        result = ((result* 31)+((this.use == null)? 0 :this.use.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Name) == false) {
            return false;
        }
        Name rhs = ((Name) other);
        return ((((this.given == rhs.given)||((this.given!= null)&&this.given.equals(rhs.given)))&&((this.family == rhs.family)||((this.family!= null)&&this.family.equals(rhs.family))))&&((this.use == rhs.use)||((this.use!= null)&&this.use.equals(rhs.use))));
    }

}
