package edu.musc.bi;

import static io.restassured.RestAssured.get;

import static org.assertj.core.api.Assertions.assertThat;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

@QuarkusTest
class biFhirEpicEndpointTest {

    /*
    @Test
    public void testbiFhirEpicServiceUsingBlockingStub() {
        String response = get("/fhirepic/blocking/neo").asString();
        assertThat(response).contains("neo");
    }

    @Test
    public void testbiFhirEpicServiceUsingMutinyStub() {
        String response = get("/fhirepic/pdf417/neo-pdf417-mutiny").asString();
        assertThat(response).contains("neo-pdf417-mutiny");
    }
    */
}
