package com.adik993.tpbclient.parsers;

import com.adik993.tpbclient.helpers.FileSizes;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Adrian on 2016-07-10.
 */
public class SizeParserTest {

    @Test
    public void parse() throws Exception {
        Document doc = Jsoup.parseBodyFragment("<table><tr><td align=\"right\">392.33&nbsp;MiB</td></tr></table>");
        Element td = doc.select("td").first();
        long parse = TpbTorrentRowParser.SizeParser.parse(td);
        assertEquals((long) (392.33 * FileSizes.MB.getMultiplier()), parse);
    }
}