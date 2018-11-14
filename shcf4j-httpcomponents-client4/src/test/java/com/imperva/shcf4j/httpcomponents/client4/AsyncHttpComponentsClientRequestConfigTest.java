package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.HttpClientBuilderFactory;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.client.protocol.ClientContext;
import com.imperva.shcf4j.test.RequestConfigTest;

import java.io.IOException;
import java.util.function.Function;

public class AsyncHttpComponentsClientRequestConfigTest extends RequestConfigTest {


    @Override
    protected <T> T execute(HttpHost host, HttpRequest request, Function<HttpResponse, T> callback, ClientContext ctx) throws IOException {
        try (AsyncHttpClient httpClient = HttpClientBuilderFactory.getHttpAsyncClientBuilder().build()) {
            return httpClient.execute(host, request, ctx).thenApply(callback).join();
        }
    }
}
