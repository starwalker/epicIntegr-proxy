package edu.musc.bi.restclient;

import com.fasterxml.jackson.annotation.*;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
// @Builder(toBuilder = true)
@JsonPropertyOrder({"type", "message", "code"})
public class biApiResp {
    public String type;
    public String message;
    public Integer code;
}
