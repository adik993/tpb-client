package com.adik993.tpbclient.proxy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by adrian on 28/03/17.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Proxy {
    private String domain;
    private float speed;
    private boolean secure;
    private String country;
    private boolean probed;
}
