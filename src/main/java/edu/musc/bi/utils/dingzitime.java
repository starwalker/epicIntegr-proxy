package edu.musc.bi.utils;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import com.google.protobuf.Timestamp;

//import java.time.LocalDate;
//import java.time.Period;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class dingzitime {
    private static final Logger LOGGER = Logger.getLogger(dingzitime.class);

    public static final int calculateAge(final String dob) {
        int iResp = -1;
        try {
            if (!StringUtils.isBlank(dob)) {
                LocalDate birthDate = LocalDate.parse(dob, DateTimeFormatter.ISO_LOCAL_DATE);
                LocalDate currentDate = LocalDate.now();
                Period dateDiff = Period.between(birthDate, currentDate);
                iResp = dateDiff.getYears();
            }
        } catch (Exception e) {
            LOGGER.errorv(
                    "calculateAge has an parse issue, check your dob's date format!! check your"
                            + " dob: {0}, error message: {1}",
                    dob, e);
        }
        return iResp;
    }

    /*
     *
     */
    public static final Timestamp convertLocalDateTimeToGoogleTimestamp(LocalDateTime localDateTime) {
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        Timestamp result =
                Timestamp.newBuilder()
                        .setSeconds(instant.getEpochSecond())
                        .setNanos(instant.getNano())
                        .build();

        return result;
    }

    /*
     *
     */
    public static final String convertDateToString(LocalDate date) {
        String strDate = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        strDate = formatter.format(date);

        return strDate;
    }

    /*
     *
     */
    public static final String convertDateTimeToString(LocalDateTime dt) {
        String strDate = null;
        //
        // Inbuilt format
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        // Format LocalDateTime
        strDate = dt.format(formatter);

        return strDate;
    }

    /*
     *
     */
    public static final String getCurrTS() {
        final LocalDateTime localDateTime = LocalDateTime.now();
        final ZoneId zoneId = ZoneId.systemDefault();
        final ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);
        final long currentTimestamp = zonedDateTime.toInstant().toEpochMilli();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final String ts = localDateTime.format(formatter);
        return ts;
    }

    public static final LocalDateTime convertStringToLocalDT(final String strDate) {
        try {
            // 2022.02.23 09:33:22
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
            // String text = "2011-10-02 18:48:05.123";
            LocalDateTime dateTime = LocalDateTime.parse(strDate, formatter);
            // System.out.println(dateTime);

            return dateTime;
        } catch (Exception e) {
            // System.out.println("Exception :" + e);
            e.printStackTrace();
            return null;
        }
    }

}
