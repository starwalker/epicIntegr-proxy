package edu.musc.bi.fhir.gateway.api;

import javax.validation.constraints.Pattern;

public interface IsResource {
  @Pattern(regexp = "[A-Za-z0-9\\-.]{1,64}")
  String id();

  IsResource id(String id);

  String resourceType();
}
