package com.imperva.shcf4j.netty4.client.async;

import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.client.protocol.ClientContext;
import com.imperva.shcf4j.concurrent.FutureCallback;
import com.imperva.shcf4j.nio.protocol.ZeroCopyToFileResponseConsumer;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Future;

class AsyncNettyHttpClient implements AsyncHttpClient {

    private final org.asynchttpclient.AsyncHttpClient asyncHttpClient;


    AsyncNettyHttpClient(org.asynchttpclient.AsyncHttpClient asyncHttpClient) {
        Objects.requireNonNull(asyncHttpClient, "asyncHttpClient");
        this.asyncHttpClient = asyncHttpClient;
    }


    @Override
    public Future<File> execute(HttpHost target, HttpRequest request, ZeroCopyToFileResponseConsumer responseConsumer, ClientContext ctx, FutureCallback<File> callback) throws FileNotFoundException {
        return null;
    }

    @Override
    public Future<File> execute(HttpHost target, HttpRequest request, ZeroCopyToFileResponseConsumer responseConsumer, FutureCallback<File> callback) throws FileNotFoundException {
        return null;
    }

    @Override
    public Future<HttpResponse> execute(HttpHost target, HttpRequest request, FutureCallback<HttpResponse> callback) {

        return null;
    }

    @Override
    public Future<HttpResponse> execute(HttpHost target, HttpRequest request, ClientContext ctx, FutureCallback<HttpResponse> callback) {


        String fullUri = target.getSchemeName() + "://" + target.getHostname() + ":" + target.getPort() + request.getUri().toASCIIString();
        RequestBuilder builder = new RequestBuilder(request.getMethod().name());
        builder.setUri(org.asynchttpclient.uri.Uri.create(fullUri));

        if (ctx != null){
            if (ctx.getRequestConfig() != null){
                RequestConfig rc = ctx.getRequestConfig();
                builder
                        .setReadTimeout(rc.getConnectTimeoutMilliseconds())
                        .setRequestTimeout(rc.getSocketTimeoutMilliseconds());
            }
        }


        return asyncHttpClient
                .executeRequest(builder.build())
                .toCompletableFuture()
                .thenApply(AsyncNettyHttpClient::convert);
    }

    @Override
    public void close() throws IOException {
        asyncHttpClient.close();
    }


    private static HttpResponse convert(Response response) {
        return new HttpResponseAdapter(response);
    }

}
