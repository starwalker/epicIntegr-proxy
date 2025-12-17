
package edu.musc.bi.aesclient;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Mode",
    "Secret",
    "Keysize",
    "Input"
})
@Generated("jsonschema2pojo")
public class Data {

    @JsonProperty("Mode")
    private String mode;
    @JsonProperty("Secret")
    private String secret;
    @JsonProperty("Keysize")
    private Integer keysize;
    @JsonProperty("Input")
    private List<String> input = new ArrayList<String>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param mode
     * @param input
     * @param keysize
     * @param secret
     */
    public Data(String mode, String secret, Integer keysize, List<String> input) {
        super();
        this.mode = mode;
        this.secret = secret;
        this.keysize = keysize;
        this.input = input;
    }

    @JsonProperty("Mode")
    public String getMode() {
        return mode;
    }

    @JsonProperty("Mode")
    public void setMode(String mode) {
        this.mode = mode;
    }

    @JsonProperty("Secret")
    public String getSecret() {
        return secret;
    }

    @JsonProperty("Secret")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @JsonProperty("Keysize")
    public Integer getKeysize() {
        return keysize;
    }

    @JsonProperty("Keysize")
    public void setKeysize(Integer keysize) {
        this.keysize = keysize;
    }

    @JsonProperty("Input")
    public List<String> getInput() {
        return input;
    }

    @JsonProperty("Input")
    public void setInput(List<String> input) {
        this.input = input;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Data.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("mode");
        sb.append('=');
        sb.append(((this.mode == null)?"<null>":this.mode));
        sb.append(',');
        sb.append("secret");
        sb.append('=');
        sb.append(((this.secret == null)?"<null>":this.secret));
        sb.append(',');
        sb.append("keysize");
        sb.append('=');
        sb.append(((this.keysize == null)?"<null>":this.keysize));
        sb.append(',');
        sb.append("input");
        sb.append('=');
        sb.append(((this.input == null)?"<null>":this.input));
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
        result = ((result* 31)+((this.mode == null)? 0 :this.mode.hashCode()));
        result = ((result* 31)+((this.input == null)? 0 :this.input.hashCode()));
        result = ((result* 31)+((this.secret == null)? 0 :this.secret.hashCode()));
        result = ((result* 31)+((this.keysize == null)? 0 :this.keysize.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Data) == false) {
            return false;
        }
        Data rhs = ((Data) other);
        return (((((this.mode == rhs.mode)||((this.mode!= null)&&this.mode.equals(rhs.mode)))&&((this.input == rhs.input)||((this.input!= null)&&this.input.equals(rhs.input))))&&((this.secret == rhs.secret)||((this.secret!= null)&&this.secret.equals(rhs.secret))))&&((this.keysize == rhs.keysize)||((this.keysize!= null)&&this.keysize.equals(rhs.keysize))));
    }

}
