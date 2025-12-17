
package edu.musc.bi.crtPatClient;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "coding",
    "text"
})
@Generated("jsonschema2pojo")
public class ValueCodeableConcept {

    @JsonProperty("coding")
    private List<Coding> coding = new ArrayList<Coding>();
    @JsonProperty("text")
    private String text;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ValueCodeableConcept() {
    }

    /**
     * 
     * @param coding
     * @param text
     */
    public ValueCodeableConcept(List<Coding> coding, String text) {
        super();
        this.coding = coding;
        this.text = text;
    }

    @JsonProperty("coding")
    public List<Coding> getCoding() {
        return coding;
    }

    @JsonProperty("coding")
    public void setCoding(List<Coding> coding) {
        this.coding = coding;
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
        sb.append(ValueCodeableConcept.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("coding");
        sb.append('=');
        sb.append(((this.coding == null)?"<null>":this.coding));
        sb.append(',');
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
        result = ((result* 31)+((this.coding == null)? 0 :this.coding.hashCode()));
        result = ((result* 31)+((this.text == null)? 0 :this.text.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ValueCodeableConcept) == false) {
            return false;
        }
        ValueCodeableConcept rhs = ((ValueCodeableConcept) other);
        return (((this.coding == rhs.coding)||((this.coding!= null)&&this.coding.equals(rhs.coding)))&&((this.text == rhs.text)||((this.text!= null)&&this.text.equals(rhs.text))));
    }

}
