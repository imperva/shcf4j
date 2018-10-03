package com.imperva.shcf4j.httpcomponents.client.impl.nio.client;

import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.httpcomponents.client.impl.ConversionUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * <b>FutureAdapter</b>
 *
 * @author <font color="blue">Maxim Kirilov</font>
 */
public class FutureAdapter implements Future<HttpResponse> {

    private final Future<org.apache.http.HttpResponse> future;

    FutureAdapter(Future<org.apache.http.HttpResponse> future) {
        this.future = future;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return future.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return future.isCancelled();
    }

    @Override
    public boolean isDone() {
        return future.isDone();
    }

    @Override
    public HttpResponse get() throws InterruptedException, ExecutionException {
        return ConversionUtils.convert(future.get());
    }

    @Override
    public HttpResponse get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return ConversionUtils.convert(future.get(timeout, unit));
    }
}
