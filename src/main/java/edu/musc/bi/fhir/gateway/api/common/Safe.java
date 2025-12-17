package edu.musc.bi.fhir.gateway.api;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Safe {
  /** Guarantee an unmodifiable list result. */
  public static <T> List<T> list(List<T> maybeList) {
    return maybeList == null ? List.of() : Collections.unmodifiableList(maybeList);
  }

  /** Guarantee a stream result. */
  public static <T> Stream<T> stream(Collection<T> maybeCollection) {
    return maybeCollection == null ? Stream.empty() : maybeCollection.stream();
  }
}
