package com.imperva.shcf4j;

import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.client.protocol.ClientContext;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface AsyncClientBaseTest extends AbstractBasicTest {

    @Override
    default <T> T execute(HttpHost host, HttpRequest request, Function<HttpResponse, T> callback, ClientContext ctx, Consumer<HttpClientCommonBuilder<?>> builderCustomizer) {
        AsyncHttpClientBuilder clientBuilder = HttpClientBuilderFactory.getHttpAsyncClientBuilder();
        builderCustomizer.accept(clientBuilder);
        try (AsyncHttpClient httpClient = clientBuilder.build()) {
            return httpClient.execute(host, request, ctx).thenApply(callback).join();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    @Override
    default void execute(List<Request<?>> requests, Consumer<HttpClientCommonBuilder<?>> builderCustomizer) {
        AsyncHttpClientBuilder clientBuilder = HttpClientBuilderFactory.getHttpAsyncClientBuilder();
        builderCustomizer.accept(clientBuilder);
        try (AsyncHttpClient httpClient = clientBuilder.build()) {
            for (Request<?> request : requests) {
                httpClient.execute(request.getHost(), request.getRequest(), request.getCtx()).thenApply(request.getCallback()).join();
            }
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
}
