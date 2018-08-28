package com.adik993.tpbclient.model

import org.jsoup.Jsoup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static com.adik993.tpbclient.model.Category.TVShows
import static com.adik993.tpbclient.model.FileSizes.MiB
import static com.adik993.tpbclient.model.TorrentQuality.VIP
import static java.nio.charset.StandardCharsets.UTF_8
import static java.time.LocalDateTime.now
import static java.time.LocalDateTime.of

class TorrentSpec extends Specification {
    @Shared
    def now = now()
    @Shared
    def yesterday = now.minusDays(1)

    def "parse row"() {
        given:
        def stream = getClass().getClassLoader().getResourceAsStream("row.html")
        def doc = Jsoup.parse(stream, UTF_8.name(), "https://thepiratebay.org")
        def row = doc.select("tr").first()

        when:
        def result = new Torrent(row, now)

        then:
        result.category == TVShows
        result.title == "Banshee.S04E02.FRENCH.LD.HDTV.XviD-ZT"
        result.id == 15256679
        result.publishDate == of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 6, 50)
        result.magnetLink == "magnet:?xt=urn:btih:552f42a4601f245a89d95c683f235d5de1591306&dn=Banshee.S04E02.FRENCH.LD.HDTV.XviD-ZT&tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80&tr=udp%3A%2F%2Fopen.demonii.com%3A1337&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Fexodus.desync.com%3A6969"
        result.quality == VIP
        result.size == MiB.toBytes(392.33)
        result.seeds == 1061
        result.leeches == 220
        result.user == "Ctrlme"
        result.torrentLink == "https://thepiratebay.org/torrent/15256679/Banshee.S04E02.FRENCH.LD.HDTV.XviD-ZT"
    }

    def "parse row with JavaScript embeded in title"() {
        given:
        def stream = getClass().getClassLoader().getResourceAsStream("row-title-embeded-js.html")
        def doc = Jsoup.parse(stream, UTF_8.name(), "https://thepiratebay.org")
        def row = doc.select("tr").first()
        row.select("td").get(1).html()

        when:
        def result = new Torrent(row, now)

        then:
        result.title == "07.09.28.Next.2007.HDDVD.720p.x264@Ht"
    }

    def "parse row with no magnet link"() {
        given:
        def stream = getClass().getClassLoader().getResourceAsStream("row-no-magnet-link.html")
        def doc = Jsoup.parse(stream, UTF_8.name(), "https://thepiratebay.org")
        def row = doc.select("tr").first()

        when:
        def result = new Torrent(row, now)

        then:
        result.magnetLink == null
    }

    @Unroll
    def "handle date formats: #date"() {
        given:
        def stream = getClass().getClassLoader().getResourceAsStream("row.html")
        def doc = Jsoup.parse(stream, UTF_8.name(), "https://thepiratebay.org")
        def row = doc.select("tr").first()
        row.select("td").get(2).html(date)

        when:
        def result = new Torrent(row, now)

        then:
        result.publishDate == expected

        where:
        date                 | expected
        "<b>20 mins ago</b>" | now.minusMinutes(20)
        "Today 11:08"        | of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 11, 8)
        "Y-day 11:08"        | of(yesterday.getYear(), yesterday.getMonth(), yesterday.getDayOfMonth(), 11, 8)
        "07-21 09:11"        | of(now.getYear(), 7, 21, 9, 11)
        "03-07 2015"         | of(2015, 3, 7, 0, 0)
    }
}
