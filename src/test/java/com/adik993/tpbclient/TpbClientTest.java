package com.adik993.tpbclient;

import com.adik993.tpbclient.exceptions.ClientBuildException;
import com.adik993.tpbclient.exceptions.ParseException;
import com.adik993.tpbclient.model.Category;
import com.adik993.tpbclient.model.OrderBy;
import com.adik993.tpbclient.model.PageInfo;
import com.adik993.tpbclient.model.TpbResult;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

/**
 * Created by Adrian on 2016-07-10.
 */
public class TpbClientTest {

    @Test
    public void testWithHost() throws Exception {
        assertEquals("https://cos.org", TpbClient.withHost("cos.org").getHost().toString());
    }

    @Test(expected = ClientBuildException.class)
    public void testMalformedHost() {
        TpbClient.withHost("asd!##");
    }

    @Test
    public void testWithDefaultHost() throws Exception {
        assertEquals("https://" + TpbClient.DEFAULT_HOST, TpbClient.withDefaultHost().getHost().toString());
    }

    @Test
    public void testToParams() {
        List<NameValuePair> list = TpbClient.toParams("cos", Category.All, "cos2", null, "cos3", OrderBy.DateAsc);
        assertEquals(2, list.size());
        assertEquals(new BasicNameValuePair("cos", Category.All.toString()), list.get(0));
        assertEquals(new BasicNameValuePair("cos3", OrderBy.DateAsc.toString()), list.get(1));
    }

    @Test
    public void testBuildUrl() {
        TpbClient client = TpbClient.withDefaultHost();
        URL url = client.buildUrl(TpbClient.toParams("cos", "0", "cos2", "1"));
        assertEquals("https://" + TpbClient.DEFAULT_HOST + "/s?cos=0&cos2=1", url.toString());
    }

    @Test
    public void testGet() throws IOException, ParseException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("result.html");
        TpbClient tpbClient = spy(TpbClient.withDefaultHost());
        String url = "https://thepiratebay.org/s/?q=Banshee&category=0&page=0&orderby=99";
        Document doc = Jsoup.parse(stream, StandardCharsets.UTF_8.name(), url);
        doReturn(doc).when(tpbClient).fetchDocument(url);
        TpbResult result = tpbClient.get(url);
        assertEquals(new PageInfo(30, 666), result.getPageInfo());
    }
}