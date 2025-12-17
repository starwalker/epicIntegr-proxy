package edu.musc.bi.restclient;

import com.fasterxml.jackson.annotation.*;

import lombok.*;
// import lombok.extern.jackson.Jacksonized;

@Data
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
/*
@Value
@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@NonNull // Best practice, see below.
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "client_ts",
    "client_deviceid",
    "client_netid",
    "firstname",
    "lastname",
    "middlename",
    "dob",
    "sex",
    "race",
    "ethnic",
    "address",
    "city",
    "state",
    "zip",
    "phone",
    "email",
    "language",
    "ssn",
    "country",
    "idc_override"
})
*/
public class modelPatDemogr {
    @JsonProperty("client_ts")
    private String client_ts;

    @JsonProperty("client_deviceid")
    private String client_deviceid;

    @JsonProperty("client_netid")
    private String client_netid;

    @JsonProperty("firstname")
    @NonNull
    private String firstname;

    @JsonProperty("lastname")
    @NonNull
    private String lastname;

    @JsonProperty("middlename")
    private String middlename;

    @JsonProperty("dob")
    @NonNull
    private String dob;

    @JsonProperty("sex")
    private String sex;

    @JsonProperty("race")
    private String race;

    @JsonProperty("ethnic")
    private String ethnic;

    @JsonProperty("address")
    private String address;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("zip")
    @NonNull
    private String zip;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("language")
    private String language;

    @JsonProperty("ssn")
    private String ssn;

    @JsonProperty("country")
    private String country;

    @JsonProperty("idc_override")
    private String idc_override;

    public static class modelPatDemogrBuilder {
        private String client_ts = "";
        private String client_deviceid = "";
        private String client_netid = "";
        private String firstname = "";
        private String lastname = "";
        private String middlename = "";
        private String dob = "";
        private String sex = "";
        private String race = "";
        private String ethnic = "";
        private String address = "";
        private String city = "";
        private String state = "";
        private String zip = "";
        private String phone = "";
        private String email = "";
        private String language = "";
        private String ssn = "";
        private String country = "";
        private String idc_override = "";
    }
}
