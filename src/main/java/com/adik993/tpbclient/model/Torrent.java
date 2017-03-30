package com.adik993.tpbclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by Adrian on 2016-07-09.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Torrent {
    long id;
    String title;
    Category category;
    String user;
    String magnetLink;
    String torrentLink;
    LocalDateTime publishDate;
    TorrentQuality quality;
    long size;
    int seeds;
    int leeches;
}
