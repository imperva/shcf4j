package com.imperva.shcf4j.java.http.client;

import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.client.protocol.ClientContext;

import java.io.InputStream;
import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;

public class ClosableAsyncHttpClient implements AsyncHttpClient {


    private final HttpClient httpClient;

    ClosableAsyncHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public CompletableFuture<HttpResponse> execute(HttpHost target, HttpRequest request) {
        return execute(target, request, null);
    }

    @Override
    public CompletableFuture<HttpResponse> execute(HttpHost target, HttpRequest request, ClientContext ctx) {

        java.net.http.HttpRequest httpRequest = JavaHttpRequestFactory.createJavaHttpRequest(target, request, ctx);
        CompletableFuture<java.net.http.HttpResponse<InputStream>> cf =
                this.httpClient.sendAsync(httpRequest, java.net.http.HttpResponse.BodyHandlers.ofInputStream());

        return cf.thenApplyAsync(HttpResponseImpl::new);
    }

}
