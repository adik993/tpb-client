package com.adik993.tpbclient.helpers;

import java.util.Arrays;

public enum FileSizes {
    B(new String[]{"B"}, 1L),
    KB(new String[]{"KB", "KiB"}, 1024L * B.multiplier),
    MB(new String[]{"MB", "MiB"}, 1024L * KB.multiplier),
    GB(new String[]{"GB", "GiB"}, 1024L * MB.multiplier),
    TB(new String[]{"TB", "TiB"}, 1024L * GB.multiplier),
    PB(new String[]{"PB", "PiB"}, 1024L * TB.multiplier);

    private final String[] aliases;
    private final long multiplier;

    FileSizes(String[] aliases, long multiplier) {
        this.aliases = aliases;
        this.multiplier = multiplier;
    }

    public static FileSizes from(String abbreviation) {
        return Arrays.stream(values())
                .filter(fs -> Arrays.stream(fs.aliases).anyMatch(a -> a.equals(abbreviation)))
                .findFirst().orElse(null);
    }

    public String[] getAliases() {
        return aliases;
    }

    public long getMultiplier() {
        return multiplier;
    }
}
