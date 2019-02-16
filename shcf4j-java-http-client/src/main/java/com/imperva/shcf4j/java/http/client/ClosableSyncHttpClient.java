package com.imperva.shcf4j.java.http.client;

import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.ProcessingException;
import com.imperva.shcf4j.client.SyncHttpClient;
import com.imperva.shcf4j.client.protocol.ClientContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.util.function.Function;

class ClosableSyncHttpClient implements SyncHttpClient {

    private final HttpClient httpClient;

    ClosableSyncHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public HttpResponse execute(HttpHost target, HttpRequest request) {
        return execute(target, request, (ClientContext) null);
    }

    @Override
    public HttpResponse execute(HttpHost target, HttpRequest request, ClientContext ctx) {
        return execute(
                target,
                request,
                Function.identity(),
                ctx
        );
    }

    @Override
    public <T> T execute(HttpHost target, HttpRequest request, Function<HttpResponse, ? extends T> handler) {
        return execute(target, request, handler, null);
    }

    @Override
    public <T> T execute(HttpHost target, HttpRequest request, Function<HttpResponse, ? extends T> handler, ClientContext ctx) {

        java.net.http.HttpRequest httpRequest = JavaHttpRequestFactory.createJavaHttpRequest(target, request, ctx);

        try {
            java.net.http.HttpResponse<InputStream> response =
                    this.httpClient.send(httpRequest, java.net.http.HttpResponse.BodyHandlers.ofInputStream());

            return handler.apply(new HttpResponseImpl(response));

        } catch (IOException | InterruptedException e){
            throw new ProcessingException(e);
        }

    }


}
