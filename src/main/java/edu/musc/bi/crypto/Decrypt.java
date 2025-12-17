package edu.musc.bi.crypto;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.security.MessageDigest;
import org.jboss.logging.Logger;

//import java.util.Base64; If you have Java 8, you can use this instead of DatatypeConverter.
import javax.xml.bind.DatatypeConverter;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;


public class Decrypt {

    private String message;
    private boolean success;
    private static final Logger LOGGER = Logger.getLogger(RerDecryptionService.class);
    @ConfigProperty(name = "bi-key-dl-pw0")
    String bikeydlpw0;

    @ConfigProperty(name = "bi-key-dl-pw1")
    String bikeydlpw1;

    @ConfigProperty(name = "bi-key-dl-pw2")
    String bikeydlpw2;

    @ConfigProperty(name = "bi-key-dl-pw3")
    String bikeydlpw3;

    @ConfigProperty(name = "bi-key-dl-pw4")
    String bikeydlpw4;

    @ConfigProperty(name = "bi-key-dl-pw5")
    String bikeydlpw5;

    @ConfigProperty(name = "bi-key-dl-pw6")
    String bikeydlpw6;

    @ConfigProperty(name = "bi-key-dl-pw7")
    String bikeydlpw7;

    @ConfigProperty(name = "bi-key-dl-pw8")
    String bikeydlpw8;

    @ConfigProperty(name = "bi-key-dl-pw9")
    String bikeydlpw9;

    @ConfigProperty(name = "bi-key-dl-pw10")
    String bikeydlpw10;

    @ConfigProperty(name = "bi-key-dl-pw11")
    String bikeydlpw11;

    @ConfigProperty(name = "bi-key-dl-pw14")
    String bikeydlpw14;

    @ConfigProperty(name = "bi-key-dl-pw18")
    String bikeydlpw18;

    @ConfigProperty(name = "bi-key-dl-pw21")
    String bikeydlpw21;


    Decrypt(final String message) {
        this.success = true;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public final String getMRN(final String decryptedStr) {
        String rst = "";
        if (decryptedStr != null && !decryptedStr.isEmpty()) {
            try {
                if ( decryptedStr.contains("=") ) {
                    String[] strSplit = decryptedStr.split("=");
                    rst = strSplit[1];
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                throw new RuntimeException(ex.getCause());
            }
        }
        return rst;
    }

    public String getEncryptedString(final int option)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException,
                    InvalidKeyException, InvalidAlgorithmParameterException,
                    IllegalBlockSizeException, BadPaddingException {
        String rst = "";
        String password = bikeydlpw0;
        switch (option) {
            case 1: password = bikeydlpw1;
                    break;
            case 2: password = bikeydlpw2;
                    break;
            case 3: password = bikeydlpw3;
                    break;
            case 4: password = bikeydlpw4;
                    break;
            case 5: password = bikeydlpw5;
                    break;
            case 6: password = bikeydlpw6;
                    break;
            case 7: password = bikeydlpw7;
                    break;
            case 8: password = bikeydlpw8;
                    break;
            case 9: password = bikeydlpw9;
                    break;
            case 10: password = bikeydlpw10;
                    break;
            case 11: password = bikeydlpw11;
                    break;
            case 14: password = bikeydlpw14;
                    break;
            case 18: password = bikeydlpw18;
                    break;
            case 21: password = bikeydlpw21;
                    break;
            default: password = bikeydlpw0;
                     break;
        }
        boolean removeUrlEncoding = true;
        if (message != null) {
            try {
                rst = encrypt(message, passToAESHash(password, 256), removeUrlEncoding);
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex.getCause());
            }
        }
        return rst;
    }


    public String getDecryptedString(final int option)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException,
                    InvalidKeyException, InvalidAlgorithmParameterException,
                    IllegalBlockSizeException, BadPaddingException {
        String rst = "";
        String password = bikeydlpw0; // landing
        switch (option) {
            case 1: password = bikeydlpw1;
                    break;
            case 2: password = bikeydlpw2;
                    break;
            case 3: password = bikeydlpw3;
                    break;
            case 4: password = bikeydlpw4;
                    break;
            case 5: password = bikeydlpw5;
                    break;
            case 6: password = bikeydlpw6;
                    break;
            case 7: password = bikeydlpw7;
                    break;
            case 8: password = bikeydlpw8;
                    break;
            case 9: password = bikeydlpw9;
                    break;
            case 10: password = bikeydlpw10;
                    break;
            case 11: password = bikeydlpw11;
                    break;
            case 14: password = bikeydlpw14;
                    break;
            case 18: password = bikeydlpw18;
                    break;
            case 21: password = bikeydlpw21;
                    break;
            default: password = bikeydlpw0;
                     break;
        }
        boolean removeUrlEncoding = true;
        if (message != null) {
            try {
                rst = decrypt(message, passToAESHash(password, 256), removeUrlEncoding);
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex.getCause());
            }
        }
        return rst;
    }

