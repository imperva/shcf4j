package com.imperva.shcf4j.client;

import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.client.protocol.ClientContext;
import com.imperva.shcf4j.concurrent.FutureCallback;
import com.imperva.shcf4j.nio.protocol.ZeroCopyToFileResponseConsumer;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.Future;

/**
 * <b>HttpAsyncClient</b>
 *
 * <p>
 * This interface represents only the most basic contract for HTTP request execution.
 * It imposes no restrictions or particular details on the request execution process and leaves the specifics
 * of state management, authentication and redirect handling up to individual implementations.
 * </p>
 *
 * @author maxim.kirilov
 */
public interface HttpAsyncClient extends Closeable {


    Future<File> execute(HttpHost target, HttpRequest request, ZeroCopyToFileResponseConsumer responseConsumer, ClientContext ctx, FutureCallback<File> callback) throws FileNotFoundException;

    Future<File> execute(HttpHost target, HttpRequest request, ZeroCopyToFileResponseConsumer responseConsumer, FutureCallback<File> callback) throws FileNotFoundException;


    /**
     * Initiates asynchronous HTTP request execution using the given context.
     *
     * @param target   the target host for the request.
     * @param request  the request to execute
     * @param callback future callback.
     * @return future representing pending completion of the operation.
     */
    Future<HttpResponse> execute(HttpHost target, HttpRequest request, FutureCallback<HttpResponse> callback);

    /**
     * Initiates asynchronous HTTP request execution using the given context.
     *
     * @param target   the target host for the request.
     * @param request  the request to execute
     * @param ctx      client4 execution context
     * @param callback future callback.
     * @return future representing pending completion of the operation.
     */
    Future<HttpResponse> execute(HttpHost target, HttpRequest request, ClientContext ctx, FutureCallback<HttpResponse> callback);

}
