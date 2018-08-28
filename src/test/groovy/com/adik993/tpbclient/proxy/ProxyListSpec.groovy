package com.adik993.tpbclient.proxy

import com.adik993.tpbclient.proxy.model.Proxy
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.github.tomjankes.wiremock.WireMockGroovy
import org.junit.Rule
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options

class ProxyListSpec extends Specification {

    @Rule
    WireMockRule wireMockRule = new WireMockRule(options().dynamicPort())

    def "fetchProxies - fetch and parse response"() {
        given:
        def wireMock = new WireMockGroovy(wireMockRule.port())
        wireMock.stub {
            request {
                method "GET"
                url "/list"
            }
            response {
                status 200
                body getClass().getClassLoader().getResourceAsStream("proxy-list.json").getText()
            }
        }
        def underSpec = new ProxyList(wireMockRule.url("/list"))

        when:
        def subscriber = underSpec.fetchProxies().test()
        subscriber.awaitTerminalEvent()

        then:
        subscriber.assertNoErrors()
        subscriber.assertValueCount(24)
        subscriber.assertValueAt(0, new Proxy("thebay.tv", 0.067, true, "UK", true))
    }

    def "fetchProxies - error during fetch passed to the flow"() {
        given:
        def wireMock = new WireMockGroovy(wireMockRule.port())
        wireMock.stub {
            request {
                method "GET"
                url "/list"
            }
            response {
                status 404
            }
        }
        def underSpec = new ProxyList(wireMockRule.url("/list"))

        when:
        def subscriber = underSpec.fetchProxies().test()
        subscriber.awaitTerminalEvent()

        then:
        subscriber.assertError(IOException)
        subscriber.assertNoValues()
    }

    def "fetchProxies - throw IOException on invalid json"() {
        given:
        def wireMock = new WireMockGroovy(wireMockRule.port())
        wireMock.stub {
            request {
                method "GET"
                url "/list"
            }
            response {
                status 200
                body "[]"
            }
        }
        def underSpec = new ProxyList(wireMockRule.url("/list"))

        when:
        def subscriber = underSpec.fetchProxies().test()
        subscriber.awaitTerminalEvent()

        then:
        subscriber.assertError(IOException)
        subscriber.assertNoValues()
    }
}
