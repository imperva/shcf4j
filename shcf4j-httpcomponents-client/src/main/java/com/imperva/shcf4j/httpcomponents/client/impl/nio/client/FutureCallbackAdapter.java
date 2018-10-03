package com.imperva.shcf4j.httpcomponents.client.impl.nio.client;

import java.util.function.Function;

/**
 * Created by maxim.kirilov on 4/19/17.
 */
class FutureCallbackAdapter<R,T> extends BaseFutureCallbackAdapter<R, T> {

    private final Function<T,R> converter;

    public FutureCallbackAdapter(com.imperva.shcf4j.concurrent.FutureCallback<R> callback, Function<T,R> converter) {
        super(callback);
        this.converter = converter;
    }

    @Override
    public void completed(T result) {
        callback.completed(converter.apply(result));
    }


}
