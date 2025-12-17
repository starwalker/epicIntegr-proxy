package edu.musc.bi.consent;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Config {

    @JsonProperty("grantType")
    public String granttype;

    @JsonProperty("clientId")
    public String clientid;

    @JsonProperty("launchUri")
    public String launchuri;

    @JsonProperty("redirectUri")
    public String redirecturi;

    public String scope;

    public Config() {}

    public Config(
            String granttype, String clientid, String launchuri, String redirecturi, String scope) {
        this.granttype = granttype;
        this.clientid = clientid;
        this.launchuri = launchuri;
        this.redirecturi = redirecturi;
        this.scope = scope;
    }
}
