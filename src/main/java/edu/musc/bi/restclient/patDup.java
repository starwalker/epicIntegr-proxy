package edu.musc.bi.restclient;

import com.fasterxml.jackson.annotation.*;

import lombok.Data;
import lombok.ToString;

import java.util.*;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class patDup {
    private String phone;
    private String email;
    private String lastname;
    private String firstname;
    private String middlename;
    private String dob;
    private String sex;
    private String linkageid;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String mrn;
}
