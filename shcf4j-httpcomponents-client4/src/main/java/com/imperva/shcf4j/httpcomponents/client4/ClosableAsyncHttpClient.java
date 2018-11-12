package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.client.protocol.ClientContext;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * <b>InternalHttpAsyncClient</b>
 *
 * @author <font color="blue">Maxim Kirilov</font>
 */
class ClosableAsyncHttpClient implements AsyncHttpClient {

    private final CloseableHttpAsyncClient asyncClient;

    ClosableAsyncHttpClient(CloseableHttpAsyncClient asyncClient) {
        this.asyncClient = asyncClient;
        this.asyncClient.start();
    }


    @Override
    public CompletableFuture<HttpResponse> execute(HttpHost target, HttpRequest request) {
        return execute(target, request, null);
    }

    @Override
    public CompletableFuture<HttpResponse> execute(HttpHost target, HttpRequest request, ClientContext ctx) {

        final CompletableFuture<HttpResponse> cf = new CompletableFuture<>();
        this.asyncClient.execute(
                ConversionUtils.convert(target),
                ConversionUtils.convert(request),
                ConversionUtils.convert(ctx),
                new FutureCallback<org.apache.http.HttpResponse>() {

                    @Override
                    public void cancelled() {
                        cf.cancel(false);
                    }

                    @Override
                    public void completed(org.apache.http.HttpResponse httpResponse) {
                        cf.complete(ConversionUtils.convert(httpResponse));
                    }

                    @Override
                    public void failed(Exception ex) {
                        cf.completeExceptionally(ex);
                    }
                });

        return cf;
    }

    @Override
    public void close() throws IOException {
        this.asyncClient.close();
    }
}
