package com.adik993.tpbclient.parsers;

import com.adik993.tpbclient.exceptions.ParseException;
import com.adik993.tpbclient.model.PageInfo;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Adrian on 2016-07-09.
 */
public class TpbResultCountParser {
    private static final Pattern PATTERN = Pattern.compile(".*Displaying hits from ([0-9]+) to ([0-9]+) \\(approx ([0-9]+) found\\).*");

    public static PageInfo parse(Document doc) throws ParseException {
        Elements h2 = doc.select("h2:has(span:contains(Search results))");
        Matcher matcher = PATTERN.matcher(h2.html());
        if (!matcher.matches()) throw new ParseException("Unable to parse page info " + h2.toString());
        int from = getAsInt(matcher, 1);
        int to = getAsInt(matcher, 2);
        int total = getAsInt(matcher, 3);
        return new PageInfo(to - from, total);
    }

    private static int getAsInt(Matcher matcher, int group) {
        return Integer.parseInt(matcher.group(group));
    }
}