    public String decrypt(String inputString, byte[] hash, boolean removeUrlEncoding)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
                    InvalidAlgorithmParameterException, IllegalBlockSizeException,
                    BadPaddingException, UnsupportedEncodingException {

        // Apparently this decode function is deprecated; unsure of an obvious alternative
        if (removeUrlEncoding) {
            inputString = URLDecoder.decode(inputString, StandardCharsets.UTF_8.name());
        }
        byte[] input = DatatypeConverter.parseBase64Binary(inputString);

        // Pass empty IV
        byte[] IV = new byte[16];
        Cipher aes = Cipher.getInstance("AES");
        aes.init(Cipher.DECRYPT_MODE, new SecretKeySpec(hash, "AES"), new IvParameterSpec(IV));
        return new String(aes.doFinal(input), "UTF-8");
    }


    public String encrypt(final String inputString, byte[] hash, final boolean removeUrlEncoding) {
        try {
            // Apparently this decode function is deprecated; unsure of an obvious alternative
            String text = inputString;
            if (removeUrlEncoding) {
                text = URLDecoder.decode(inputString, StandardCharsets.UTF_8.name());
            }
            byte[] input = DatatypeConverter.parseBase64Binary(text);

            // Pass empty IV
            byte[] IV = new byte[256];
            Cipher aes = Cipher.getInstance("AES");
            aes.init(Cipher.DECRYPT_MODE, new SecretKeySpec(hash, "AES"), new IvParameterSpec(IV));

            return new String(aes.doFinal(input), "UTF-8");

        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
       }
    }

    public static byte[] fromHexString(String s) {
        int len = s.length();
        byte[] data = new byte[len / 4];
        for (int i = 0; i < len; i += 4) {
            data[i / 4] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }


    public byte[] passToAESHash(String pass, int keySize)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {

        // initialize the two 64-element arrays of constants used in Microsoft's algorithm
        byte[] firstXORArray = new byte[256];
        Arrays.fill(firstXORArray, (byte) 0x36);

        byte[] secondXORArray = new byte[256];
        Arrays.fill(secondXORArray, (byte) 0x5C);

        // hash the input password
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedPass = md.digest(pass.getBytes("UTF-8"));

        // hash the XOR outputs and concatenate
        byte[] hashed36 = md.digest(xorWithHashedPass(firstXORArray, hashedPass));
        byte[] hashed5C = md.digest(xorWithHashedPass(secondXORArray, hashedPass));
        byte[] concatenatedArray = new byte[hashed36.length + hashed5C.length];
        System.arraycopy(hashed36, 0, concatenatedArray, 0, hashed36.length);
        System.arraycopy(hashed5C, 0, concatenatedArray, hashed36.length, hashed5C.length);

        // pull the first 16 elements (if with the key size of 256) and return
        int arraySize = keySize / 8;
        byte[] returnArray = new byte[arraySize];
        for (int i = 0; i < arraySize; i++) {
            returnArray[i] = concatenatedArray[i];
        }

        return returnArray;
    }
    // Helper function for XOR outputs
    public byte[] xorWithHashedPass(byte[] constantArray, byte[] hashedPass) {
        byte[] xorArray = new byte[64];
        for (int i = 0; i < constantArray.length; i++) {
            xorArray[i] =
                    (i < hashedPass.length)
                            ? (byte) (constantArray[i] ^ hashedPass[i])
                            : constantArray[i];
        }

        return xorArray;
    }

    public boolean isSuccess() {
        return success;
    }
}
