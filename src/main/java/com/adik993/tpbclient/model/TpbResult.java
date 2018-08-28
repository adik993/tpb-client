package com.adik993.tpbclient.model;

import com.adik993.tpbclient.exceptions.ParseException;
import lombok.Getter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TpbResult {
    private final PageInfo pageInfo;
    private final List<Torrent> torrents;

    public TpbResult(Document document, LocalDateTime now) throws ParseException {
        Elements rows = document.select("#searchResult tbody tr:has(td.vertTh)");
        this.torrents = new ArrayList<>(rows.size());
        for (Element row : rows) {
            this.torrents.add(new Torrent(row, now));
        }
        this.pageInfo = parsePageInfoSafe(document, torrents.size());
    }

    private PageInfo parsePageInfoSafe(Document document, int fallbackPageSize) {
        try {
            return new PageInfo(document);
        } catch (ParseException e) {
            return new PageInfo(fallbackPageSize, -1);
        }
    }
}
