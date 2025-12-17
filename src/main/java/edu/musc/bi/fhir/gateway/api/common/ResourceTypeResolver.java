package edu.musc.bi.fhir.gateway.api;

import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class ResourceTypeResolver {
    /**
     * Type resolves are likely to be statically defined and reused, e.g. in JSON deserialization.
     * However, it is possible that static instances may not know _all_ types for a specific use
     * case. For example, if an application using this library adds their own Resource
     * implementation, then the application may need to add a resolver to the statically defined
     * instances used for JSON deserialization.
     *
     * <p>We want to make sure that this is thread safe, should something attempt to add resolvers
     * simultaneously.
     */
    @Getter private final List<Resolver> resolvers = new CopyOnWriteArrayList<>();

    /** Discovery a resource type as a class with the same name in a package. */
    public static Resolver findInPackage(Package resourcePackage) {
        return resourceType -> {
            try {
                return Class.forName(resourcePackage.getName() + "." + resourceType);
            } catch (ClassNotFoundException e) {
                return null;
            }
        };
    }

    /**
     * Discovery a resource type as a class with the same name in the same package as the given
     * class.
     */
    public static Resolver findInSamePackageAs(Class<?> resourceClass) {
        return findInPackage(resourceClass.getPackage());
    }

    /** Create a new instance initialized with the given resolvers. */
    public static ResourceTypeResolver of(Resolver... resolvers) {
        var resourceTypeResolver = new ResourceTypeResolver();
        resourceTypeResolver.resolvers.addAll(List.of(resolvers));
        return resourceTypeResolver;
    }

    /** Explicitly map a resource type name to a Java type. */
    public static Resolver typeFor(@NonNull String resourceTypeName, @NonNull Class<?> type) {
        return resourceType -> resourceTypeName.equals(resourceType) ? type : null;
    }

    /** Throw an UnknownType exception or find a class that represents the given resource type. */
    public Class<?> resolve(@NonNull String resourceType) {
        return resolvers.stream()
                .map(r -> r.resolveResourceType(resourceType))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new UnknownType(resourceType));
    }

    /** Pluggable module that allows Java types to be discovered for FHIR resource type names. */
    @FunctionalInterface
    public interface Resolver {
        /**
         * Return null if the type cannot be resolve, otherwise return the class that represents the
         * resource type.
         */
        Class<?> resolveResourceType(String resourceType);
    }

    /** Thrown in the resource type cannot be resolved to a Java type. */
    public static class UnknownType extends RuntimeException {
        public UnknownType(String resourceType) {
            super("Type not resolved for resource type: " + resourceType);
        }
    }
}
