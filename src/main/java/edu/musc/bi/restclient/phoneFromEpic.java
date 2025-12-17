package edu.musc.bi.restclient;

import com.fasterxml.jackson.annotation.*;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonPropertyOrder({"phone", "first"})
public class phoneFromEpic {
    public String phone;
    public String first;
}
