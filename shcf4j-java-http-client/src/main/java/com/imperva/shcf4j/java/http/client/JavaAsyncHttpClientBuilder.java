package com.imperva.shcf4j.java.http.client;

import com.imperva.shcf4j.AsyncHttpClientBuilder;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.MutableHttpRequest;
import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.conn.ssl.SSLSessionStrategy;
import com.imperva.shcf4j.nio.reactor.IOReactorConfig;

import javax.net.ssl.SSLException;
import java.net.http.HttpClient;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;

class JavaAsyncHttpClientBuilder implements AsyncHttpClientBuilder {

    private final HttpClient.Builder builder = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1);


    @Override
    public AsyncHttpClientBuilder setThreadFactory(ThreadFactory threadFactory) {
        return this;
    }

    @Override
    public AsyncHttpClientBuilder setDefaultSocketConfig(IOReactorConfig ioReactorConfig) {
        return this;
    }

    @Override
    public AsyncHttpClient build() {
        return new ClosableAsyncHttpClient(this.builder.build());
    }

    @Override
    public AsyncHttpClientBuilder setSSLSessionStrategy(SSLSessionStrategy strategy) throws SSLException {
        HttpClientBuilderUtils.setSSLSessionStrategy(strategy, this.builder);
        return this;
    }

    @Override
    public AsyncHttpClientBuilder setDefaultRequestConfig(RequestConfig config) {
        HttpClientBuilderUtils.setDefaultRequestConfig(config, this.builder);
        return this;
    }

    @Override
    public AsyncHttpClientBuilder setProxy(HttpHost proxy) {
        HttpClientBuilderUtils.setProxy(proxy, this.builder);
        return this;
    }

    @Override
    public AsyncHttpClientBuilder setDefaultCredentialsProvider(CredentialsProvider cp) {
        HttpClientBuilderUtils.setDefaultCredentialsProvider(cp, this.builder);
        return this;
    }

    @Override
    public AsyncHttpClientBuilder addRequestInterceptor(Consumer<MutableHttpRequest> interceptor) {
        return null;
    }
}
