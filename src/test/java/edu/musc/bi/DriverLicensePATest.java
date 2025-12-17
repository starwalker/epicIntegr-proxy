package edu.musc.bi.parseusdl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DriverLicensePATest {

    static String barCode = "";
    static String barCodeWithNo = "";
    static DriverLicense license = null;

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        final String file_path = "usdl_rawcode/pa";
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
        Assertions.assertEquals("PA", license.getState());
    }

    @Test
    public void testHeight() {
        Assertions.assertEquals(506, (int) license.getHeight());
    }

    @Test
    public void testSex() {
        Assertions.assertEquals("M", license.getSex());
    }

    @Test
    public void testIssuedDate() {
        /*
        Assertions.assertEquals(
                2010,
                license.getLicenseIssuedDate().get(Calendar.YEAR));
        Assertions.assertEquals(
                12 - 1,
                license.getLicenseIssuedDate().get(Calendar.MONTH));
        Assertions.assertEquals(
                4,
                license.getLicenseIssuedDate().get(Calendar.DATE));
                */
    }

    @Test
    public void testExpDate() {
        /*
        Assertions.assertEquals(
                2055,
                license.getLicenseExpirationDate().get(Calendar.YEAR));
        Assertions.assertEquals(
                1 - 1,
                license.getLicenseExpirationDate().get(Calendar.MONTH));
        Assertions.assertEquals(
                18,
                license.getLicenseExpirationDate().get(Calendar.DATE));
                */
    }

    @Test
    public void testDOB() {
        /*
        Assertions.assertEquals(
                1960, license.getDOB().get(Calendar.YEAR));
        Assertions.assertEquals(
                2 - 1, license.getDOB().get(Calendar.MONTH));
        Assertions.assertEquals(
                17, license.getDOB().get(Calendar.DATE));
                */
    }

    @Test
    public void testHasExpired() {
        // Assertions.assertEquals("License should have not expired", false,
        //      license.hasExpired());
        Assertions.assertEquals(false, license.hasExpired());
    }

    @Test
    public void testDriverLicenseNumber() {
        Assertions.assertEquals("16117181", license.getDriverLicenseNumber());
    }

    @Test
    public void testJSON() {
        System.out.println("json=");
        System.out.println(license.toJson());
    }
}
