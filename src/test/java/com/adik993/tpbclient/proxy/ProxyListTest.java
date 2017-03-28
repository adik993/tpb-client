package com.adik993.tpbclient.proxy;

import com.adik993.tpbclient.proxy.model.Proxy;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by adrian on 28/03/17.
 */
public class ProxyListTest {

    private ProxyList underTest;
    private static final List<Proxy> proxies = Arrays.asList(
            Proxy.builder().domain("aaa.com").build(),
            Proxy.builder().domain("bbb.com").build());

    @Before
    public void setUp() {
        underTest = new ProxyList() {
            List<Proxy> fetchProxies() {
                return proxies;
            }
        };
    }

    @Test
    public void testInit() throws IOException {
        assertNull(underTest.getProxyListIfPresent());
        underTest.init();
        assertNotNull(underTest.getProxyListIfPresent());
    }

    @Test
    public void testGetProxyListAutoFetch() throws IOException {
        assertEquals(proxies, underTest.getProxyList());
    }

    @Test(expected = IOException.class)
    public void errorInInit() throws IOException {
        underTest = faultyInstance();
        underTest.init();
    }

    @Test(expected = IOException.class)
    public void errorInGetProxyList() throws IOException {
        underTest = faultyInstance();
        underTest.getProxyList();
    }

    @Test
    public void parseResponse() throws IOException {
        underTest.parseResponse(getClass().getClassLoader().getResourceAsStream("proxy-list.json"));
    }

    @Test(expected = IOException.class)
    public void errorParsingResponse() throws IOException {
        underTest.parseResponse(new ByteArrayInputStream("aa".getBytes()));
    }

    @Test
    public void testFetchData() throws IOException {
        HttpResponse mockResponse = mock(HttpResponse.class);
        when(mockResponse.getEntity()).thenReturn(mock(HttpEntity.class));
        HttpClient mockHttpClient = mock(HttpClient.class);
        when(mockHttpClient.execute(any())).thenReturn(mockResponse);
        underTest = spy(new ProxyList(mockHttpClient));
        doReturn(proxies).when(underTest).parseResponse(any());
        underTest.fetchProxies();
        verify(mockHttpClient, times(1)).execute(any(HttpGet.class));
    }

    private ProxyList faultyInstance() {
        return new ProxyList() {
            @Override
            List<Proxy> fetchProxies() throws IOException {
                throw new IOException();
            }
        };
    }

}