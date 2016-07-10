package com.adik993.tpbclient.parsers;

import com.adik993.tpbclient.exceptions.ParseException;
import com.adik993.tpbclient.model.Torrent;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 2016-07-09.
 */
public class TpbResultTableParser {
    public static List<Torrent> parse(Document doc) throws ParseException {
        Elements h2 = doc.select("h2:has(span:contains(Search results))");
        Elements rows = doc.select("#searchResult tbody tr:has(td.vertTh)");
        List<Torrent> list = new ArrayList<>(rows.size());
        for (Element row : rows) {
            list.add(TpbTorrentRowParser.parse(row));
        }
        return list;
    }
}
