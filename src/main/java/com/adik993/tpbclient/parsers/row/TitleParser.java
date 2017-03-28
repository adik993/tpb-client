package com.adik993.tpbclient.parsers.row;

import org.jsoup.nodes.Element;

/**
 * Created by adrian on 28/03/17.
 */
public class TitleParser {
    public static String parse(Element element) {
        return element.select("a[title]").html();
    }
}
