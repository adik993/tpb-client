package com.adik993.tpbclient.parsers.row;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Adrian on 2016-07-10.
 */
public class SeedsParserTest {
    @Test
    public void parse() throws Exception {
        Document doc = Jsoup.parseBodyFragment("<table><tr><td align=\"right\">1061</td></tr></table>");
        Element td = doc.select("td").first();
        Integer parse = SeedsParser.parse(td);
        assertEquals(1061, parse.intValue());
    }
}