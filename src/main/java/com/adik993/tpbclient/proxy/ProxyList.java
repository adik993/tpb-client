package com.adik993.tpbclient.proxy;

import com.adik993.tpbclient.proxy.model.Proxy;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import io.reactivex.Flowable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

@Slf4j
public class ProxyList {
    public static final String DEFAULT_URL = "https://thepiratebay-proxylist.se/api/v1/proxies";
    private HttpClient httpClient;
    private HttpGet getProxyList;
    private Gson gson = new Gson();

    public ProxyList(String url) {
        this.httpClient = HttpClientBuilder.create().build();
        this.getProxyList = new HttpGet(url);
        this.getProxyList.addHeader("accept", "application/json");
    }

    public Flowable<Proxy> fetchProxies() {
        return Flowable.fromCallable(() -> httpClient.execute(getProxyList))
                .doOnSubscribe(subscription -> log.debug("fetching tpb proxies.."))
                .map(response -> response.getEntity().getContent())
                .map(this::parseResponse)
                .doOnNext(proxies -> log.trace("fetched proxies: {}", proxies))
                .flatMapIterable(proxies -> proxies);
    }

    private List<Proxy> parseResponse(InputStream stream) throws IOException {
        InputStreamReader reader = new InputStreamReader(stream);
        try {
            log.debug("Parsing tpb proxy list response");
            return ofNullable(gson.fromJson(reader, ProxyListWrapper.class))
                    .map(ProxyListWrapper::getProxies)
                    .orElseThrow(() -> new IOException("Invalid response received"));
        } catch (JsonSyntaxException | JsonIOException e) {
            throw new IOException(e);
        }
    }

    @Getter
    private static class ProxyListWrapper {
        List<Proxy> proxies = new ArrayList<>();
    }
}
