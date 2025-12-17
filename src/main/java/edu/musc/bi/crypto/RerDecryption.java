package edu.musc.bi.crypto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RerDecryption {

    @JsonProperty("epic_token")
    @Size(min = 4, max = 256, message = "epic_token should have size [{min},{max}]")
    @NotBlank(message = "epic_token may not be blank!")
    public String epicToken;

    public RerDecryption() {}

    public RerDecryption(String epicToken) {
        this.epicToken = epicToken;
    }
}
