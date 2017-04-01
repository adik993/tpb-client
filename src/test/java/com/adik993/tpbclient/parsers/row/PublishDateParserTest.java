package com.adik993.tpbclient.parsers.row;

import com.adik993.tpbclient.exceptions.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;

import static org.junit.Assert.assertEquals;

/**
 * Created by Adrian on 2016-07-10.
 */
public class PublishDateParserTest {
    private static final LocalDateTime now = LocalDateTime.now();

    private Element prepareElement(String date) {
        Document doc = Jsoup.parseBodyFragment("<table><tr><td>" + date.replace(" ", "&nbsp;") + "<td></tr></table>");
        return doc.select("td").first();
    }

    @Test
    public void parseXMinsAgo() throws ParseException {
        Element td = prepareElement("<b>20 mins ago</b>");
        Temporal date = PublishDateParser.parse(td, now);
        assertEquals(now.minusMinutes(20), date);
    }

    @Test
    public void parseToday() throws ParseException {
        Element td = prepareElement("Today 11:08");
        Temporal date = PublishDateParser.parse(td, now);
        assertEquals(LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 11, 8), date);
    }

    @Test
    public void parseYday() throws ParseException {
        Element td = prepareElement("Y-day 11:08");
        LocalDateTime yday = now.minusDays(1);
        Temporal date = PublishDateParser.parse(td, now);
        assertEquals(LocalDateTime.of(yday.getYear(), yday.getMonth(), yday.getDayOfMonth(), 11, 8), date);
    }

    @Test
    public void parseTodayHour() throws ParseException {
        Element td = prepareElement("07-21 09:11");
        Temporal date = PublishDateParser.parse(td, now);
        assertEquals(LocalDateTime.of(now.getYear(), 7, 21, 9, 11), date);
    }

    @Test
    public void parseDayYer() throws ParseException {
        Element td = prepareElement("03-07 2015");
        LocalDateTime date = PublishDateParser.parse(td, now);
        assertEquals(LocalDateTime.of(2015, 3, 7, 0, 0), date);
    }
}