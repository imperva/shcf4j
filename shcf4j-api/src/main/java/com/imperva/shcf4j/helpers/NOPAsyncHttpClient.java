package com.imperva.shcf4j.helpers;

import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.client.protocol.ClientContext;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

class NOPAsyncHttpClient implements AsyncHttpClient {

    static final AsyncHttpClient INSTANCE = new NOPAsyncHttpClient();


    @Override
    public void close() throws IOException {

    }

    @Override
    public CompletableFuture<HttpResponse> execute(HttpHost target, HttpRequest request) {
        return CompletableFuture.completedFuture(NOPHttpResponse.INSTANCE);
    }

    @Override
    public CompletableFuture<HttpResponse> execute(HttpHost target, HttpRequest request, ClientContext ctx) {
        return CompletableFuture.completedFuture(NOPHttpResponse.INSTANCE);
    }
}
