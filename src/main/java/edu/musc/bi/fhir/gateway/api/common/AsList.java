package edu.musc.bi.fhir.gateway.api;

import java.util.List;

public interface AsList<T extends AsList<T>> {

  @SuppressWarnings("unchecked")
  default List<T> asList() {
    return List.of((T) this);
  }
}
