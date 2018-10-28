package com.imperva.shcf4j.helpers;

import com.imperva.shcf4j.HttpAsyncClientBuilder;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.HttpAsyncClient;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.conn.ssl.SSLSessionStrategy;
import com.imperva.shcf4j.nio.reactor.IOReactorConfig;

import java.util.concurrent.ThreadFactory;

class NOPHttpAsyncClientBuilder implements HttpAsyncClientBuilder {

    static final HttpAsyncClientBuilder INSTANCE = new NOPHttpAsyncClientBuilder();

    @Override
    public HttpAsyncClientBuilder setThreadFactory(ThreadFactory threadFactory) {
        return this;
    }

    @Override
    public HttpAsyncClientBuilder setDefaultSocketConfig(IOReactorConfig ioReactorConfig) {
        return this;
    }

    @Override
    public HttpAsyncClientBuilder setSSLSessionStrategy(SSLSessionStrategy strategy) {
        return this;
    }

    @Override
    public HttpAsyncClientBuilder setDefaultRequestConfig(RequestConfig config) {
        return this;
    }

    @Override
    public HttpAsyncClientBuilder setProxy(HttpHost proxy) {
        return this;
    }

    @Override
    public HttpAsyncClientBuilder setDefaultCredentialsProvider(CredentialsProvider cp) {
        return this;
    }

    @Override
    public HttpAsyncClient build() {
        return NOPHttpAsyncClient.INSTANCE;
    }
}
