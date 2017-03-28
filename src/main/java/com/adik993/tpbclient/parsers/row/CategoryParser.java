package com.adik993.tpbclient.parsers.row;

import com.adik993.tpbclient.exceptions.ParseException;
import com.adik993.tpbclient.model.Category;
import org.jsoup.nodes.Element;

/**
 * Created by adrian on 28/03/17.
 */
public class CategoryParser {
    public static Category parse(Element element) throws ParseException {
        String href = element.select("a[href]").attr("href");
        return Category.parse(href);
    }
}
