package com.adik993.tpbclient.model;

import lombok.Data;

import java.time.temporal.Temporal;

/**
 * Created by Adrian on 2016-07-09.
 */
@Data
public class Torrent {
    long id;
    String title;
    Category category;
    String user;
    String magnetLink;
    String torrentLink;
    Temporal publishDate;
    TorrentQuality quality;
    long size;
    int seeds;
    int leeches;
}
