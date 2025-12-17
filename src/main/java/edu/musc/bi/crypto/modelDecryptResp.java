package edu.musc.bi.crypto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Jacksonized
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
// @JsonInclude(Include.NON_NULL)
public class modelDecryptResp {

    @JsonProperty("message")
    private String msg;

    @JsonProperty("encrypted_string")
    private String encrypted;

    @JsonProperty("decrypted_string")
    private String decrypted;

    @JsonProperty("mrn")
    private String mrn;

    @JsonProperty("first")
    private String first;

    @JsonProperty("last")
    private String last;

    @JsonProperty("name")
    private String name;

    @JsonProperty("iss")
    private int iss;

    @JsonProperty("iat")
    private String iat;

    @JsonProperty("collect_type")
    private String collect_type;

    @JsonProperty("env")
    private String env;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("agegroup")
    private String agegroup;

    @JsonProperty("refsource")
    private String refsource;

    @JsonProperty("enrollstatus")
    private String enrollstatus;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("email")
    private String email;

    @JsonProperty("dob")
    private String dob;

    @JsonProperty("zip")
    private String zip;

    @JsonProperty("ts")
    private String ts;
}
