package com.adik993.tpbclient

import com.adik993.tpbclient.exceptions.ClientBuildException
import com.adik993.tpbclient.proxy.model.Proxy
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.github.tomjankes.wiremock.WireMockGroovy
import org.junit.Rule
import spock.lang.Specification
import spock.lang.Unroll

import static com.adik993.tpbclient.model.Category.Video
import static com.adik993.tpbclient.model.OrderBy.SeedsDesc
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options

class TpbClientSpec extends Specification {
    @Rule
    WireMockRule wireMockRule = new WireMockRule(options().dynamicPort())

    def "fromProxy - create client from Proxy object"() {
        when:
        def result = TpbClient.fromProxy(proxy)

        then:
        result.getHost().toString() == expected

        where:
        proxy                                       | expected
        new Proxy("tpb.org", 1f, true, "PL", false) | "https://tpb.org"
        new Proxy("tpb.se", 1f, false, "PL", false) | "http://tpb.se"

    }

    def "withDefaultHost - create client with to main thepiratebay.org site"() {
        given:
        def underSpec = TpbClient.withDefaultHost()

        when:
        def result = underSpec.host

        then:
        result.toString() == "https://thepiratebay.org"
    }

    @Unroll
    def "withHost - build valid url when secure is #secure"() {
        given:
        def underSpec = TpbClient.withHost("cos.org", secure)

        when:
        def result = underSpec.getHost()

        then:
        result.toString() == expected

        where:
        secure | expected
        true   | "https://cos.org"
        false  | "http://cos.org"
    }

    def "withHost - throw ClientBuildException when url is malformed"() {
        when:
        TpbClient.withHost("asd!##", true)

        then:
        thrown ClientBuildException
    }

    def "search - call service and parse response"() {
        given:
        def wireMock = new WireMockGroovy(wireMockRule.port())
        wireMock.stub {
            request {
                method "GET"
                url "/s/?q=Banshee&category=200&page=1&orderby=7"
            }
            response {
                status 200
                body getClass().getClassLoader().getResourceAsStream("result.html").getText()
            }
        }
        def underSpec = TpbClient.withHost("localhost:${wireMockRule.port()}", false)

        when:
        def subscriber = underSpec.search("Banshee", Video, 1, SeedsDesc).test()
        subscriber.awaitTerminalEvent()

        then:
        subscriber.assertNoErrors()
        def result = subscriber.values().first()
        result.pageInfo.pageSize == 30
        result.pageInfo.total == 666
        result.torrents.size() == 30
    }
}
