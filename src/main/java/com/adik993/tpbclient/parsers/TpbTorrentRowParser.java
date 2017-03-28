package com.adik993.tpbclient.parsers;

import com.adik993.tpbclient.exceptions.ParseException;
import com.adik993.tpbclient.model.Torrent;
import com.adik993.tpbclient.parsers.row.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;

/**
 * Created by Adrian on 2016-07-09.
 */
public class TpbTorrentRowParser {

    public static Torrent parse(Element row) throws ParseException {
        Elements cols = row.select("td");
        Torrent torrent = new Torrent();
        torrent.setCategory(CategoryParser.parse(cols.get(0)));
        torrent.setTitle(TitleParser.parse(cols.get(1)));
        torrent.setId(TorrentIdParser.parse(cols.get(1)));
        torrent.setTorrentLink(TorrentLinkParser.parse(cols.get(1)));
        torrent.setPublishDate(PublishDateParser.parse(cols.get(2), LocalDateTime.now()));
        torrent.setMagnetLink(MagnetLinkParser.parse(cols.get(3)));
        torrent.setQuality(TorrentQualityParser.parse(cols.get(3)));
        torrent.setSize(SizeParser.parse(cols.get(4)));
        torrent.setSeeds(SeedsParser.parse(cols.get(5)));
        torrent.setLeeches(LeechesParser.parse(cols.get(6)));
        torrent.setUser(UserParser.parse(cols.get(7)));
        return torrent;
    }
}