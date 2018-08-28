package com.adik993.tpbclient.model;

import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.Optional;

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

    static TorrentQuality parse(Element element) {
        String title = element.select("a[href*=/user/] img[title]")
                .attr("title");
        Optional<TorrentQuality> optional = Arrays.stream(values())
                .filter(tq -> tq.getTypeName().equals(title))
                .findFirst();
        return optional.orElse(Unknown);
    }
}
