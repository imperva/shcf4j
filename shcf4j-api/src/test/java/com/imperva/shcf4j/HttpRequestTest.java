package com.imperva.shcf4j;

import org.junit.Assert;
import org.junit.Test;

public class HttpRequestTest {



    @Test
    public void addDifferentHeadersTest() {
        HttpRequest request =
                HttpRequest
                        .builder()
                        .getRequest()
                        .uri("https://google.com")
                        .header(Header.builder().name("h1").value("v1").build())
                        .header(Header.builder().name("h2").value("v2").build())
                        .build();

        Assert.assertEquals("wrong number of headers", 2, request.getAllHeaders().size());
    }

    @Test
    public void addIdenticalHeadersTest() {
        HttpRequest request =
                HttpRequest
                        .builder()
                        .getRequest()
                        .uri("https://google.com")
                        .header(Header.builder().name("h1").value("v1").build())
                        .header(Header.builder().name("h1").value("v2").build())
                        .build();

        Assert.assertEquals("wrong number of headers", 2, request.getAllHeaders().size());
    }



    @Test
    public void setMissingHeaderTest(){
        HttpRequest request =
                HttpRequest
                        .builder()
                        .getRequest()
                        .uri("http://localhost:8080")
                        .header(Header.builder().name("h").value("v").build())
                        .build();


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
