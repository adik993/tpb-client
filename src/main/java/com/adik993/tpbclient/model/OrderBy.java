package com.adik993.tpbclient.model;

public enum OrderBy {
    NameDesc(1),
    NameAsc(2),
    DateDesc(3),
    DateAsc(4),
    SizeDesc(5),
    SizeAsc(6),
    SeedsDesc(7),
    SeedsAsc(8),
    LeechesDesc(9),
    LeechesAsc(10);

    private int id;

    OrderBy(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return getId() + "";
    }
}
