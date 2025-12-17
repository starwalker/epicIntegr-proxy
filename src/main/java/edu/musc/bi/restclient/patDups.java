package edu.musc.bi.restclient;

import com.fasterxml.jackson.annotation.*;

import lombok.Data;

import java.util.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class patDups {
    public List<patDup> pats;
}
