package com.imperva.shcf4j;

import org.junit.Assert;
import org.junit.Test;

/**
 * <b>HttpHostTest</b>
 * <p/>
 * User: Maxim.Kirilov
 * Date: 04/05/2014
 */
public class HttpHostTest {


    @Test
    public void legalCreationTest() {
        String scheme = "scheme";
        String host = "host";
        int port = 89;

        HttpHost httpHost = HttpHost.builder().schemeName(scheme).hostname(host).port(port).build();
        Assert.assertEquals("wrong scheme", scheme, httpHost.getSchemeName());
        Assert.assertEquals("wrong host", host, httpHost.getHostname());
        Assert.assertEquals("wrong port", port, httpHost.getPort());
    }

    @Test
    public void defaultSchemeTest() {
        String host = "host";
        int port = 89;

        HttpHost httpHost = HttpHost.builder().hostname(host).port(port).build();
        Assert.assertEquals("wrong scheme", HttpHost.DEFAULT_SCHEME_NAME, httpHost.getSchemeName());
        Assert.assertEquals("wrong host", host, httpHost.getHostname());
        Assert.assertEquals("wrong port", port, httpHost.getPort());
    }
}
