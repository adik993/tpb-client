package com.adik993.tpbclient.parsers.row;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class TitleParserTest {

    @Test
    public void testParse() throws Exception {
        Document doc = Jsoup.parseBodyFragment("<table><tr><td><a href=\"/torrent/15256679/Movie title\" title=\"Details for Movie title\">Movie title</a> </td></tr></table>");
        Element td = doc.select("td").first();
        String parse = TitleParser.parse(td);
        assertEquals("Movie title", parse);
    }

    @Test
    public void testParseEmbededJavaScript() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("title_embeded_js.html")));
        String htmlFragment = bufferedReader.lines().collect(Collectors.joining("\n"));
        Document doc = Jsoup.parseBodyFragment(htmlFragment);
        Element td = doc.select("td").first();
        String parse = TitleParser.parse(td);
        assertEquals("07.09.28.Next.2007.HDDVD.720p.x264@Ht", parse);
    }

}