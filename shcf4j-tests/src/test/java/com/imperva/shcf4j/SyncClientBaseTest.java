package com.imperva.shcf4j;

import com.imperva.shcf4j.client.SyncHttpClient;
import com.imperva.shcf4j.client.protocol.ClientContext;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface SyncClientBaseTest extends AbstractBasicTest {


    @Override
    default <T> T execute(HttpHost host, HttpRequest request, Function<HttpResponse, T> callback, ClientContext ctx, Consumer<HttpClientCommonBuilder<?>> builderCustomizer) {
        SyncHttpClientBuilder clientBuilder = HttpClientBuilderFactory.getHttpClientBuilder();
        builderCustomizer.accept(clientBuilder);
        try (SyncHttpClient httpClient = clientBuilder.build()) {
            return httpClient.execute(host, request, callback, ctx);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    @Override
    default void execute(List<Request<?>> requests, Consumer<HttpClientCommonBuilder<?>> builderCustomizer) {
        SyncHttpClientBuilder clientBuilder = HttpClientBuilderFactory.getHttpClientBuilder();
        builderCustomizer.accept(clientBuilder);
        try (SyncHttpClient httpClient = clientBuilder.build()) {
            for (Request<?> request : requests) {
                httpClient.execute(request.getHost(), request.getRequest(), request.getCallback(), request.getCtx());
            }
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
}
