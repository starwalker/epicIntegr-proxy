package edu.musc.bi.parseusdl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DriverLicenseSC1Test {

    static String barCode = "";
    static String barCodeWithNo = "";
    static DriverLicense license = null;

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        final String file_path = "usdl_rawcode/sc1";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream testResource = loader.getResourceAsStream(file_path);

        BufferedReader br = null;
        int cursor = -1;
        br = new BufferedReader(new InputStreamReader(testResource, StandardCharsets.UTF_8));
        int i = 0;
        while ((cursor = br.read()) != -1) {
            barCodeWithNo += "(" + i + ")" + (char) cursor;
            barCode += (char) cursor;
            i++;
        }
        license = new DriverLicense(barCode);
    }

    @AfterAll
    public static void tearDownAfterClass() throws Exception {}

    @Test
    public void testLast() {
        Assertions.assertEquals("SCTESTONELAST", license.getLastName());
    }

    @Test
    public void testFirst() {
        Assertions.assertEquals("SCTESTONEFIRST", license.getFirstName());
    }

    @Test
    public void testMiddle() {
        Assertions.assertEquals("SCTESTONEMIDDLE", license.getMiddleName());
    }

    @Test
    public void testCity() {
        Assertions.assertEquals("CHARLESTON", license.getCity());
    }

    @Test
    public void testStreet() {
        Assertions.assertEquals("700 EAST ELLIS DR APT 8304", license.getAddress());
    }

    @Test
    public void testState() {
        Assertions.assertEquals("SC", license.getState());
    }

    @Test
    public void testHeight() {
        Assertions.assertEquals(71, (int) license.getHeight());
    }

    @Test
    public void testSex() {
        Assertions.assertEquals("M", license.getSex());
    }

    @Test
    public void testDOB() {
        /*
        Assertions.assertEquals(1992, license.getDOB().get(Calendar.YEAR));
        Assertions.assertEquals(1 - 1, license.getDOB().get(Calendar.MONTH));
        Assertions.assertEquals(1, license.getDOB().get(Calendar.DATE));
        */
    }

    @Test
    public void testHasExpired() {
        Assertions.assertEquals(false, license.hasExpired());
    }

    @Test
    public void testDriverLicenseNumber() {
        Assertions.assertEquals("101761505", license.getDriverLicenseNumber());
    }

    @Test
    public void testJSON() {
        System.out.println("json=");
        System.out.println(license.toJson());
    }
}
