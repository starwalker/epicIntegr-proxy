package edu.musc.bi.restclient;

import com.fasterxml.jackson.annotation.*;

//import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

//@AllArgsConstructor(access = AccessLevel.PRIVATE)
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Jacksonized
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(Include.NON_EMPTY)
@JsonInclude(Include.NON_NULL)
public class modelPatReq {
    @JsonProperty("message")
    private String msg;

    @JsonProperty("mrn")
    private String mrn;

    @JsonProperty("dob")
    private String dob;

    @JsonProperty("env")
    private String env;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("agegroup")
    private String agegroup;

    @JsonProperty("encrypted")
    private String encrypted;

    @JsonProperty("refsource")
    private String refsource;

    @JsonProperty("iss")
    private int iss;
}
