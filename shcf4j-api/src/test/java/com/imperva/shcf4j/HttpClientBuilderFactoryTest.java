package com.imperva.shcf4j;

import com.imperva.shcf4j.client.HttpClient;
import org.junit.Assert;
import org.junit.Test;

public class HttpClientBuilderFactoryTest {


    @Test
    public void NOPHttpClientBuilderTest() {

        HttpClientBuilder instance1 = HttpClientBuilderFactory.getHttpClientBuilder();
        HttpClientBuilder instance2 = HttpClientBuilderFactory.getHttpClientBuilder();
        Assert.assertEquals("NOP implementation returns different instances", instance1, instance2);

    }

    @Test
    public void NOPHttpClientTest() throws Exception {
        HttpClient httpClient = HttpClientBuilderFactory.getHttpClientBuilder().build();

        httpClient.execute(
                HttpHost.builder().schemeName("https").hostname("github.com").port(443).build(),
                HttpRequest.createGetRequest("/imperva/shcf4j"));
    }
}
