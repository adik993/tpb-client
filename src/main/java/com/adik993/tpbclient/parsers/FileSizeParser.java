package com.adik993.tpbclient.parsers;

import com.adik993.tpbclient.exceptions.ParseException;
import com.adik993.tpbclient.helpers.FileSizes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Adrian on 2016-07-09.
 */
public class FileSizeParser {
    private static final Pattern PATTERN = Pattern.compile("^(\\d*\\.?\\d*)\\s*([a-zA-Z]+)$");

    public static long parse(String size) throws ParseException {
        Matcher matcher = PATTERN.matcher(size);
        if (!matcher.matches()) throw new ParseException("Unable to parse " + size);
        return (long) (Double.parseDouble(matcher.group(1)) * FileSizes.from(matcher.group(2)).getMultiplier());
    }
}
