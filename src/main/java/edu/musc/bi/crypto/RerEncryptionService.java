package edu.musc.bi.crypto;

import edu.musc.bi.aesclient.aesClientResource;

import io.smallrye.mutiny.Uni;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class RerEncryptionService {

    private static final Logger LOGGER = Logger.getLogger(RerEncryptionService.class);

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


    public Uni<Response> encrypt(@Valid RerEncryption body, final String key) throws Exception {

        final String encryptedText = body.Message;
        if (encryptedText == null) {
            return Uni.createFrom().item(Response.status(400).build());
        }

        Encrypt encrypt = new Encrypt(encryptedText);
        try {
            if (encryptedText != null) {
                try {
                    if (encryptedText.length() > 40) {

                        // System.out.println(encryptedText);
                        LOGGER.debug(
                                "this message is came from DLScanApp or NOTABLE: " + encryptedText);

                        final aesClientResource aesRes = new aesClientResource();
                        final String id = aesRes.getMessage(encryptedText, key);
                        String mrn = id == null ? "" : id;
                        if (!StringUtils.isBlank(id)) {
                            if (id.contains("|")) {
                                String[] arrOfMsg = id.split(Pattern.quote("|"));
                                if (arrOfMsg.length > 0) {
                                    mrn = arrOfMsg[0];
                                }
                            }
                        }

                        final String paramter = "mrn=" + mrn;

                        EncryptResp resp = new EncryptResp("success", encryptedText, paramter, mrn);
                        return Uni.createFrom().item(Response.ok(resp).build());

                    } else {
                        List<String> list =
                                Arrays.asList(
                                        bikeydlpw0,
                                        bikeydlpw6,
                                        bikeydlpw1,
                                        bikeydlpw2,
                                        bikeydlpw3,
                                        bikeydlpw4,
                                        bikeydlpw5,
                                        bikeydlpw6,
                                        bikeydlpw11,
                                        bikeydlpw14,
                                        bikeydlpw7,
                                        bikeydlpw8);
                        String encrypted = encrypt.getEncryptedString(0);
                        String mrn = encrypt.getMRN(encrypted);
                        if (StringUtils.isBlank(mrn)) {
                            for (int i = 1; i <= 4; i++) {
                                encrypted = encrypt.getEncryptedString(i);
                                mrn = encrypt.getMRN(encrypted);
                                if (StringUtils.isNotBlank(mrn)) {
                                    break;
                                }
                            }
                        }

                        EncryptResp resp =
                                new EncryptResp("success", encryptedText, encrypted, mrn);
                        return Uni.createFrom().item(Response.ok(resp).build());
                    }
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace(System.out);
                    throw new RuntimeException(e.getCause());
                } catch (BadPaddingException e) {
                    e.printStackTrace(System.out);
                    throw new RuntimeException(e.getCause());
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                    throw new RuntimeException(e.getCause());
                }
            }

            LOGGER.info("the message is void.");

            return Uni.createFrom()
                    .item(Response.status(400).entity("Unknown messageType").build());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        }
    }
}
