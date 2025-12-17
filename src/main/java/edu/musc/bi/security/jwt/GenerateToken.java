package edu.musc.bi.security.jwt;

import io.smallrye.jwt.build.Jwt;
// import io.smallrye.jwt.build.JwtClaimsBuilder;

import org.eclipse.microprofile.jwt.Claims;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;

/*
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
*/

public class GenerateToken {
    /** Generate JWT token */
    public static void main(String[] args) {

        // Builder created from JsonObject
        /*
        JsonObject AppName =
                Json.createObjectBuilder().add("AppName", "InOurDNASCMobileApp").build();
        JsonObject userName = Json.createObjectBuilder().add("UserName", "bi-client").build();
        JsonObject userPass =
                Json.createObjectBuilder().add("UserPass", "532ppw947N4ryk2Z").build();
        JsonObject userAddress =
                Json.createObjectBuilder()
                        .add("city", "Charleston")
                        .add("street", "22 WestEdge")
                        .build();
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        jsonArray.add("bi-dev");
        jsonArray.add("bi-admin");
        jsonArray.add("bi-client");
        JsonArray array = jsonArray.build();

        JsonObject json =
                Json.createObjectBuilder(AppName)
                        .add("username", userPass)
                        .add("userpass", userPass)
                        .add("groups", array)
                        .add("address", userAddress)
                        .build();
        JwtClaimsBuilder builder4 = Jwt.claims(json);
        */

        /*
        The iss claim is the issuer of the JWT. This needs to match the server side mp.jwt.verify.issuer. in order for the token to be accepted as valid.
        The upn claim is defined by the MicroProfile JWT RBAC spec as preferred claim to use for the Principal seen via the container security APIs.
        The group claim provides the groups and top-level roles associated with the JWT bearer.

        */
        long nowMillis = System.currentTimeMillis();
        // 43200 12 hours
        // 604800 7 days
        // 2592000 30 days
        long ttlMillis = nowMillis + 2592000;

        String token =
                Jwt.issuer("https://www.musc.edu/issuer")
                        .upn("jwt_prod@musc.edu")
                        .groups(new HashSet<>(Arrays.asList("bidev", "biadmin", "biclient")))
                        .claim(Claims.birthdate.name(), "1999-11-19")
                        .claim("user", "biclient")
                        .claim("pass", "")
                        .claim("scope", "ptlookup")
                        .issuedAt(Instant.now().getEpochSecond())
                        .expiresAt(Instant.now().getEpochSecond() + 2592000)
                        .sign();
        System.out.println(token);
    }
}
