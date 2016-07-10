package com.adik993.tpbclient.helpers;

import java.util.Arrays;

/**
 * Created by Adrian on 2016-07-09.
 */
public enum FileSizes {
    KB(new String[]{"KB", "KiB"}, 1024),
    MB(new String[]{"MB", "MiB"}, 1024 * KB.multiplier),
    GB(new String[]{"GB", "GiB"}, 1024 * MB.multiplier),
    TB(new String[]{"TB", "TiB"}, 1024 * GB.multiplier),
    PB(new String[]{"PB", "PiB"}, 1024 * TB.multiplier);

    private final String[] aliases;
    private final int multiplier;

    FileSizes(String[] aliases, int multiplier) {
        this.aliases = aliases;
        this.multiplier = multiplier;
    }

    public static FileSizes from(String abbreviation) {
        return Arrays.stream(values())
                .filter(fs -> Arrays.stream(fs.aliases).anyMatch(a -> a.equals(abbreviation)))
                .findFirst().get();
    }

    public String[] getAliases() {
        return aliases;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
