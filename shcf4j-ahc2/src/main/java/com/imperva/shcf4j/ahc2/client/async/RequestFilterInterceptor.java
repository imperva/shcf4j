package com.imperva.shcf4j.ahc2.client.async;

import com.imperva.shcf4j.MutableHttpRequest;
import org.asynchttpclient.filter.FilterContext;
import org.asynchttpclient.filter.RequestFilter;

import java.util.function.Consumer;

/**
 * <b>RequestFilterInterceptor</b>
 *
 * <p>
 *     Uses as a bridge between {@link RequestFilter} to {@link Consumer<MutableHttpRequest>}
 * </p>
 *
 */
public class RequestFilterInterceptor implements RequestFilter {

    private final Consumer<MutableHttpRequest> interceptor;

    public RequestFilterInterceptor(Consumer<MutableHttpRequest> interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public <T> FilterContext<T> filter(FilterContext<T> ctx) {
        interceptor.accept(new AhcHttpRequestAdapter(ctx.getRequest()));
        return ctx;
    }
}
