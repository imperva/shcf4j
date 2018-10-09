package com.imperva.shcf4j.httpcomponents.client4.impl.client;

import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.client.HttpClient;
import com.imperva.shcf4j.client.protocol.ClientContext;
import com.imperva.shcf4j.httpcomponents.client4.impl.ConversionUtils;

import java.io.IOException;
import java.util.function.Function;

/**
 * <b>HttpClientBase</b>
 * <p>
 * Base implementation of {@link HttpClient}
 * </p>
 *
 * @author maxim.kirilov
 */
public abstract class HttpClientBase implements HttpClient {

    private final org.apache.http.impl.client.CloseableHttpClient httpClient;

    protected HttpClientBase(org.apache.http.impl.client.CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }


    @Override
    public void close() throws IOException {
        httpClient.close();
    }


    @Override
    public HttpResponse execute(HttpHost target, HttpRequest request) throws IOException {
        return execute(target, request, (ClientContext) null);
    }

    @Override
    public HttpResponse execute(HttpHost target, HttpRequest request, ClientContext ctx) throws IOException {
        return httpClient.execute(
                ConversionUtils.convert(target),
                ConversionUtils.convert(request),
                ConversionUtils::<HttpResponse>convert,
                ConversionUtils.convert(ctx)
        );
    }

    @Override
    public <T> T execute(HttpHost target, HttpRequest request, Function<HttpResponse, ? extends T> handler) throws IOException {
        return execute(target, request, handler, null);
    }

    @Override
    public <T> T execute(HttpHost target, HttpRequest request, Function<HttpResponse, ? extends T> handler, ClientContext ctx) throws IOException {
        return httpClient.execute(
                ConversionUtils.convert(target),
                ConversionUtils.convert(request),
                response -> handler.apply(ConversionUtils.convert(response)),
                ConversionUtils.convert(ctx)
        );
    }
}
