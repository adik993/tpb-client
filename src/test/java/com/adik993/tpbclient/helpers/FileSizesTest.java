package com.adik993.tpbclient.helpers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileSizesTest {
    @Test
    public void from() throws Exception {
        assertEquals(1L, FileSizes.from("B").getMultiplier());
        assertEquals(1024L, FileSizes.from("KB").getMultiplier());
        assertEquals(1024L, FileSizes.from("KiB").getMultiplier());
        assertEquals(1024L * 1024, FileSizes.from("MB").getMultiplier());
        assertEquals(1024L * 1024, FileSizes.from("MiB").getMultiplier());
        assertEquals(1024L * 1024 * 1024, FileSizes.from("GB").getMultiplier());
        assertEquals(1024L * 1024 * 1024, FileSizes.from("GiB").getMultiplier());
        assertEquals(1024L * 1024 * 1024 * 1024, FileSizes.from("TB").getMultiplier());
        assertEquals(1024L * 1024 * 1024 * 1024, FileSizes.from("TiB").getMultiplier());
        assertEquals(1024L * 1024 * 1024 * 1024 * 1024, FileSizes.from("PB").getMultiplier());
        assertEquals(1024L * 1024 * 1024 * 1024 * 1024, FileSizes.from("PiB").getMultiplier());
    }

}