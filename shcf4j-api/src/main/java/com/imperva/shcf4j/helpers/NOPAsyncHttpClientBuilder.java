package com.imperva.shcf4j.helpers;

import com.imperva.shcf4j.AsyncHttpClientBuilder;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.MutableHttpRequest;
import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.conn.ssl.SSLSessionStrategy;
import com.imperva.shcf4j.nio.reactor.IOReactorConfig;

import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;

class NOPAsyncHttpClientBuilder implements AsyncHttpClientBuilder {

    static final AsyncHttpClientBuilder INSTANCE = new NOPAsyncHttpClientBuilder();

    @Override
    public AsyncHttpClientBuilder setThreadFactory(ThreadFactory threadFactory) {
        return this;
    }

    @Override
    public AsyncHttpClientBuilder setDefaultSocketConfig(IOReactorConfig ioReactorConfig) {
        return this;
    }

    @Override
    public AsyncHttpClientBuilder setSSLSessionStrategy(SSLSessionStrategy strategy) {
        return this;
    }

    @Override
    public AsyncHttpClientBuilder setDefaultRequestConfig(RequestConfig config) {
        return this;
    }

    @Override
    public AsyncHttpClientBuilder setProxy(HttpHost proxy) {
        return this;
    }

    @Override
    public AsyncHttpClientBuilder setDefaultCredentialsProvider(CredentialsProvider cp) {
        return this;
    }

    @Override
    public AsyncHttpClientBuilder addRequestInterceptor(Consumer<MutableHttpRequest> request) {
        return this;
    }


    @Override
    public AsyncHttpClient build() {
        return NOPAsyncHttpClient.INSTANCE;
    }
}
