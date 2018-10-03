package com.imperva.shcf4j.httpcomponents.client.impl.nio.client;

import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.client.HttpAsyncClient;
import com.imperva.shcf4j.client.protocol.ClientContext;
import com.imperva.shcf4j.concurrent.FutureCallback;
import com.imperva.shcf4j.httpcomponents.client.impl.ConversionUtils;
import com.imperva.shcf4j.nio.protocol.ZeroCopyToFileResponseConsumer;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.nio.client.methods.HttpAsyncMethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Future;
import java.util.function.Function;

/**
 * <b>InternalHttpAsyncClient</b>
 *
 * @author <font color="blue">Maxim Kirilov</font>
 */
class InternalClosableHttpAsyncClient implements HttpAsyncClient {

    private final CloseableHttpAsyncClient asyncClient;

    InternalClosableHttpAsyncClient(CloseableHttpAsyncClient asyncClient) {
        this.asyncClient = asyncClient;
        this.asyncClient.start();
    }


	@Override
	public Future<File> execute(HttpHost target, HttpRequest request,
                                ZeroCopyToFileResponseConsumer responseConsumer, ClientContext ctx,
                                FutureCallback<File> callback) throws FileNotFoundException {

		return this.asyncClient.execute(
				HttpAsyncMethods.create(ConversionUtils.convert(target),
                        ConversionUtils.convert(request)),
				new InternalZeroCopyConsumer(responseConsumer.getPath().toFile()),
                ConversionUtils.convert(ctx),
				new FutureCallbackAdapter<>(callback, Function.identity()));
	}


    @Override
    public Future<File> execute(HttpHost target, HttpRequest request, ZeroCopyToFileResponseConsumer responseConsumer, FutureCallback<File> callback) throws FileNotFoundException {
        return execute(target, request, responseConsumer, ClientContext.builder().build(), callback);
    }

    @Override
    public Future<HttpResponse> execute(HttpHost target, HttpRequest request, FutureCallback<HttpResponse> callback) {
        return new FutureAdapter(
                this.asyncClient.execute(
                        ConversionUtils.convert(target),
                        ConversionUtils.convert(request),
                        new FutureCallbackAdapter<>(callback, ConversionUtils::convert)));
    }

    @Override
    public Future<HttpResponse> execute(HttpHost target, HttpRequest request, ClientContext ctx, FutureCallback<HttpResponse> callback) {
        return new FutureAdapter(
                this.asyncClient.execute(
                        ConversionUtils.convert(target),
                        ConversionUtils.convert(request),
                        ConversionUtils.convert(ctx),
                        new FutureCallbackAdapter<>(callback, ConversionUtils::convert)));
    }

    @Override
    public void close() throws IOException {
        this.asyncClient.close();
    }
}
