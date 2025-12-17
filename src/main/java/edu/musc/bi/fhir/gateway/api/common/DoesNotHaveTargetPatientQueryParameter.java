package edu.musc.bi.fhir.gateway.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that an item specifically does not have query parameters that targets a single specific
 * patient, like an Observation 'patient' or QuestionnaireResponse 'subject'.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DoesNotHaveTargetPatientQueryParameter {}
