package com.imperva.shcf4j;

import com.imperva.shcf4j.client.SyncHttpClient;
import org.junit.Assert;
import org.junit.Test;

public class SyncSyncHttpClientBuilderFactoryTest {


    @Test
    public void NOPHttpClientBuilderTest() {

        SyncHttpClientBuilder instance1 = HttpClientBuilderFactory.getHttpClientBuilder();
        SyncHttpClientBuilder instance2 = HttpClientBuilderFactory.getHttpClientBuilder();
        Assert.assertEquals("NOP implementation returns different instances", instance1, instance2);

    }

    @Test
    public void NOPHttpClientTest() throws Exception {
        SyncHttpClient syncHttpClient = HttpClientBuilderFactory.getHttpClientBuilder().build();

        syncHttpClient.execute(
                HttpHost.builder().hostname("localhost").port(8090).build(),
                HttpRequestBuilder.GET().uri("/hello").build() );
    }
}
