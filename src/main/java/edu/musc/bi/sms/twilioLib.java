package edu.musc.bi.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import io.quarkus.logging.Log;

import org.eclipse.microprofile.config.ConfigProvider;

public class twilioLib {
    // Find your Account SID and Auth Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure
    protected static final String ACCOUNT_SID =
            ConfigProvider.getConfig()
                    .getOptionalValue("bi-twilio.sid", String.class);
    protected static final String AUTH_TOKEN =
            ConfigProvider.getConfig()
                    .getOptionalValue("bi-twillio.token", String.class);

    protected static final String LANGONE =
            ConfigProvider.getConfig()
                    .getOptionalValue("bi-twilio.lang1", String.class);
    protected static final String LANGTWO =
            ConfigProvider.getConfig() .getOptionalValue("bi-twilio.lang2", String.class);

    protected static final String URLONE =
            ConfigProvider.getConfig()
                    .getOptionalValue("bi-twilio.url1", String.class);

    protected static final String URLTWO =
            ConfigProvider.getConfig()
                    .getOptionalValue("bi-twilio.url2", String.class);

    protected static final String PHONEONE =
            ConfigProvider.getConfig()
                    .getOptionalValue("bi-twilio.phone1", String.class);
    /*
     *
     */
    public twilioLib() {
    }

    /*
     *
     */
    public String sendSMS(
            final String phone, final String url, final String name, final boolean env_prod) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String res = "";
        final String strEnv = env_prod ? "" : "BMIC Test Dept: ";

        final String strMsg =
                strEnv
                        + LANGONE
                        + name
                        + LANGTWO
                        + url;
        try {
            Message message =
                    Message.creator(
                                    new com.twilio.type.PhoneNumber("+1" + phone), // to
                                    new com.twilio.type.PhoneNumber(PHONEONE), // from
                                    strMsg)
                            .create();

            // System.out.println(message.getSid());
            Log.debug("SMS: sending..." + message.getSid());
            res = name;
        } catch (final Exception e) {
            Log.error("SMS - Failed to handle request", e);
            // System.err.println(e);
        }
        return name;
    }

    /*
     *
     */
    public String sendSMSwithMyChartLink(
            final String phone, final String name, final boolean env_prod) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String res = "";
        final String strEnv = env_prod ? "" : "MUSC Test Dept: ";
        final String url =
                env_prod
                        ? URLONE
                        : URLTWO;

        final String strMsg =
                strEnv
                        + LANGONE
                        + name
                        + LANGTWO
                        + url;
        try {
            Message message =
                    Message.creator(
                                    new com.twilio.type.PhoneNumber("+1" + phone), // to
                                    new com.twilio.type.PhoneNumber("+18437903002"), // from
                                    strMsg)
                            .create();

            // System.out.println(message.getSid());
            Log.debug("SMS: sending..." + message.getSid());

            res = name;
        } catch (final Exception e) {
            Log.error("SMS - Failed to handle request", e);
            // System.err.println(e);
        }
        return name;
    }
}
