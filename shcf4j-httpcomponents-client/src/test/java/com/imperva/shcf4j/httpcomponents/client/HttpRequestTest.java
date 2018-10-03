package com.imperva.shcf4j.httpcomponents.client;

import com.imperva.shcf4j.HttpRequest;
import org.junit.Assert;
import org.junit.Test;

/**
 * <b>HttpRequestTest</b>
 * <p/>
 * <p/>
 * User: Maxim.Kirilov
 * Date: 04/05/2014
 */
public class HttpRequestTest {

    @Test
    public void addHeadersTest() {
        String uri = "https://google.com";
        HttpRequest request = HttpRequest.createGetRequest(uri);
        request
                .addHeader("h1", "v1")
                .addHeader("h2", "v2");

        Assert.assertEquals("wrong number of headers", 2, request.getAllHeaders().size());
    }

    @Test
    public void setHeadersTest() {
        String uri = "https://google.com";
        HttpRequest request = HttpRequest.createGetRequest(uri);
        request
                .addHeader("h1", "v1")
                .addHeader("h2", "v2");

        Assert.assertEquals("wrong number of headers", 2, request.getAllHeaders().size());
        Assert.assertEquals("", "v1", request.getHeaders("h1").get(0).getValue());

        request.setHeader("h1", "v2");
        Assert.assertEquals("wrong number of headers", 2, request.getAllHeaders().size());
        Assert.assertEquals("", "v2", request.getHeaders("h1").get(0).getValue());
    }

}
