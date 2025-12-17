package edu.musc.bi.restclient;

import com.fasterxml.jackson.annotation.*;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonPropertyOrder({"first", "last", "dob"})
public class nameFromEpic {
    @JsonProperty("first")
    public String first;

    @JsonProperty("last")
    public String last;

    @JsonProperty("dob")
    public String dob;
}
