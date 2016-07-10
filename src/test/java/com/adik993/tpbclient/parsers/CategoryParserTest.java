package com.adik993.tpbclient.parsers;

import com.adik993.tpbclient.exceptions.ParseException;
import com.adik993.tpbclient.model.Category;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Adrian on 2016-07-10.
 */
public class CategoryParserTest {

    @Test
    public void testParse() throws Exception {
        Document doc = Jsoup.parseBodyFragment("<table><tr><td class=\"vertTh\"><a href=\"/browse/205\" title=\"More from this category\">Video &gt; TV shows</a></td></tr></table>");
        Element td = doc.select("td").first();
        Category category = TpbTorrentRowParser.CategoryParser.parse(td);
        assertEquals(Category.TVShows, category);
    }

    @Test
    public void unknownCategory() throws Exception {
        Document doc = Jsoup.parse("<table><tr><td class=\"vertTh\"><a href=\"/browse/34534\" title=\"More from this category\">Video &gt; TV shows</a></td></tr></table>");
        Element td = doc.select("td").first();
        Category category = TpbTorrentRowParser.CategoryParser.parse(td);
        assertEquals(Category.Unknown, category);
    }

    @Test(expected = ParseException.class)
    public void invalidNumber() throws Exception {
        Document doc = Jsoup.parse("<table><tr><td class=\"vertTh\"><a href=\"/browse/aaa\" title=\"More from this category\">Video &gt; TV shows</a></td></tr></table>");
        Element td = doc.select("td").first();
        Category category = TpbTorrentRowParser.CategoryParser.parse(td);
    }

}