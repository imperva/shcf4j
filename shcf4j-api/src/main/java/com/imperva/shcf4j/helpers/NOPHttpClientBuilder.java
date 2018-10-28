package com.imperva.shcf4j.helpers;

import com.imperva.shcf4j.HttpClientBuilder;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.HttpClient;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.config.SocketConfig;
import com.imperva.shcf4j.conn.ssl.SSLSessionStrategy;


/**
 * <b>NOPHttpClientBuilder</b>
 *
 * <p>
 * A trivial implementation of {@link HttpClientBuilder}
 * </p>
 *
 * @author maxim.kirilov
 */
class NOPHttpClientBuilder implements HttpClientBuilder {

    static final HttpClientBuilder INSTANCE = new NOPHttpClientBuilder();

    @Override
    public HttpClientBuilder setSSLSessionStrategy(SSLSessionStrategy strategy) {
        return this;
    }

    @Override
    public HttpClientBuilder setDefaultSocketConfig(SocketConfig socketConfig) {
        return this;
    }

    @Override
    public HttpClientBuilder setDefaultRequestConfig(RequestConfig config) {
        return this;
    }

    @Override
    public HttpClientBuilder setProxy(HttpHost proxy) {
        return this;
    }

    @Override
    public HttpClientBuilder setDefaultCredentialsProvider(CredentialsProvider cp) {
        return this;
    }

    @Override
    public HttpClient build() {
        return NOPHttpClient.INSTANCE;
    }
}
