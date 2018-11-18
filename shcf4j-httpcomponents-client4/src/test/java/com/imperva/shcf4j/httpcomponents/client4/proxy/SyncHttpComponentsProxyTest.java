package com.imperva.shcf4j.httpcomponents.client4.proxy;

import com.imperva.shcf4j.HttpClientBuilderFactory;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.client.SyncHttpClient;
import com.imperva.shcf4j.client.protocol.ClientContext;
import com.imperva.shcf4j.proxy.ProxyTest;

import java.io.IOException;
import java.util.function.Function;

public class SyncHttpComponentsProxyTest extends ProxyTest {

    @Override
    protected <T> T execute(HttpHost host, HttpRequest request, Function<HttpResponse, T> callback, ClientContext ctx) throws IOException {
        try (SyncHttpClient httpClient = HttpClientBuilderFactory.getHttpClientBuilder().build()) {
            return httpClient.execute(host, request, callback, ctx);
        }
    }
}
