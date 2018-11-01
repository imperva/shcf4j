package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.HttpClientBuilderFactory;
import com.imperva.shcf4j.client.SyncHttpClient;

/**
 * <b>DefaultClientTest</b>
 */
public class DefaultClientTest extends HttpMethodsTest {

    private final SyncHttpClient client = HttpClientBuilderFactory.getHttpClientBuilder().build();

    @Override
    SyncHttpClient getHttpClient() {
        return client;
    }
}
