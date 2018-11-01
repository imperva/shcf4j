package com.imperva.shcf4j.helpers;

import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.client.SyncHttpClient;
import com.imperva.shcf4j.client.protocol.ClientContext;
import com.imperva.shcf4j.spi.SHC4JServiceProvider;

import java.util.function.Function;


/**
 * <b>NOPSyncHttpClient</b>
 *
 * <p>
 * An implementation that used as fallback for non found {@link SHC4JServiceProvider}
 * </p>
 *
 * @author maxim.kirilov
 */
class NOPSyncHttpClient implements SyncHttpClient {


    static final SyncHttpClient INSTANCE = new NOPSyncHttpClient();


    @Override
    public HttpResponse execute(HttpHost target, HttpRequest request) {
        return NOPHttpResponse.INSTANCE;
    }

    @Override
    public HttpResponse execute(HttpHost target, HttpRequest request, ClientContext ctx) {
        return NOPHttpResponse.INSTANCE;
    }

    @Override
    public <T> T execute(HttpHost target, HttpRequest request, Function<HttpResponse, ? extends T> handler) {
        return handler.apply(NOPHttpResponse.INSTANCE);
    }

    @Override
    public <T> T execute(HttpHost target, HttpRequest request, Function<HttpResponse, ? extends T> handler, ClientContext ctx) {
        return handler.apply(NOPHttpResponse.INSTANCE);
    }

    @Override
    public void close() {

    }
}
