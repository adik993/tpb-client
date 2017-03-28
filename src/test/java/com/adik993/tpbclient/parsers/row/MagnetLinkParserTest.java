package com.adik993.tpbclient.parsers.row;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Adrian on 2016-07-10.
 */
public class MagnetLinkParserTest {
    @Test
    public void parse() throws Exception {
        Document doc = Jsoup.parseBodyFragment("<table><tr><td><nobr><a href=\"magnet:?xt=urn:btih:55\" title=\"Download this torrent using magnet\"><img src=\"//thepiratebay.org/static/img/icon-magnet.gif\" alt=\"Magnet link\"></a><a href=\"//cdn.bitx.tv/bx.php?torrent=bW\" target=\"_blank\" title=\"Play now using BitX\"><img src=\"//thepiratebay.org/static/img/icons/icon-bitx.png\" alt=\"Play link\"></a><a href=\"/user/Ctrlme\"><img src=\"//thepiratebay.org/static/img/vip.gif\" alt=\"VIP\" title=\"VIP\" style=\"width:11px;\" border=\"0\"></a></nobr></td></tr></table>");
        Element td = doc.select("td").first();
        String parse = MagnetLinkParser.parse(td);
        assertEquals("magnet:?xt=urn:btih:55", parse);
    }

    @Test
    public void parseNoMagnet() throws Exception {
        Document doc = Jsoup.parseBodyFragment("<table><tr><td><nobr><img src=\"//thepiratebay.org/static/img/icon-magnet.gif\" alt=\"Magnet link\"><a href=\"//cdn.bitx.tv/bx.php?torrent=bW\" target=\"_blank\" title=\"Play now using BitX\"><img src=\"//thepiratebay.org/static/img/icons/icon-bitx.png\" alt=\"Play link\"></a><a href=\"/user/Ctrlme\"><img src=\"//thepiratebay.org/static/img/vip.gif\" alt=\"VIP\" title=\"VIP\" style=\"width:11px;\" border=\"0\"></a></nobr></td></tr></table>");
        Element td = doc.select("td").first();
        String parse = MagnetLinkParser.parse(td);
        assertNull(parse);
    }

}