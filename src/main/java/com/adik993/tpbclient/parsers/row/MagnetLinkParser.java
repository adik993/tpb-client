package com.adik993.tpbclient.parsers.row;

import com.adik993.tpbclient.exceptions.ParseException;
import org.jsoup.nodes.Element;

import java.net.URI;

/**
 * Created by adrian on 28/03/17.
 */
public class MagnetLinkParser {
    public static String parse(Element element) throws ParseException {
        String magnet = element.select("a[href*=magnet:?xt=urn:]")
                .attr("href");
        return "".equals(magnet) ? null : URI.create(magnet).toString();
    }
}
