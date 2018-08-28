package com.adik993.tpbclient.model

import org.jsoup.Jsoup
import spock.lang.Specification
import spock.lang.Unroll

import static com.adik993.tpbclient.model.TorrentQuality.*

class TorrentQualitySpec extends Specification {

    @Unroll
    def "parse - succesfully parse quality: #quality"() {
        when:
        def result = parse(element)

        then:
        result == quality

        where:
        quality | element
        VIP     | Jsoup.parseBodyFragment("<table><tr><td><nobr><a href=\"magnet:?xt=urn:btih:55\" title=\"Download this torrent using magnet\"><img src=\"//thepiratebay.org/static/img/icon-magnet.gif\" alt=\"Magnet link\"></a><a href=\"//cdn.bitx.tv/bx.php?torrent=bW\" target=\"_blank\" title=\"Play now using BitX\"><img src=\"//thepiratebay.org/static/img/icons/icon-bitx.png\" alt=\"Play link\"></a><a href=\"/user/Ctrlme\"><img src=\"//thepiratebay.org/static/img/vip.gif\" alt=\"VIP\" title=\"VIP\" style=\"width:11px;\" border=\"0\"></a></nobr></td></tr></table>")
        Trusted | Jsoup.parseBodyFragment("<table><tr><td><nobr><a href=\"magnet:?xt=urn:btih:55\" title=\"Download this torrent using magnet\"><img src=\"//thepiratebay.org/static/img/icon-magnet.gif\" alt=\"Magnet link\"></a><a href=\"//cdn.bitx.tv/bx.php?torrent=bW\" target=\"_blank\" title=\"Play now using BitX\"><img src=\"//thepiratebay.org/static/img/icons/icon-bitx.png\" alt=\"Play link\"></a><a href=\"/user/Ctrlme\"><img src=\"//thepiratebay.org/static/img/trusted.gif\" alt=\"Trusted\" title=\"Trusted\" style=\"width:11px;\" border=\"0\"></a></nobr></td></tr></table>")
        Unknown | Jsoup.parseBodyFragment("<table><tr><td><nobr><a href=\"magnet:?xt=urn:btih:55\" title=\"Download this torrent using magnet\"><img src=\"//thepiratebay.org/static/img/icon-magnet.gif\" alt=\"Magnet link\"></a><a href=\"//cdn.bitx.tv/bx.php?torrent=bW\" target=\"_blank\" title=\"Play now using BitX\"><img src=\"//thepiratebay.org/static/img/icons/icon-bitx.png\" alt=\"Play link\"></a></nobr></td></tr></table>")
    }
}
