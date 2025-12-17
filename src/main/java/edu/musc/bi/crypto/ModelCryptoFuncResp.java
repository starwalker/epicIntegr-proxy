package edu.musc.bi.crypto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

public class ModelCryptoFuncResp {

    @JsonProperty("message")
    public String msg;

    @JsonProperty("encrypted_string")
    public String encrypted;

    @JsonProperty("decrypted_string")
    public String decrypted;

    @JsonProperty("mrn")
    public String mrn;

    @JsonProperty("zipmrn")
    public String zipmrn;

    public void ModelCryptoFuncResp() {}

    public void ModelCryptoFuncResp(
            final String msg,
            final String encrypted,
            final String decrypted,
            final String mrn,
            final String zipmrn) {
        this.msg = msg;
        this.encrypted = encrypted;
        this.decrypted = decrypted;
        this.mrn = mrn;
        this.zipmrn = zipmrn;
    }

    public void ModelCryptoFuncResp(Set<? extends ConstraintViolation<?>> violations) {
        this.msg = violations.stream().map(cv -> cv.getMessage()).collect(Collectors.joining(", "));
        this.decrypted = null;
        this.mrn = null;
    }
}
