package edu.musc.bi.fhir.gateway.api;

import lombok.Value;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

@Value
public class FhirDateTimeParameter implements Serializable {
    private static final int YEAR = 4;

    private static final int YEAR_MONTH = 7;

    private static final int YEAR_MONTH_DAY = 10;

    private static final int TIME_ZONE = 20;

    private static final int TIME_ZONE_OFFSET = 25;

    SearchPrefix prefix;

    String date;

    Instant lowerBound;

    Instant upperBound;

    /** Extract prefix and date from parameter string. */
    public FhirDateTimeParameter(String paramString) {
        super();
        if (paramString.length() <= 1) {
            throw new InvalidRequest(paramString);
        }
        if (Character.isLetter(paramString.charAt(0))) {
            prefix = prefixOrDie(paramString);
            date = paramString.substring(2);
        } else {
            prefix = SearchPrefix.EQ;
            date = paramString;
        }
        lowerBound = computeLowerBound();
        upperBound = computeUpperBound();
    }

    private static SearchPrefix prefixOrDie(String paramString) {
        try {
            return SearchPrefix.valueOf(paramString.substring(0, 2).toUpperCase(Locale.US));
        } catch (IllegalArgumentException e) {
            throw new InvalidRequest(paramString);
        }
    }

    /** Compute lower bound for date. */
    private Instant computeLowerBound() {
        try {
            ZoneOffset offset = ZonedDateTime.now(ZoneId.systemDefault()).getOffset();
            switch (date.length()) {
                case YEAR:
                    return OffsetDateTime.parse(String.format("%s-01-01T00:00:00%s", date, offset))
                            .toInstant();
                case YEAR_MONTH:
                    return OffsetDateTime.parse(String.format("%s-01T00:00:00%s", date, offset))
                            .toInstant();
                case YEAR_MONTH_DAY:
                    return OffsetDateTime.parse(String.format("%sT00:00:00%s", date, offset))
                            .toInstant();
                case TIME_ZONE:
                    return Instant.parse(date);
                case TIME_ZONE_OFFSET:
                    return OffsetDateTime.parse(date).toInstant();
                default:
                    throw new InvalidRequest(date);
            }
        } catch (DateTimeException e) {
            throw new InvalidRequest(date);
        }
    }

    /** Compute upper bound for date. */
    private Instant computeUpperBound() {
        try {
            OffsetDateTime lowerBound =
                    OffsetDateTime.ofInstant(
                            Instant.now(), ZonedDateTime.now(ZoneId.systemDefault()).getOffset());
            switch (date.length()) {
                case YEAR:
                    return lowerBound.plusYears(1).minus(1, ChronoUnit.MILLIS).toInstant();
                case YEAR_MONTH:
                    return lowerBound.plusMonths(1).minus(1, ChronoUnit.MILLIS).toInstant();
                case YEAR_MONTH_DAY:
                    return lowerBound.plusDays(1).minus(1, ChronoUnit.MILLIS).toInstant();
                case TIME_ZONE:
                    // falls through
                case TIME_ZONE_OFFSET:
                    return lowerBound.plusSeconds(1).minus(1, ChronoUnit.MILLIS).toInstant();
                default:
                    throw new InvalidRequest(date);
            }
        } catch (DateTimeException e) {
            throw new InvalidRequest(date);
        }
    }

    /** Indicates if the given date range (epoch millis) satisfies this date-time parameter. */
    public boolean isSatisfied(long lower, long upper) {
        if (lower > upper) {
            throw new IllegalArgumentException(
                    String.format("invalid time range [%s,%s]", lower, upper));
        }
        long lowerBound = this.lowerBound.toEpochMilli();
        long upperBound = this.upperBound.toEpochMilli();
        switch (prefix) {
            case EQ:
                // the range of the search value fully contains the range of the target value
                return lowerBound <= lower && upper <= upperBound;
            case NE:
                // the range of the search value does not fully contain the range of the target
                // value
                return lower < lowerBound || upperBound < upper;
            case GT:
                // the range above the search value intersects the range of the target value
                return upperBound < upper;
            case LT:
                // the range below the search value intersects the range of the target value
                return lower < lowerBound;
            case GE:
                // or the range of the search value fully contains the range of the target value
                return lowerBound <= lower || upperBound < upper;
            case LE:
                // or the range of the search value fully contains the range of the target value
                return lower < lowerBound || upper <= upperBound;
            case SA:
                // and the range above the search value contains the range of the target value
                return upperBound < lower;
            case EB:
                // and the range below the search value contains the range of the target value
                return upper < lowerBound;
            case AP:
                // falls through
            default:
                throw new UnsupportedOperationException(
                        String.format("search prefix %s not implemented", prefix));
        }
    }

    public enum SearchPrefix {
        EQ,
        NE,
        GT,
        LT,
        GE,
        LE,
        SA,
        EB,
        AP
    }

    public static final class InvalidRequest extends IllegalArgumentException {
        /** Invalid request exception. */
        public InvalidRequest(String value) {
            super(
                    String.format(
                            "invalid fhir date-time %s, expected %s",
                            value,
                            "[EQ|NE|GT|LT|GE|LE|SA|EB|AP]YYYY[-MM][-DD]['T'HH:MM:SS][Z|(+|-)HH:MM]"));
        }
    }
}
