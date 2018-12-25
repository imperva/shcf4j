package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.ProcessingException;
import com.imperva.shcf4j.client.SyncHttpClient;
import com.imperva.shcf4j.client.protocol.ClientContext;

import java.io.IOException;
import java.util.function.Function;

/**
 * <b>SyncHttpClientBase</b>
 * <p>
 * Base implementation of {@link SyncHttpClient}
 * </p>
 *
 * @author maxim.kirilov
 */
public abstract class ClosableSyncHttpClientBase implements SyncHttpClient {

    private final org.apache.http.impl.client.CloseableHttpClient httpClient;

    protected ClosableSyncHttpClientBase(org.apache.http.impl.client.CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }


    @Override
    public void close() throws IOException {
        httpClient.close();
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
        try {
            return httpClient.execute(
                    ConversionUtils.convert(target),
                    ConversionUtils.convert(request),
                    response -> handler.apply(ConversionUtils.convert(response)),
                    ConversionUtils.convert(ctx)
            );
        } catch (IOException ioException) {
            throw new ProcessingException(ioException);
        }
    }
}
