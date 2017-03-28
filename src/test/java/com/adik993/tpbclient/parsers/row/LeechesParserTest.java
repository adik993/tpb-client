package com.adik993.tpbclient.parsers.row;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Adrian on 2016-07-10.
 */
public class LeechesParserTest {
    @Test
    public void parse() throws Exception {
        Document doc = Jsoup.parseBodyFragment("<table><tr><td align=\"right\">26</td></tr></table>");
        Element td = doc.select("td").first();
        Integer parse = LeechesParser.parse(td);
        assertEquals(26, parse.intValue());
    }

}