package com.adik993.tpbclient.model;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
enum FileSizes {
    B("B", 1L),
    KB("KB", 1000L * B.multiplier),
    MB("MB", 1000L * KB.multiplier),
    GB("GB", 1000L * MB.multiplier),
    TB("TB", 1000L * GB.multiplier),
    PB("PB", 1000L * TB.multiplier),
    KiB("KiB", 1024L * B.multiplier),
    MiB("MiB", 1024L * KiB.multiplier),
    GiB("GiB", 1024L * MiB.multiplier),
    TiB("TiB", 1024L * GiB.multiplier),
    PiB("PiB", 1024L * TiB.multiplier);

    private final String abbreviation;
    private final long multiplier;

    public static FileSizes from(String abbreviation) {
        return Arrays.stream(values())
                .filter(fs -> fs.abbreviation.toLowerCase().equals(abbreviation.toLowerCase()))
                .findFirst().orElse(null);
    }

    public long toBytes(double value) {
        return (long) (multiplier * value);
    }
}
