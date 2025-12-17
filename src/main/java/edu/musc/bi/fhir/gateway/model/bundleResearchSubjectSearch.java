package edu.musc.bi.fhir.gateway;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Builder
@ToString
@Data
@AllArgsConstructor
@Getter
@Setter
class Search {

    private String mode;
}

@Builder
@ToString
@Data
@AllArgsConstructor
@Getter
@Setter
class Identifier {

    private String system;
    private Integer value;
}

@Builder
@ToString
@Data
@AllArgsConstructor
@Getter
@Setter
class Period {

    private String start;
}

@Builder
@ToString
@Data
@AllArgsConstructor
@Getter
@Setter
class Study {

    private String reference;
    private String display;
}

@Builder
@ToString
@Data
@AllArgsConstructor
@Getter
@Setter
class Individual {

    private String reference;
    private String display;
}

@Builder
@ToString
@Data
@AllArgsConstructor
@Getter
@Setter
class Resource {

    private List<Identifier> identifier;
    private Period period;
    private Study study;
    private Individual individual;
    private String id;
    private String resourceType;
    private String status;
}

@Builder
@ToString
@Data
@AllArgsConstructor
@Getter
@Setter
class Link {

    private String url;
    private String relation;
}

@Builder
@ToString
@Data
@AllArgsConstructor
@Getter
@Setter
class Entry {

    private Search search;
    private Resource resource;
    private String fullUrl;
    private List<Link> link;
}

@Builder
@ToString
@Data
@AllArgsConstructor
@Getter
@Setter
public class bundleResearchSubjectSearch {

    private List<Entry> entry;
    private Integer total;
    private List<Link> link;
    private String type;
    private String resourceType;
}
