package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.HttpClientBuilderFactory;
import com.imperva.shcf4j.client.HttpClient;
import com.imperva.shcf4j.httpcomponents.client4.impl.client.HttpClients;

/**
 * <b>DefaultClientTest</b>
 */
public class DefaultClientTest extends HttpMethodsTest {

    private final HttpClient client = HttpClientBuilderFactory.getHttpClientBuilder().build();

    @Override
    HttpClient getHttpClient() {
        return client;
    }
}
