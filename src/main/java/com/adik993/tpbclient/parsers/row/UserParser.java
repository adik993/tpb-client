package com.adik993.tpbclient.parsers.row;

import com.adik993.tpbclient.exceptions.ParseException;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by adrian on 28/03/17.
 */
public class UserParser {
    private static final Pattern PATTERN = Pattern.compile("/user/([^/]+)\\/|$");

    public static String parse(Element element) throws ParseException {
        String href = element.select("a[href*=/user/")
                .attr("href");
        Matcher matcher = PATTERN.matcher(href);
        if (!matcher.matches()) throw new ParseException("Unable to parse user from " + href);
        return matcher.group(1);
    }
}
