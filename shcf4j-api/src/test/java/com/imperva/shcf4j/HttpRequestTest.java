package com.imperva.shcf4j;

import org.junit.Assert;
import org.junit.Test;

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



    @Test
    public void setMissingHeaderTest(){
        HttpRequest request = HttpRequest.createGetRequest("http://localhost:8080");
        request.setHeader("h", "v");
        boolean exists = request
                .getHeaders("h")
                .stream()
                .filter(h -> "v".equals(h.getValue()))
                .findFirst()
                .map(x -> true)
                .orElse(false);

        Assert.assertTrue("Failed to set missing header on HTTP request", exists);
    }
}
