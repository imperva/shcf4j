package com.imperva.shcf4j.httpcomponents.client;

import com.imperva.shcf4j.client.HttpClient;
import com.imperva.shcf4j.httpcomponents.client.impl.client.HttpClients;

/**
 * <b>DefaultClientTest</b>
 */
public class DefaultClientTest extends HttpMethodsTest {

    private final HttpClient client = HttpClients.createDefault();

    @Override
    HttpClient getHttpClient() {
        return client;
    }
}
