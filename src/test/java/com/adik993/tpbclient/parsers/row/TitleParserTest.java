package com.adik993.tpbclient.parsers.row;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Adrian on 2016-07-10.
 */
public class TitleParserTest {

    @Test
    public void testParse() throws Exception {
        Document doc = Jsoup.parseBodyFragment("<table><tr><td><a href=\"/torrent/15256679/Movie title\" title=\"Details for Movie title\">Movie title</a> </td></tr></table>");
        Element td = doc.select("td").first();
        String parse = TitleParser.parse(td);
        assertEquals("Movie title", parse);
    }

}