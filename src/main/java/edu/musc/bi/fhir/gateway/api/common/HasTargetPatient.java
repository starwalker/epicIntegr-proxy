package edu.musc.bi.fhir.gateway.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.regex.Pattern;

/** Indicates that an item that targets a single specific patient, like an Observation record. */
public interface HasTargetPatient {

  /** Regex to match whatever/Patient/123 type reference values. */
  Pattern PATIENT_REFERENCE = Pattern.compile("(^|.*/)Patient/[^/]+");

  /** Return true if the given reference is referring to a patient resource. */
  static boolean isPatientReference(IsReference reference) {
    if (reference == null || reference.reference() == null) {
      return false;
    }
    return PATIENT_REFERENCE.matcher(reference.reference()).matches();
  }

  /** Return null if patient is not yet known otherwise the ID of the target patient. */
  @JsonIgnore
  IsReference targetPatientId();
}
