package com.adik993.tpbclient.parsers;

import com.adik993.tpbclient.exceptions.ParseException;
import com.adik993.tpbclient.model.PageInfo;
import com.adik993.tpbclient.model.Torrent;
import com.adik993.tpbclient.model.TpbResult;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created by Adrian on 2016-07-09.
 */
public class TpbResultParser {
    public static TpbResult parse(Document doc) throws ParseException {
        PageInfo pageInfo = null;
        try {
            pageInfo = TpbResultCountParser.parse(doc);
        } catch (ParseException e) {
        }
        List<Torrent> torrents = TpbResultTableParser.parse(doc);
        if (pageInfo == null) pageInfo = new PageInfo(torrents.size(), -1);
        return new TpbResult(pageInfo, torrents);
    }
}
