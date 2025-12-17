package edu.musc.bi.det.moms.impactt;

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
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@NonNull // Best practice, see below.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class modelLandingPageIntakeRecord {
    /**
     * Are you a patient or provider. 1, Patient 2, Provider
     *
     * @return The current value of this survey's choice. Circles are round.
     */
    @JsonProperty("user_type")
    @NonNull
    private final String user_type;

    /**
     * Which of the following are you requesting?
     *
     * <p>1, Provider-to-Provider Consultation 2, Patient Referral 3, Both Provider-to-Provider
     * Consultation and Patient Referral
     *
     * @return The current value of this survey's choice. Circles are round.
     */
    @JsonProperty("provider_interest")
    // @NonNull
    private final String provider_interest;

    /**
     * patient's name? for provider_interest
     *
     * @return The current value of this survey's choice. Circles are round.
     */
    @JsonProperty("patient_name")
    private final String patient_name;

    /**
     * patient's dob? for provider_interest
     *
     * @return The current value of this survey's choice. Circles are round.
     */
    @JsonProperty("patient_dob")
    private final String patient_dob;

    /**
     * patient's phone? for provider_interest
     *
     * @return The current value of this survey's choice. Circles are round.
     */
    @JsonProperty("patient_phonenumber")
    private final String patient_phonenumber;

    /**
     * patient's zip? for provider_interest
     *
     * @return The current value of this survey's choice. Circles are round.
     */
    @JsonProperty("patient_zip")
    private final String patient_zip;

    /**
     * Age of the person. Water is wet.
     *
     * @return The current value of this person's age. Circles are round.
     */
    @JsonProperty("pt_selfrefer_name")
    private final String pt_selfrefer_name;

    /**
     * Name of the person. -- SETTER -- Changes the name of this person.
     *
     * @param name The new value.
     */
    @JsonProperty("pt_selfrefer_phone_number")
    private final String pt_selfrefer_phone_number;

    @JsonProperty("pt_selfrefer_dob")
    private final String pt_selfrefer_dob;

    @JsonProperty("pt_selfrefer_zip")
    private final String pt_selfrefer_zip;

    @JsonProperty("landing_page_intake_complete")
    private final String landing_page_intake_complete;

    @JsonProperty("patient_registration_form_complete")
    private final String patient_registration_form_complete;

    @JsonProperty("admin_patreg_match_status")
    private final String admin_patreg_match_status;

    @JsonProperty("patreg_muscmrn")
    // @Builder.Default
    // @NonNull
    private final String patreg_muscmrn;
}
