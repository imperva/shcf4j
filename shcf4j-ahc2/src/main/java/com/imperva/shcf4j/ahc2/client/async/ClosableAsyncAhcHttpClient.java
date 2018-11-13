package com.imperva.shcf4j.ahc2.client.async;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.client.protocol.ClientContext;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Response;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;


/**
 * <b>AsyncNettyHttpClient</b>
 *
 * <p>
 * An Async HTTP client implementation {@link AsyncHttpClient} that backed by {@link org.asynchttpclient.AsyncHttpClient}
 * </p>
 *
 * @author maxim.kirilov
 */
class ClosableAsyncAhcHttpClient implements AsyncHttpClient {

    private final org.asynchttpclient.AsyncHttpClient asyncHttpClient;


    ClosableAsyncAhcHttpClient(org.asynchttpclient.AsyncHttpClient asyncHttpClient) {
        Objects.requireNonNull(asyncHttpClient, "asyncHttpClient");
        this.asyncHttpClient = asyncHttpClient;
    }


    @Override
    public CompletableFuture<HttpResponse> execute(HttpHost target, HttpRequest request) {
        return execute(target, request, null);
    }

    @Override
    public CompletableFuture<HttpResponse> execute(HttpHost target, HttpRequest request, ClientContext ctx) {


        String fullUri = target.getSchemeName() + "://" + target.getHostname() + ":" + target.getPort() + request.getUri().toASCIIString();
        RequestBuilder builder = new RequestBuilder(request.getHttpMethod().name());
        builder.setUri(org.asynchttpclient.uri.Uri.create(fullUri));

        if (ctx != null) {
            if (ctx.getRequestConfig() != null) {
                RequestConfig rc = ctx.getRequestConfig();
                builder.setReadTimeout(rc.getConnectTimeoutMilliseconds())
                        .setRequestTimeout(rc.getSocketTimeoutMilliseconds());
            }
        }

        addBody(request, builder);
        copyHeaders(request, builder);

        return asyncHttpClient
                .executeRequest(builder.build())
                .toCompletableFuture()
                .thenApply(ClosableAsyncAhcHttpClient::convert);
    }

    @Override
    public void close() throws IOException {
        asyncHttpClient.close();
    }


    private static HttpResponse convert(Response response) {
        return new HttpResponseAdapter(response);
    }


    private static void addBody(HttpRequest request, RequestBuilder builder) {
        builder.setBody(request.getByteData());
        builder.setBody(request.getStringData());
        builder.setBody(request.getFilePath() != null ? request.getFilePath().toFile() : null);
        builder.setBody(request.getInputStreamData());
    }


    private static void copyHeaders(HttpRequest request, RequestBuilder builder) {
        for (Header h : request.getAllHeaders()) {
            builder.addHeader(h.getName(), h.getValue());
        }
    }

}
