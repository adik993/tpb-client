package com.adik993.tpbclient.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Adrian on 2016-07-10.
 */
public class UserParserTest {
    @Test
    public void parse() throws Exception {
        Document doc = Jsoup.parseBodyFragment("<table><tr><td><a href=\"/user/Ctrlme/\" title=\"Browse Ctrlme\">Ctrlme</a></td></tr></table>");
        Element td = doc.select("td").first();
        String parse = TpbTorrentRowParser.UserParser.parse(td);
        assertEquals("Ctrlme", parse);
    }

}