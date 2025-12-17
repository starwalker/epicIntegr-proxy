package edu.musc.bi.restclient;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Data
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class mrnInOurDNASC {
    @JsonProperty("linkageid")
    private String linkageid;

    @JsonProperty("mrn")
    private String mrn;

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

    public static class modelPatDemogrBuilder {
        private String linkageid = "";
        private String mrn = "";
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
    }
}
