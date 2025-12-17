package edu.musc.bi;

import static org.assertj.core.api.Assertions.assertThat;

import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Duration;

import javax.net.ssl.SSLException;

@QuarkusTest
class biFhirEpicServiceTest {

    private ManagedChannel channel;

    @BeforeEach
    public void init() throws SSLException {
        SslContextBuilder builder = GrpcSslContexts.forClient();
        builder.trustManager(new File("shared_musc_edu_cert.cer"));
        builder.keyManager(
                new File("client.cert.pem"),
                new File("client.key.pem"));
        SslContext context = builder.build();

        channel =
                NettyChannelBuilder.forAddress("0.0.0.0", 9001)
                        .overrideAuthority("shared.musc.edu")
                        .sslContext(context)
                        .build();
    }

    @AfterEach
    public void cleanup() {
        channel.shutdownNow();
    }

    @Test
    public void testbiFhirEpicServiceUsingBlockingStub() {
        biFhirEpicSVCGrpc.biFhirEpicSVCBlockingStub client =
                biFhirEpicSVCGrpc.newBlockingStub(channel);
        ptLookupReply reply =
                client.ptLookupPDF417(
                        ptLookupRequest.newBuilder().setPdf417("neo-blocking").build());
        // assertThat(reply.getReturnCode()).isEqualTo("PARSE ERROR: neo-blocking");
        assertThat(reply.getReturnCode()).isEqualTo("");
    }

    @Test
    public void testbiFhirEpicServiceUsingMutinyStub() {
        ptLookupReply reply =
                MutinybiFhirEpicSVCGrpc.newMutinyStub(channel)
                        .ptLookupPDF417(
                                ptLookupRequest.newBuilder().setPdf417("neo-blocking").build())
                        .await()
                        .atMost(Duration.ofSeconds(100));
        // assertThat(reply.getReturnCode()).isEqualTo("PARSE ERROR: neo-blocking");
        assertThat(reply.getReturnCode()).isEqualTo("");
    }
}
