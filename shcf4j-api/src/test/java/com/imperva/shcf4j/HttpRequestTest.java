package com.imperva.shcf4j;

import org.junit.Assert;
import org.junit.Test;

public class HttpRequestTest {




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
