package pe.client.custom.app.util.constant;

import java.time.format.DateTimeFormatter;

public class Format {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-DD'T'hh:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    public static final String DATE_SEPARATOR = "-";
}
