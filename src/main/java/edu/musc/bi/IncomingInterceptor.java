package edu.musc.bi;

import static java.util.Arrays.asList;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

// 06/30/2022
/*
 *
 * [WARNING] [io.quarkus.grpc.deployment.GrpcServerProcessor] At least one unused gRPC interceptor found: edu.musc.bi.IncomingInterceptor. If there are meant to be used globally, annotate them with @GlobalInterceptor.
 *
 */

//
@ApplicationScoped
// add @GlobalInterceptor for interceptors meant to be invoked for every service
public class IncomingInterceptor implements ServerInterceptor {

    public static final Metadata.Key<String> EXTRA_HEADER =
            Metadata.Key.of("bi-extra-header", Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> INTERFACE_HEADER =
            Metadata.Key.of("bi-interface-header", Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> EXTRA_BLOCKING_HEADER =
            Metadata.Key.of("bi-blocking-header", Metadata.ASCII_STRING_MARSHALLER);

    private final Map<String, String> headerValues = new HashMap<>();

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> serverCall,
            Metadata metadata,
            ServerCallHandler<ReqT, RespT> serverCallHandler) {

        for (Metadata.Key<String> strKey :
                asList(EXTRA_HEADER, INTERFACE_HEADER, EXTRA_BLOCKING_HEADER)) {
            String header = metadata.get(strKey);
            if (header != null) {
                headerValues.put(strKey.name(), header);
            }
        }

        return serverCallHandler.startCall(serverCall, metadata);
    }

    public void clear() {
        headerValues.clear();
    }

    public Map<String, String> getCollectedHeaders() {
        return headerValues;
    }
}
