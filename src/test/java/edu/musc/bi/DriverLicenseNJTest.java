package edu.musc.bi.parseusdl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DriverLicenseNJTest {

    static String barCode = "";
    static String barCodeWithNo = "";
    static DriverLicense license = null;

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        final String file_path = "usdl_rawcode/nj";
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
    public void testState() {
        Assertions.assertEquals("NJ", license.getState());
    }

    @Test
    public void testHeight() {
        Assertions.assertEquals(67, (int) license.getHeight());
    }

    @Test
    public void testSex() {
        Assertions.assertEquals("M", license.getSex());
    }

    @Test
    public void testIssuedDate() {
        /*
        Assertions.assertEquals(
                2012,
                license.getLicenseIssuedDate().get(Calendar.YEAR));
        Assertions.assertEquals(
                5 - 1,
                license.getLicenseIssuedDate().get(Calendar.MONTH));
        Assertions.assertEquals(
                24,
                license.getLicenseIssuedDate().get(Calendar.DATE));
                */
    }

    @Test
    public void testExpDate() {
        /*
        Assertions.assertEquals(
                2013,
                license.getLicenseExpirationDate().get(Calendar.YEAR));
        Assertions.assertEquals(
                5 - 1,
                license.getLicenseExpirationDate().get(Calendar.MONTH));
        Assertions.assertEquals(
                1, license.getLicenseExpirationDate().get(Calendar.DATE));
                */
    }

    @Test
    public void testDOB() {
        /*
        Assertions.assertEquals(
                1962, license.getDOB().get(Calendar.YEAR));
        Assertions.assertEquals(
                10 - 1, license.getDOB().get(Calendar.MONTH));
        Assertions.assertEquals(26, license.getDOB().get(Calendar.DATE));
        */
    }

    @Test
    public void testHasExpired() {
        Assertions.assertEquals(true, license.hasExpired());
    }

    @Test
    public void testDriverLicenseNumber() {
        Assertions.assertEquals("D46220533109611", license.getDriverLicenseNumber());
    }

    @Test
    public void testJSON() {
        System.out.println("json=");
        System.out.println(license.toJson());
    }
}
