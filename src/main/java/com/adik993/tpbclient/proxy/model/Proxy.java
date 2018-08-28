package com.adik993.tpbclient.proxy.model;

import lombok.Value;

@Value
public class Proxy {
    private String domain;
    private float speed;
    private boolean secure;
    private String country;
    private boolean probed;
}
