package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.HttpClientBuilderFactory;
import com.imperva.shcf4j.HttpClientCommonBuilder;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.SyncHttpClientBuilder;
import com.imperva.shcf4j.client.SyncHttpClient;
import com.imperva.shcf4j.client.protocol.ClientContext;
import com.imperva.shcf4j.config.RequestConfigTest;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

public class SyncHttpComponentsClientRequestConfigTest extends RequestConfigTest {


    @Override
    protected <T> T execute(HttpHost host, HttpRequest request, Function<HttpResponse, T> callback, ClientContext ctx, Consumer<HttpClientCommonBuilder<?>> builderCustomizer) throws IOException {
        SyncHttpClientBuilder clientBuilder = HttpClientBuilderFactory.getHttpClientBuilder();
        builderCustomizer.accept(clientBuilder);
        try (SyncHttpClient httpClient = clientBuilder.build()) {
            return httpClient.execute(host, request, callback, ctx);
        }
    }
}
