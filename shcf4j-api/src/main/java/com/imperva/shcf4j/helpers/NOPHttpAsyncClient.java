package com.imperva.shcf4j.helpers;

import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.client.HttpAsyncClient;
import com.imperva.shcf4j.client.protocol.ClientContext;
import com.imperva.shcf4j.concurrent.FutureCallback;
import com.imperva.shcf4j.nio.protocol.ZeroCopyToFileResponseConsumer;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

class NOPHttpAsyncClient implements HttpAsyncClient {

    static final HttpAsyncClient INSTANCE = new NOPHttpAsyncClient();

    @Override
    public Future<File> execute(HttpHost target, HttpRequest request, ZeroCopyToFileResponseConsumer responseConsumer, ClientContext ctx, FutureCallback<File> callback) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public Future<File> execute(HttpHost target, HttpRequest request, ZeroCopyToFileResponseConsumer responseConsumer, FutureCallback<File> callback) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public Future<HttpResponse> execute(HttpHost target, HttpRequest request, FutureCallback<HttpResponse> callback) {
        return CompletableFuture.completedFuture(NOPHttpResponse.INSTANCE);
    }

    @Override
    public Future<HttpResponse> execute(HttpHost target, HttpRequest request, ClientContext ctx, FutureCallback<HttpResponse> callback) {
        return CompletableFuture.completedFuture(NOPHttpResponse.INSTANCE);
    }

    @Override
    public void close() throws IOException {

    }
}
