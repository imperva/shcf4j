package com.imperva.shcf4j.helpers;

import com.imperva.shcf4j.SyncHttpClientBuilder;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.SyncHttpClient;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.config.SocketConfig;
import com.imperva.shcf4j.conn.ssl.SSLSessionStrategy;


/**
 * <b>NOPSyncHttpClientBuilder</b>
 *
 * <p>
 * A trivial implementation of {@link SyncHttpClientBuilder}
 * </p>
 *
 * @author maxim.kirilov
 */
class NOPSyncHttpClientBuilder implements SyncHttpClientBuilder {

    static final SyncHttpClientBuilder INSTANCE = new NOPSyncHttpClientBuilder();

    @Override
    public SyncHttpClientBuilder setSSLSessionStrategy(SSLSessionStrategy strategy) {
        return this;
    }

    @Override
    public SyncHttpClientBuilder setDefaultSocketConfig(SocketConfig socketConfig) {
        return this;
    }

    @Override
    public SyncHttpClientBuilder setDefaultRequestConfig(RequestConfig config) {
        return this;
    }

    @Override
    public SyncHttpClientBuilder setProxy(HttpHost proxy) {
        return this;
    }

    @Override
    public SyncHttpClientBuilder setDefaultCredentialsProvider(CredentialsProvider cp) {
        return this;
    }

    @Override
    public SyncHttpClient build() {
        return NOPSyncHttpClient.INSTANCE;
    }
}
