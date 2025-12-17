package edu.musc.bi.tracking;

import com.fasterxml.jackson.annotation.*;

//import io.quarkus.runtime.annotations.*;

import lombok.*;
import lombok.experimental.*;
import lombok.extern.jackson.*;

@Jacksonized
@Data
// @Value
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
// @Builder
// @Data
@SuperBuilder
// @JsonInclude(Include.NON_EMPTY)
//@RegisterForReflection
public class modelTrackingRequest {

    @JsonProperty("id")
    private String id;

    @JsonProperty("env")
    private String env;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("agegroup")
    private String agegroup;

    /*
     * type  1: recruitment
     * type  2: enroll
     * type  3: collect
     * type  4: ordered
     * type  5: followup message
     */
    @JsonProperty("timelinetype")
    private Integer timelinetype;
    /*
    1, SMS Campaign Option 1
    2, SMS Campaign Option 2
    3, SMS Campaign Option 3
    4, SMS Campaign Option 4
    5, In Our DNA SC Landing Page
    6, In Our DNA SC Landing Page - ES
    7, MyChart Initial Recruitment Message Consent Link
    8, MyChart Reminder Recruitment  Consent Link
    9, MyChart Research List Link - Being Identified
    10, MyChart Interesting Consent Link
    11, Interested Text Message
    12, MUSC Horseshoe Employee Sign-Up Link
    13, Community Event
    14, Notable
    15, DL Scan App
    16, Paper Consented
    17, SMS Campaign 2022
    18, SMS Campaign Option 5 (Consent5)
    19, SMS Campaign Option 6 (dna1)
    20, SMS Campaign Option 7 (230919 dna)
    21, Mammogram and Colonoscopy Cohorts
     *
     *
    */
    @JsonProperty("recruitingtype")
    private Integer recruitingtype;

    @JsonProperty("recsource")
    private String recsource;

    /*
     * if timeline type == 1
     * option <0: anything else
     * option 0: Original landing page
     * option 1: option 1 link
     * option 2: option 2 link
     * option 3: option 3 link
     * option 4: option 4 link
     * option 5: option 5 link
     * option 6: option 6 link
     * option 7: option 7 link
     */
    @JsonProperty("option")
    private Integer option;

    /*
        1, Accepted
        2, Queued
        3, Sending
        4, Sent
        5, Failed
        6, Delivered
        7, Undelivered
        8, Receiving
        9, Received
        10, Text via Email - Sent
        11, Not SENT: Already On Study
        12, Not SENT: Already Off Study
        13, Not SENT: Already WITHDRAWN
    */
    @JsonProperty("timeline_rec_sms_deliv_st")
    private String timeline_rec_sms_deliv_st;

    /*
       1, Twilio
       2, Email
       3, MyChart
       4, Gateway Api Proxy Route
    */
    @JsonProperty("timeline_rec_delivery_meth")
    private String timeline_rec_delivery_meth;

    @JsonProperty("msg_sms_reply")
    private String msg_sms_reply;

    @JsonProperty("ts")
    private String ts;

    @JsonProperty("mrn")
    private String mrn;

    @JsonProperty("token")
    private String token;

    @JsonProperty("order_status")
    private String order_status;

    @JsonProperty("enrollstatus")
    private String enrollstatus;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("msg_phone_from")
    private String msg_phone_from;

    @JsonProperty("msg_phone_to")
    private String msg_phone_to;

    @JsonProperty("msg_errcode")
    private String msg_errcode;

    @JsonProperty("msg_errmsg")
    private String msg_errmsg;

    @JsonProperty("msg_dateupdated")
    private String msg_dateupdated;

    @JsonProperty("msg_datesent")
    private String msg_datesent;

    @JsonProperty("msg_datecreated")
    private String msg_datecreated;

    @JsonProperty("msgsid")
    private String msgsid;

    @JsonProperty("msgstatus")
    private String msgstatus;

    @JsonProperty("email")
    private String email;

    @JsonProperty("first")
    private String first;

    @JsonProperty("last")
    private String last;

    @JsonProperty("zip")
    private String zip;

    @JsonProperty("raceeth")
    private String raceeth;

    @JsonProperty("ethnic")
    private String ethnic;

    @JsonProperty("sex")
    private String sex;

    @JsonProperty("dob")
    private String dob;

    @JsonProperty("selectionprob")
    private String selectionprob;

    @JsonProperty("samplingweight")
    private String samplingweight;

    @JsonProperty("groupid")
    private Integer groupid;
}
