package edu.musc.bi.fhir.gateway.api;

/**
 * This is a marker interface that explicitly declares a Resource does not contain patient data,
 * e.g. Location.
 */
public interface DoesNotHaveTargetPatient {}
