package edu.musc.bi.crypto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RerEncryption {

    @JsonProperty("EncryptMessage")
    @Size(
            min = 4,
            max = 256,
            message = "The Encrypt/Decrypt Message should have size [{min},{max}]")
    @NotBlank(message = "The Encrypt/Decrypt message may not be blank!")
    public String Message;

    public RerEncryption() {}

    public RerEncryption(String Message) {
        this.Message = Message;
    }
}
