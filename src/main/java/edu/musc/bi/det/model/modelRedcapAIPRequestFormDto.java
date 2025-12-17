package edu.musc.bi.det.model;


import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import lombok.ToString;

// import org.jboss.resteasy.reactive.multipart.FileUpload;

import javax.ws.rs.core.MediaType;

@ToString
public class modelRedcapAIPRequestFormDto {
    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String token;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String content;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String action;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String format;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String type;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String csvDelimiter;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String records;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String fields;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String forms;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String events;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String rawOrLabel;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String rawOrLabelHeaders;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String exportCheckboxLabel;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String exportSurveyFields;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String exportDataAccessGroups;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String returnFormat;
}
