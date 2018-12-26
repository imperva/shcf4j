package com.imperva.shcf4j.httpcomponents.client4.proxy;

import com.imperva.shcf4j.AsyncHttpClientBuilder;
import com.imperva.shcf4j.HttpClientBuilderFactory;
import com.imperva.shcf4j.HttpClientCommonBuilder;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.client.protocol.ClientContext;
import com.imperva.shcf4j.proxy.ProxyTest;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

public class AsyncHttpComponentsProxyTest extends ProxyTest {


    @Override
    protected <T> T execute(HttpHost host, HttpRequest request, Function<HttpResponse, T> callback, ClientContext ctx, Consumer<HttpClientCommonBuilder<?>> builderCustomizer) {
        AsyncHttpClientBuilder clientBuilder = HttpClientBuilderFactory.getHttpAsyncClientBuilder();
        builderCustomizer.accept(clientBuilder);
        try (AsyncHttpClient httpClient = clientBuilder.build()) {
            return httpClient.execute(host, request, ctx).thenApply(callback).join();
        } catch (IOException ioException){
            throw new RuntimeException(ioException);
        }
    }
}
