package edu.musc.bi.det.moms.impactt;

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
    "mrn"
})
*/
public class modelPatLookupResult {
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

    @JsonProperty("mrn")
    private String mrn;

    @JsonProperty("linkageid")
    private String linkageid;

    public static class modelPatLookupResultBuilder {
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
        private String mrn = "";
        private String linkageid = "";
    }
}
