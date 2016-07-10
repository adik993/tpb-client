package com.adik993.tpbclient.model;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Adrian on 2016-07-09.
 */
public enum TorrentQuality {
    VIP("VIP"),
    Trusted("Trusted"),
    Unknown("");

    private String typeName;

    TorrentQuality(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public static TorrentQuality parse(String title) {
        Optional<TorrentQuality> optional = Arrays.stream(values())
                .filter(tq -> tq.getTypeName().equals(title))
                .findFirst();
        if (optional.isPresent()) return optional.get();
        else return Unknown;
    }
}
