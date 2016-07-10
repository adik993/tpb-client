package com.adik993.tpbclient.model;

import lombok.Data;

import java.util.List;

/**
 * Created by Adrian on 2016-07-09.
 */
@Data
public class TpbResult {
    private final PageInfo pageInfo;
    private final List<Torrent> torrents;

    public TpbResult(PageInfo pageInfo, List<Torrent> torrents) {
        this.pageInfo = pageInfo;
        this.torrents = torrents;
    }
}
