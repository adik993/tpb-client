package com.adik993.tpbclient.model

import org.jsoup.Jsoup
import spock.lang.Specification

import static java.nio.charset.StandardCharsets.UTF_8
import static java.time.LocalDateTime.now

class TpbResultSpec extends Specification {
    def "parse result with page info"() {
        given:
        def stream = getClass().getClassLoader().getResourceAsStream("result.html")
        def document = Jsoup.parse(stream, UTF_8.toString(), "http://thepiratebay.org")

        when:
        def result = new TpbResult(document, now())

        then:
        result.pageInfo.pageSize == 30
        result.pageInfo.total == 666
    }

    def "parse result without page info"() {
        given:
        def stream = getClass().getClassLoader().getResourceAsStream("result.html")
        def document = Jsoup.parse(stream, UTF_8.toString(), "http://thepiratebay.org")
        document.select("h2:has(span:contains(Search results))").first().remove()

        when:
        def result = new TpbResult(document, now())

        then:
        result.pageInfo.pageSize == 30
        result.pageInfo.total == -1
    }
}
