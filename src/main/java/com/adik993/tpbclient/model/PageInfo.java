package com.adik993.tpbclient.model;

import com.adik993.tpbclient.exceptions.ParseException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static lombok.AccessLevel.PACKAGE;

@Getter
@AllArgsConstructor(access = PACKAGE)
public class PageInfo {
    private static final Pattern PATTERN = Pattern.compile(".*Displaying hits from ([0-9]+) to ([0-9]+) \\(approx ([0-9]+) found\\).*");

    private final int pageSize;
    private final int total;

    PageInfo(Document document) throws ParseException {
        Elements h2 = document.select("h2:has(span:contains(Search results))");
        Matcher matcher = PATTERN.matcher(h2.html());
        if (!matcher.matches()) throw new ParseException("Unable to parse page info " + h2.toString());
        int from = parseInt(matcher.group(1));
        int to = parseInt(matcher.group(2));
        this.pageSize = to - from;
        this.total = parseInt(matcher.group(3));
    }
}
