package com.adik993.tpbclient.proxy;

import com.adik993.tpbclient.proxy.model.Proxy;
import com.adik993.tpbclient.proxy.model.ProxyListWrapper;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
public class ProxyList {
    private static final String PROXY_LIS_URL = "https://thepiratebay-proxylist.org/api/v1/proxies";
    private HttpClient httpClient;
    private HttpGet getProxyList;
    private Gson gson = new Gson();
    private volatile List<Proxy> proxies;

    public ProxyList() {
        this(HttpClientBuilder.create().build());
    }

    public ProxyList(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.getProxyList = new HttpGet(PROXY_LIS_URL);
        this.getProxyList.addHeader("accept", "application/json");
    }

    public void init() throws IOException {
        log.debug("Fetching proxies");
        proxies = fetchProxies();
        log.debug("Proxies fetched");
    }

    List<Proxy> fetchProxies() throws IOException {
        HttpResponse response = httpClient.execute(getProxyList);
        return parseResponse(response.getEntity().getContent());
    }

    List<Proxy> parseResponse(InputStream stream) throws IOException {
        InputStreamReader reader = new InputStreamReader(stream);
        try {
            log.debug("Parsing tpb proxy list response");
            return gson.fromJson(reader, ProxyListWrapper.class).getProxies();
        } catch (JsonSyntaxException | JsonIOException e) {
            throw new IOException(e);
        }
    }

    public List<Proxy> getProxyList() throws IOException {
        if (proxies == null) {
            synchronized (this) {
                if (proxies == null) {
                    init();
                }
            }
        }
        return proxies;
    }

    public List<Proxy> getProxyListIfPresent() {
        return proxies;
    }
}
