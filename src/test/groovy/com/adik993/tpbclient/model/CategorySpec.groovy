package com.adik993.tpbclient.model

import com.adik993.tpbclient.exceptions.ParseException
import org.jsoup.Jsoup
import spock.lang.Specification

import static com.adik993.tpbclient.model.Category.TVShows
import static com.adik993.tpbclient.model.Category.Unknown

class CategorySpec extends Specification {

    def "parse - succesfully parse existing category"() {
        given:
        def element = Jsoup.parseBodyFragment("<table><tr><td class=\"vertTh\"><a href=\"/browse/205\" title=\"More from this category\">Video &gt; TV shows</a></td></tr></table>")
                .select("td").first()

        when:
        def result = Category.parse(element)

        then:
        result == TVShows
    }

    def "parse - handle unknown category"() {
        given:
        def element = Jsoup.parse("<table><tr><td class=\"vertTh\"><a href=\"/browse/34534\" title=\"More from this category\">Video &gt; TV shows</a></td></tr></table>")
                .select("td").first()

        when:
        def result = Category.parse(element)

        then:
        result == Unknown
    }

    def "parse - throw ParseException when category id is not a number"() {
        given:
        def element = Jsoup.parse("<table><tr><td class=\"vertTh\"><a href=\"/browse/aaa\" title=\"More from this category\">Video &gt; TV shows</a></td></tr></table>")
                .select("td").first()

        when:
        Category.parse(element)

        then:
        thrown ParseException
    }
}
