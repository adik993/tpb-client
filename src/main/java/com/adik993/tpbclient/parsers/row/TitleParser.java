package com.adik993.tpbclient.parsers.row;

import org.jsoup.nodes.Element;

public class TitleParser {
    public static String parse(Element element) {
        return element.select("a[title]").attr("title").replaceFirst("Details for ", "");
    }
}
