package com.adik993.tpbclient.parsers.row;

import com.adik993.tpbclient.exceptions.ParseException;
import com.adik993.tpbclient.model.TorrentQuality;
import org.jsoup.nodes.Element;

/**
 * Created by adrian on 28/03/17.
 */
public class TorrentQualityParser {
    public static TorrentQuality parse(Element element) throws ParseException {
        String title = element.select("a[href*=/user/] img[title]")
                .attr("title");
        return TorrentQuality.parse(title);
    }
}
