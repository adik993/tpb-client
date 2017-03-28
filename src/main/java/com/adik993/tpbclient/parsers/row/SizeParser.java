package com.adik993.tpbclient.parsers.row;

import com.adik993.tpbclient.exceptions.ParseException;
import com.adik993.tpbclient.parsers.FileSizeParser;
import org.jsoup.nodes.Element;

/**
 * Created by adrian on 28/03/17.
 */
public class SizeParser {
    public static long parse(Element element) throws ParseException {
        return FileSizeParser.parse(element.html().replace("&nbsp;", " "));
    }
}
