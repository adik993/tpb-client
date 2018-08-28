package com.adik993.tpbclient;

import com.adik993.tpbclient.exceptions.ClientBuildException;
import com.adik993.tpbclient.model.Category;
import com.adik993.tpbclient.model.OrderBy;
import com.adik993.tpbclient.model.TpbResult;
import com.adik993.tpbclient.proxy.model.Proxy;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;

@Slf4j
public class TpbClient {
    public static final String PARAM_QUERY = "q";
    public static final String PARAM_CATEGORY = "category";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_ORDER_BY = "orderby";
    public static final String DEFAULT_HOST = "thepiratebay.org";

    private URI host;

    public static TpbClient fromProxy(Proxy proxy) {
        return withHost(proxy.getDomain(), proxy.isSecure());
    }

    public static TpbClient withHost(String host, boolean secure) throws ClientBuildException {
        TpbClient client = new TpbClient();
        try {
            client.host = new URIBuilder()
                    .setScheme(secure ? "https" : "http")
                    .setHost(host)
                    .build();
        } catch (URISyntaxException e) {
            throw new ClientBuildException("Unable to build client. Host malformed", e);
        }
        return client;
    }

    public static TpbClient withDefaultHost() {
        return withHost(DEFAULT_HOST, true);
    }

    public Single<TpbResult> search(String query, Category category, Integer page, OrderBy orderBy) {
        String url = buildUrl(toParams(
                PARAM_QUERY, query,
                PARAM_CATEGORY, category,
                PARAM_PAGE, page,
                PARAM_ORDER_BY, orderBy)).toString();
        return fetchDocument(url)
                .doOnError(throwable -> {
                    if (throwable instanceof HttpStatusException)
                        log.debug("error searching for torrents at {}. Response status is {}", url, ((HttpStatusException) throwable).getStatusCode());
                })
                .doOnSubscribe(disposable -> log.debug("Searching for torrents at {}", url))
                .map(document -> new TpbResult(document, now()));
    }

    private Single<Document> fetchDocument(String url) {
        Connection connect = Jsoup.connect(url);
        connect.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connect.cookie("lw", "s");
        return Single.fromCallable(connect::get);
    }

    private URL buildUrl(List<NameValuePair> params) {
        try {
            URIBuilder builder = new URIBuilder(host);
            return builder
                    .setPath("/s/")
                    .addParameters(params).build().toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new IllegalStateException("Url built by this client is malformed!. That's a bug");
        }
    }

    private static List<NameValuePair> toParams(Object... objects) {
        if (objects.length % 2 != 0) throw new IllegalStateException("Number of parameters must be even");
        List<NameValuePair> ret = new ArrayList<>(objects.length / 2);
        for (int i = 0; i < objects.length; i += 2) {
            if (objects[i + 1] == null) continue;
            ret.add(new BasicNameValuePair(objects[i].toString(), objects[i + 1].toString()));
        }
        return ret;
    }

    public URI getHost() {
        return host;
    }
}
