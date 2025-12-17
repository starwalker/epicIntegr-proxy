package edu.musc.bi.crypto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ModelCryptoFuncRqst {

    @JsonProperty("message")
    @Size(
            min = 4,
            max = 512,
            message = "The Encrypt/Decrypt message should have size [{min},{max}]")
    @NotBlank(message = "The Encrypt/Decrypt message may not be blank!")
    public String message;

    @JsonProperty("cipher")
    @Size(min = 3, max = 256, message = "The Cipher's name should have size [{min},{max}]")
    @NotBlank(message = "The Cipher's name may not be blank!")
    public String cipher;

    @JsonProperty("secretkey")
    @Size(min = 2, max = 256, message = "The secret key should have size [{min},{max}]")
    @NotBlank(message = "The secret key may not be blank!")
    public String key;

    @JsonProperty("action")
    @Size(
            min = 2,
            max = 256,
            message = "The action type (encrypt or decrypt) should have size [{min},{max}]")
    @NotBlank(message = "The action type (encrypt or decrypt) may not be blank!")
    public String action;

    public ModelCryptoFuncRqst() {}

    public ModelCryptoFuncRqst(
            final String message, final String cipher, final String key, final String action) {
        this.message = message;
        this.cipher = cipher;
        this.key = key;
        this.action = action;
    }
}
