package com.adik993.tpbclient.model;

import com.adik993.tpbclient.exceptions.ParseException;
import lombok.Getter;
import lombok.ToString;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

@Getter
@ToString
public class Torrent {
    private static final Pattern TORRENT_ID = Pattern.compile("/torrent/([0-9]+).*");
    private static final Pattern TORRENT_LINK = Pattern.compile("/torrent/[0-9]+.*");
    private static final Pattern SIZE = Pattern.compile("^(\\d*\\.?\\d*)\\s*([a-zA-Z]+)$");
    private static final Pattern USER = Pattern.compile("/user/([^/]+)/|$");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-dd yyyy");
    private static final DateTimeFormatter FORMATTER2 = DateTimeFormatter.ofPattern("MM-dd yyyy HH:mm");
    private static final DateTimeFormatter FORMATTER_MM_DD = DateTimeFormatter.ofPattern("MM-dd");
    private static final Pattern PATTERN_X_AGO = Pattern.compile("([0-9]+)\\s+mins\\s+ago");

    long id;
    String title;
    Category category;
    String user;
    String magnetLink;
    String torrentLink;
    LocalDateTime publishDate;
    TorrentQuality quality;
    long size;
    int seeds;
    int leeches;

    Torrent(Element row, LocalDateTime now) throws ParseException {
        Elements cols = row.select("td");
        this.category = Category.parse(cols.get(0));
        this.title = parseTitle(cols.get(1));
        this.id = parseId(cols.get(1));
        this.torrentLink = parseLink(cols.get(1));
        this.publishDate = parseDate(cols.get(2), now);
        this.magnetLink = parseMagnetLink(cols.get(3));
        this.quality = TorrentQuality.parse(cols.get(3));
        this.size = parseSize(cols.get(4));
        this.seeds = parseInt(cols.get(5).html());
        this.leeches = parseInt(cols.get(6).html());
        this.user = parseUser(cols.get(7));
    }

    private String parseTitle(Element element) {
        return element.select("a[title]").attr("title")
                .replaceFirst("Details for ", "");
    }

    private long parseId(Element element) throws ParseException {
        String href = element.select("a[title]").attr("href");
        Matcher matcher = TORRENT_ID.matcher(href);
        if (!matcher.matches()) throw new ParseException("Unable to parse torrent id from " + href);
        return Long.parseLong(matcher.group(1));
    }

    private String parseLink(Element element) throws ParseException {
        String href = element.select("a[title]").attr("href");
        Matcher matcher = TORRENT_LINK.matcher(href);
        if (!matcher.matches()) throw new ParseException("Unable to parse torrent link from " + href);
        try {
            URL url = new URL(element.baseUri());
            return new URL(url.getProtocol() + "://" + url.getHost() + href).toString();
        } catch (MalformedURLException e) {
            throw new ParseException("Unable to parse url " + element.baseUri() + href);
        }
    }

    private LocalDateTime parseDate(Element element, LocalDateTime now) {
        String str = element.html().replace("&nbsp;", " ");
        str = str.replace("<b>", "").replace("</b>", "");
        Matcher matcher = PATTERN_X_AGO.matcher(str);
        if (matcher.matches()) {
            int ago = parseInt(matcher.group(1));
            return now.minusMinutes(ago);
        }

        str = str.replace("Today", now.format(FORMATTER_MM_DD));
        str = str.replace("Y-day", now.minusDays(1).format(FORMATTER_MM_DD));
        try {
            return LocalDate.parse(str, FORMATTER).atTime(0, 0);
        } catch (DateTimeParseException e) {
            str = str.replace(" ", " " + now.getYear() + " ");
            return LocalDateTime.parse(str, FORMATTER2);
        }
    }

    private String parseMagnetLink(Element element) {
        String magnet = element.select("a[href*=magnet:?xt=urn:]")
                .attr("href");
        return "".equals(magnet) ? null : URI.create(magnet).toString();
    }

    private long parseSize(Element element) throws ParseException {
        String size = element.html().replace("&nbsp;", " ");
        Matcher matcher = SIZE.matcher(size);
        if (!matcher.matches()) throw new ParseException("Unable to parse " + size);
        return FileSizes.from(matcher.group(2)).toBytes(parseDouble(matcher.group(1)));
    }

    private String parseUser(Element element) throws ParseException {
        String href = element.select("a[href*=/user/]")
                .attr("href");
        Matcher matcher = USER.matcher(href);
        if (!matcher.matches()) throw new ParseException("Unable to parse user from " + href);
        return matcher.group(1);
    }
}
