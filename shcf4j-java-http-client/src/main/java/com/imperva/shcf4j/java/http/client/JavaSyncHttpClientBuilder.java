package com.imperva.shcf4j.java.http.client;

import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.MutableHttpRequest;
import com.imperva.shcf4j.SyncHttpClientBuilder;
import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.SyncHttpClient;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.config.SocketConfig;
import com.imperva.shcf4j.conn.ssl.SSLSessionStrategy;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManager;
import java.net.CookieManager;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Objects;
import java.util.function.Consumer;

class JavaSyncHttpClientBuilder implements SyncHttpClientBuilder {

    private final HttpClient.Builder builder = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1);

    @Override
    public SyncHttpClientBuilder setDefaultSocketConfig(SocketConfig socketConfig) {
        return this;
    }

    @Override
    public SyncHttpClient build() {
        return new ClosableSyncHttpClient(builder.build());
    }

    @Override
    public SyncHttpClientBuilder setSSLSessionStrategy(SSLSessionStrategy strategy) throws SSLException {
        HttpClientBuilderUtils.setSSLSessionStrategy(strategy, this.builder);
        return this;
    }

    @Override
    public SyncHttpClientBuilder setDefaultRequestConfig(RequestConfig config) {
        HttpClientBuilderUtils.setDefaultRequestConfig(config, this.builder);
        return this;
    }

    @Override
    public SyncHttpClientBuilder setProxy(HttpHost proxy) {
        HttpClientBuilderUtils.setProxy(proxy, this.builder);
        return this;
    }

    @Override
    public SyncHttpClientBuilder setDefaultCredentialsProvider(CredentialsProvider cp) {
        HttpClientBuilderUtils.setDefaultCredentialsProvider(cp, this.builder);
        return this;
    }

    @Override
    public SyncHttpClientBuilder addRequestInterceptor(Consumer<MutableHttpRequest> interceptor) {
        return this;
    }
}
