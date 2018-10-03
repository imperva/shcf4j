package com.imperva.shcf4j.httpcomponents.client.impl.nio.client;

import org.apache.http.concurrent.FutureCallback;

import java.util.Objects;

/**
 * Created by maxim.kirilov on 4/19/17.
 */
abstract class BaseFutureCallbackAdapter<R,T> implements FutureCallback<T> {

    protected final com.imperva.shcf4j.concurrent.FutureCallback<R> callback;

    public BaseFutureCallbackAdapter(com.imperva.shcf4j.concurrent.FutureCallback<R> callback) {
        Objects.requireNonNull(callback, "callback");
        this.callback = callback;
    }

    @Override
    public void failed(Exception ex) {
        this.callback.failed(ex);
    }

    @Override
    public void cancelled() {
        this.callback.cancelled();
    }


}
