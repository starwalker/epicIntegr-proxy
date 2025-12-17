
package edu.musc.bi.crtPatClient;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "text"
})
@Generated("jsonschema2pojo")
public class MaritalStatus {

    @JsonProperty("text")
    private String text;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MaritalStatus() {
    }

    /**
     * 
     * @param text
     */
    public MaritalStatus(String text) {
        super();
        this.text = text;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MaritalStatus.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("text");
        sb.append('=');
        sb.append(((this.text == null)?"<null>":this.text));
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
        result = ((result* 31)+((this.text == null)? 0 :this.text.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MaritalStatus) == false) {
            return false;
        }
        MaritalStatus rhs = ((MaritalStatus) other);
        return ((this.text == rhs.text)||((this.text!= null)&&this.text.equals(rhs.text)));
    }

}
