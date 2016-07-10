package com.adik993.tpbclient.parsers;

import com.adik993.tpbclient.model.TorrentQuality;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Adrian on 2016-07-10.
 */
public class TorrentQualityParserTest {
    @Test
    public void parseVIP() throws Exception {
        Document doc = Jsoup.parseBodyFragment("<table><tr><td><nobr><a href=\"magnet:?xt=urn:btih:55\" title=\"Download this torrent using magnet\"><img src=\"//thepiratebay.org/static/img/icon-magnet.gif\" alt=\"Magnet link\"></a><a href=\"//cdn.bitx.tv/bx.php?torrent=bW\" target=\"_blank\" title=\"Play now using BitX\"><img src=\"//thepiratebay.org/static/img/icons/icon-bitx.png\" alt=\"Play link\"></a><a href=\"/user/Ctrlme\"><img src=\"//thepiratebay.org/static/img/vip.gif\" alt=\"VIP\" title=\"VIP\" style=\"width:11px;\" border=\"0\"></a></nobr></td></tr></table>");
        Element td = doc.select("td").first();
        TorrentQuality parse = TpbTorrentRowParser.TorrentQualityParser.parse(td);
        assertEquals(TorrentQuality.VIP, parse);
    }

    @Test
    public void parseTrusted() throws Exception {
        Document doc = Jsoup.parseBodyFragment("<table><tr><td><nobr><a href=\"magnet:?xt=urn:btih:55\" title=\"Download this torrent using magnet\"><img src=\"//thepiratebay.org/static/img/icon-magnet.gif\" alt=\"Magnet link\"></a><a href=\"//cdn.bitx.tv/bx.php?torrent=bW\" target=\"_blank\" title=\"Play now using BitX\"><img src=\"//thepiratebay.org/static/img/icons/icon-bitx.png\" alt=\"Play link\"></a><a href=\"/user/Ctrlme\"><img src=\"//thepiratebay.org/static/img/trusted.gif\" alt=\"Trusted\" title=\"Trusted\" style=\"width:11px;\" border=\"0\"></a></nobr></td></tr></table>");
        Element td = doc.select("td").first();
        TorrentQuality parse = TpbTorrentRowParser.TorrentQualityParser.parse(td);
        assertEquals(TorrentQuality.Trusted, parse);
    }

    @Test
    public void parseUnknown() throws Exception {
        Document doc = Jsoup.parseBodyFragment("<table><tr><td><nobr><a href=\"magnet:?xt=urn:btih:55\" title=\"Download this torrent using magnet\"><img src=\"//thepiratebay.org/static/img/icon-magnet.gif\" alt=\"Magnet link\"></a><a href=\"//cdn.bitx.tv/bx.php?torrent=bW\" target=\"_blank\" title=\"Play now using BitX\"><img src=\"//thepiratebay.org/static/img/icons/icon-bitx.png\" alt=\"Play link\"></a></nobr></td></tr></table>");
        Element td = doc.select("td").first();
        TorrentQuality parse = TpbTorrentRowParser.TorrentQualityParser.parse(td);
        assertEquals(TorrentQuality.Unknown, parse);
    }

}