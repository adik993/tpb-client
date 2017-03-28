package com.adik993.tpbclient.parsers.row;

import com.adik993.tpbclient.exceptions.ParseException;
import org.jsoup.nodes.Element;

/**
 * Created by adrian on 28/03/17.
 */
public class SeedsParser {
    public static Integer parse(Element element) throws ParseException {
        return Integer.parseInt(element.html());
    }
}
