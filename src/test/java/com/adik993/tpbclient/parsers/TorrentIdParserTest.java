package com.adik993.tpbclient.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Adrian on 2016-07-10.
 */
public class TorrentIdParserTest {

    @Test
    public void parse() throws Exception {
        Document doc = Jsoup.parseBodyFragment("<table><tr><td><a href=\"/torrent/15256679/Banshee.S04E02.FRENCH.LD.HDTV.XviD-ZT\" title=\"Details for Banshee.S04E02.FRENCH.LD.HDTV.XviD-ZT\">Banshee.S04E02.FRENCH.LD.HDTV.XviD-ZT</a> </td></tr></table>");
        Element td = doc.select("td").first();
        long parse = TpbTorrentRowParser.TorrentIdParser.parse(td);
        assertEquals(15256679, parse);
    }

}