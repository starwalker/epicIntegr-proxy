package edu.musc.bi.det.model;

import com.fasterxml.jackson.annotation.*;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
// import lombok.AccessLevel;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;
// import lombok.Value;
// import lombok.Getter;
// import lombok.Builder;
// import lombok.ToString;
// import lombok.Value;
//
// @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
// @AllArgsConstructor
// @Value

@Value
@Jacksonized
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@NonNull // Best practice, see below.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class modelPatRegRecord {
    /**
     * Age of the person. Water is wet.
     *
     * @return The current value of this person's age. Circles are round.
     */
    @JsonProperty("patreg_creation_city")
    private final String patreg_creation_city;

    @JsonProperty("patreg_creation_dob")
    private final String patreg_creation_dob;

    @JsonProperty("patreg_creation_email")
    private final String patreg_creation_email;

    @JsonProperty("patreg_creation_ethnicity")
    private final String patreg_creation_ethnicity;

    @JsonProperty("patreg_creation_insgroup")
    private final String patreg_creation_insgroup;

    @JsonProperty("patreg_creation_insurancename")
    private final String patreg_creation_insurancename;

    @JsonProperty("patreg_creation_language")
    private final String patreg_creation_language;

    @JsonProperty("patreg_creation_marital")
    private final String patreg_creation_marital;

    @JsonProperty("patreg_creation_mobile")
    private final String patreg_creation_mobile;

    @JsonProperty("patreg_creation_mrn")
    private final String patreg_creation_mrn;

    @JsonProperty("patreg_creation_name")
    private final String patreg_creation_name;

    @JsonProperty("patreg_creation_race")
    private final String patreg_creation_race;

    @JsonProperty("patreg_sel_new")
    private final String patreg_sel_new;

    @JsonProperty("patreg_sel_1")
    private final String patreg_sel_1;

    @JsonProperty("patreg_sel_2")
    private final String patreg_sel_2;

    @JsonProperty("patreg_sel_3")
    private final String patreg_sel_3;

    @JsonProperty("patreg_match1_mrn")
    private final String patreg_match1_mrn;

    @JsonProperty("patreg_match2_mrn")
    private final String patreg_match2_mrn;

    @JsonProperty("patreg_match3_mrn")
    private final String patreg_match3_mrn;

    @JsonProperty("patreg_creation_state")
    private final String patreg_creation_state;

    @JsonProperty("patreg_creation_street")
    private final String patreg_creation_street;

    @JsonProperty("patreg_creation_veteran")
    private final String patreg_creation_veteran;

    @JsonProperty("patreg_creation_zip")
    private final String patreg_creation_zip;

    @JsonProperty("patient_registration_form_complete")
    private final String patient_registration_form_complete;

    @JsonProperty("admin_patreg_match_status")
    private final String admin_patreg_match_status;

    @JsonProperty("patreg_muscmrn")
    private final String patreg_muscmrn;
}
