package com.adik993.tpbclient.parsers;

import com.adik993.tpbclient.exceptions.ParseException;
import com.adik993.tpbclient.model.Category;
import com.adik993.tpbclient.model.Torrent;
import com.adik993.tpbclient.model.TorrentQuality;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Adrian on 2016-07-09.
 */
public class TpbTorrentRowParser {


    static Torrent parse(Element row) throws ParseException {
        Elements cols = row.select("td");
        Torrent torrent = new Torrent();
        torrent.setCategory(CategoryParser.parse(cols.get(0)));
        torrent.setTitle(TitleParser.parse(cols.get(1)));
        torrent.setId(TorrentIdParser.parse(cols.get(1)));
        torrent.setTorrentLink(TorrentLinkParser.parse(cols.get(1)));
        torrent.setPublishDate(PublishDateParser.parse(cols.get(2)));
        torrent.setMagnetLink(MagnetLinkParser.parse(cols.get(3)));
        torrent.setQuality(TorrentQualityParser.parse(cols.get(3)));
        torrent.setSize(SizeParser.parse(cols.get(4)));
        torrent.setSeeds(SeedsParser.parse(cols.get(5)));
        torrent.setLeeches(LeechesParser.parse(cols.get(6)));
        torrent.setUser(UserParser.parse(cols.get(7)));
        return torrent;
    }

    public static class CategoryParser {
        public static Category parse(Element element) throws ParseException {
            String href = element.select("a[href]").attr("href");
            return Category.parse(href);
        }
    }

    public static class TitleParser {
        public static String parse(Element element) {
            return element.select("a[title]").html();
        }
    }

    public static class TorrentIdParser {
        private static final Pattern PATTERN = Pattern.compile("/torrent/([0-9]+).*");

        public static long parse(Element element) throws ParseException {
            String href = element.select("a[title]").attr("href");
            Matcher matcher = PATTERN.matcher(href);
            if (!matcher.matches()) throw new ParseException("Unable to parse torrent id from " + href);
            return Long.parseLong(matcher.group(1));
        }
    }

    public static class TorrentLinkParser {
        private static final Pattern PATTERN = Pattern.compile("/torrent/[0-9]+.*");

        public static String parse(Element element) throws ParseException {
            String href = element.select("a[title]").attr("href");
            Matcher matcher = PATTERN.matcher(href);
            if (!matcher.matches()) throw new ParseException("Unable to parse torrent link from " + href);
            try {
                URL url = new URL(element.baseUri());
                return new URL(url.getProtocol() + "://" + url.getHost() + href).toString();
            } catch (MalformedURLException e) {
                throw new ParseException("Unable to parse url " + element.baseUri() + href);
            }
        }
    }

    public static class PublishDateParser {
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-dd yyyy");
        private static final DateTimeFormatter FORMATTER2 = DateTimeFormatter.ofPattern("MM-dd yyyy HH:mm");
        private static final DateTimeFormatter FORMATTER_MM_DD = DateTimeFormatter.ofPattern("MM-dd");
        private static final Pattern PATTERN_X_AGO = Pattern.compile("([0-9]+)\\s+mins\\s+ago");

        static Temporal parse(Element element) throws ParseException {
            String str = element.html().replace("&nbsp;", " ");
            str = str.replace("<b>", "").replace("</b>", "");
            Matcher matcher = PATTERN_X_AGO.matcher(str);
            if (matcher.matches()) {
                int ago = Integer.parseInt(matcher.group(1));
                return LocalDateTime.now().minusMinutes(ago);
            }

            str = str.replace("Today", LocalDate.now().format(FORMATTER_MM_DD));
            try {
                return LocalDate.parse(str, FORMATTER);
            } catch (DateTimeParseException e) {
                str = str.replace(" ", " " + LocalDateTime.now().getYear() + " ");
                return LocalDateTime.parse(str, FORMATTER2);
            }
        }
    }

    public static class MagnetLinkParser {
        public static String parse(Element element) throws ParseException {
            String magnet = element.select("a[href*=magnet:?xt=urn:]")
                    .attr("href");
            return "".equals(magnet) ? null : URI.create(magnet).toString();
        }
    }

    public static class TorrentQualityParser {
        public static TorrentQuality parse(Element element) throws ParseException {
            String title = element.select("a[href*=/user/] img[title]")
                    .attr("title");
            return TorrentQuality.parse(title);
        }
    }

    public static class SizeParser {
        public static long parse(Element element) throws ParseException {
            return FileSizeParser.parse(element.html().replace("&nbsp;", " "));
        }
    }

    public static class SeedsParser {
        public static Integer parse(Element element) throws ParseException {
            return Integer.parseInt(element.html());
        }
    }

    public static class LeechesParser {
        public static Integer parse(Element element) throws ParseException {
            return Integer.parseInt(element.html());
        }
    }

    public static class UserParser {
        private static final Pattern PATTERN = Pattern.compile("/user/([^/]+)\\/|$");

        static String parse(Element element) throws ParseException {
            String href = element.select("a[href*=/user/")
                    .attr("href");
            Matcher matcher = PATTERN.matcher(href);
            if (!matcher.matches()) throw new ParseException("Unable to parse user from " + href);
            return matcher.group(1);
        }
    }
}