package com.adik993.tpbclient.parsers.row;

import com.adik993.tpbclient.exceptions.ParseException;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by adrian on 28/03/17.
 */
public class TorrentIdParser {
    private static final Pattern PATTERN = Pattern.compile("/torrent/([0-9]+).*");

    public static long parse(Element element) throws ParseException {
        String href = element.select("a[title]").attr("href");
        Matcher matcher = PATTERN.matcher(href);
        if (!matcher.matches()) throw new ParseException("Unable to parse torrent id from " + href);
        return Long.parseLong(matcher.group(1));
    }
}
