package com.adik993.tpbclient.parsers.row;

import com.adik993.tpbclient.exceptions.ParseException;
import org.jsoup.nodes.Element;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by adrian on 28/03/17.
 */
public class TorrentLinkParser {
    private static final Pattern PATTERN = Pattern.compile("/torrent/[0-9]+.*");

    public static String parse(Element element) throws ParseException {
        String href = element.select("a[title]").attr("href");
        Matcher matcher = PATTERN.matcher(href);
        if (!matcher.matches()) throw new ParseException("Unable to parse torrent link from " + href);
        try {
            URL url = new URL(element.baseUri());
            return new URL(url.getProtocol() + "://" + url.getHost() + href).toString();
        } catch (MalformedURLException e) {
            throw new ParseException("Unable to parse url " + element.baseUri() + href);
        }
    }
}
