package com.adik993.tpbclient.parsers;

import com.adik993.tpbclient.helpers.FileSizes;
import com.adik993.tpbclient.model.Category;
import com.adik993.tpbclient.model.Torrent;
import com.adik993.tpbclient.model.TorrentQuality;
import junit.framework.TestCase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by Adrian on 2016-07-10.
 */
public class TpbTorrentRowParserTest extends TestCase {

    public void testParse() throws Exception {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("row.html");
        Document doc = Jsoup.parse(stream, StandardCharsets.UTF_8.name(), "https://thepiratebay.org");
        Element tr = doc.select("tr").first();
        Torrent torrent = TpbTorrentRowParser.parse(tr);
        LocalDate now = LocalDate.now();
        assertEquals(Category.TVShows, torrent.getCategory());
        assertEquals("Banshee.S04E02.FRENCH.LD.HDTV.XviD-ZT", torrent.getTitle());
        assertEquals(15256679, torrent.getId());
        assertEquals(LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 50), torrent.getPublishDate());
        assertEquals("magnet:?xt=urn:btih:552f42a4601f245a89d95c683f235d5de1591306&dn=Banshee.S04E02.FRENCH.LD.HDTV.XviD-ZT&tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80&tr=udp%3A%2F%2Fopen.demonii.com%3A1337&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Fexodus.desync.com%3A6969", torrent.getMagnetLink());
        assertEquals(TorrentQuality.VIP, torrent.getQuality());
        assertEquals((long) (392.33 * FileSizes.MB.getMultiplier()), torrent.getSize());
        assertEquals(1061, torrent.getSeeds());
        assertEquals(220, torrent.getLeeches());
        assertEquals("Ctrlme", torrent.getUser());
        assertEquals("https://thepiratebay.org/torrent/15256679/Banshee.S04E02.FRENCH.LD.HDTV.XviD-ZT", torrent.getTorrentLink());
    }

}