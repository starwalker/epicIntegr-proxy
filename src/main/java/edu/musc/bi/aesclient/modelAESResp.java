package edu.musc.bi.aesclient;

import com.fasterxml.jackson.annotation.*;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@JsonPropertyOrder({"message"})
public class modelAESResp {
    public List<String> message = new ArrayList<String>();
}
