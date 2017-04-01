package com.adik993.tpbclient.parsers.row;

import com.adik993.tpbclient.exceptions.ParseException;
import org.jsoup.nodes.Element;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by adrian on 28/03/17.
 */
public class PublishDateParser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-dd yyyy");
    private static final DateTimeFormatter FORMATTER2 = DateTimeFormatter.ofPattern("MM-dd yyyy HH:mm");
    private static final DateTimeFormatter FORMATTER_MM_DD = DateTimeFormatter.ofPattern("MM-dd");
    private static final Pattern PATTERN_X_AGO = Pattern.compile("([0-9]+)\\s+mins\\s+ago");

    public static LocalDateTime parse(Element element, LocalDateTime now) throws ParseException {
        String str = element.html().replace("&nbsp;", " ");
        str = str.replace("<b>", "").replace("</b>", "");
        Matcher matcher = PATTERN_X_AGO.matcher(str);
        if (matcher.matches()) {
            int ago = Integer.parseInt(matcher.group(1));
            return now.minusMinutes(ago);
        }

        str = str.replace("Today", now.format(FORMATTER_MM_DD));
        str = str.replace("Y-day", now.minusDays(1).format(FORMATTER_MM_DD));
        try {
            return LocalDate.parse(str, FORMATTER).atTime(0, 0);
        } catch (DateTimeParseException e) {
            str = str.replace(" ", " " + now.getYear() + " ");
            return LocalDateTime.parse(str, FORMATTER2);
        }
    }
}
