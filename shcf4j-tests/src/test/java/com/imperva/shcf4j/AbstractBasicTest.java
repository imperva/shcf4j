package com.imperva.shcf4j;

import com.imperva.shcf4j.client.protocol.ClientContext;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface AbstractBasicTest {

    default HttpResponse execute(HttpHost host, HttpRequest request) {
        return execute(host, request, (ClientContext) null);
    }


    default HttpResponse execute(HttpHost host, HttpRequest request, ClientContext ctx) {
        return execute(host, request, Function.identity(), ctx);
    }


   default  <T> T execute(HttpHost host, HttpRequest request, Function<HttpResponse, T> callback) {
        return execute(host, request, callback, null);
    }

    default  <T> T execute(HttpHost host, HttpRequest request, Function<HttpResponse, T> callback, ClientContext ctx) {
        return execute(host, request, callback, ctx, b -> {
        });
    }

    <T> T execute(HttpHost host, HttpRequest request, Function<HttpResponse, T> callback, ClientContext ctx, Consumer<HttpClientCommonBuilder<?>> builderCustomizer);

    /**
     * Enable multiple requests execution under the same constructed HTTP client.
     *
     * @param requests
     * @param builderCustomizer
     */
    void execute(List<Request<?>> requests, Consumer<HttpClientCommonBuilder<?>> builderCustomizer);
}
