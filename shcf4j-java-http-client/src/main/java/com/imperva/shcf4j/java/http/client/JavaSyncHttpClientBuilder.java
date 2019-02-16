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
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
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

        this.builder.sslParameters(new SSLParameters(strategy.getSupportedCipherSuites(), strategy.getSupportedProtocols()));

        try {
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            KeyManager[] km = strategy.getKeyManagerFactory() != null ? strategy.getKeyManagerFactory().getKeyManagers() : null;
            TrustManager[] tm = strategy.getTrustManagerFactory() != null ? strategy.getTrustManagerFactory().getTrustManagers() : null;
            sslContext.init(km, tm, null);
            this.builder.sslContext(sslContext);
        } catch (NoSuchAlgorithmException | KeyManagementException e){
            throw new SSLException(e);
        }

        return this;
    }

    @Override
    public SyncHttpClientBuilder setDefaultRequestConfig(RequestConfig config) {
        this.builder.followRedirects(config.isRedirectsEnabled() ? HttpClient.Redirect.ALWAYS : HttpClient.Redirect.NEVER);

        if (config.getProxy() != null) { // Handle Proxy
            return setProxy(config.getProxy());
        }

        if (config.getConnectTimeoutMilliseconds() > 0){
            this.builder.connectTimeout(Duration.ofMillis(config.getConnectTimeoutMilliseconds()));
        }


//        builder.cookieHandler()


        return this;
    }

    @Override
    public SyncHttpClientBuilder setProxy(HttpHost proxy) {
        this.builder.proxy(ProxySelector.of(new InetSocketAddress(proxy.getHostname(), proxy.getPort())));
        return this;
    }

    @Override
    public SyncHttpClientBuilder setDefaultCredentialsProvider(CredentialsProvider cp) {
        return this;
    }

    @Override
    public SyncHttpClientBuilder addRequestInterceptor(Consumer<MutableHttpRequest> interceptor) {
        return this;
    }
}
