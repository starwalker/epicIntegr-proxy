package edu.musc.bi.crypto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

public class EncryptResp {

    @JsonProperty("message")
    public String msg;

    @JsonProperty("encrypted_string")
    public String encrypted;

    @JsonProperty("decrypted_string")
    public String decrypted;

    @JsonProperty("mrn")
    public String mrn;

    public EncryptResp() {}

    public EncryptResp(
            final String msg, final String encrypted, final String decrypted, final String mrn) {
        this.msg = msg;
        this.encrypted = encrypted;
        this.decrypted = decrypted;
        this.mrn = mrn;
    }

    public EncryptResp(Set<? extends ConstraintViolation<?>> violations) {
        this.msg = violations.stream().map(cv -> cv.getMessage()).collect(Collectors.joining(", "));
        this.decrypted = null;
        this.mrn = null;
    }
}
