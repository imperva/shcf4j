package com.imperva.shcf4j;

import com.imperva.shcf4j.client.protocol.ClientContext;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractBasicTest {

    protected final HttpResponse execute(HttpHost host, HttpRequest request) throws IOException {
        return execute(host, request, (ClientContext) null);
    }


    protected final HttpResponse execute(HttpHost host, HttpRequest request, ClientContext ctx) throws IOException {
        return execute(host, request, Function.identity(), ctx);
    }


    protected final <T> T execute(HttpHost host, HttpRequest request, Function<HttpResponse, T> callback) throws IOException {
        return execute(host, request, callback, null);
    }

    protected final  <T> T execute(HttpHost host, HttpRequest request, Function<HttpResponse, T> callback, ClientContext ctx) throws IOException {
        return execute(host, request, callback, ctx, b -> {});
    }

    protected abstract <T> T execute(HttpHost host, HttpRequest request, Function<HttpResponse, T> callback, ClientContext ctx, Consumer<HttpClientCommonBuilder<?>> builderCustomizer) throws IOException;
}
