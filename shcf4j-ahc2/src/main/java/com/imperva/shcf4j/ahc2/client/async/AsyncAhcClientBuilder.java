package com.imperva.shcf4j.ahc2.client.async;

import com.imperva.shcf4j.AsyncHttpClientBuilder;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.conn.ssl.SSLSessionStrategy;
import com.imperva.shcf4j.nio.reactor.IOReactorConfig;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;

import javax.net.ssl.SSLException;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ThreadFactory;

public class AsyncAhcClientBuilder implements AsyncHttpClientBuilder {

    private DefaultAsyncHttpClientConfig.Builder configBuilder = new DefaultAsyncHttpClientConfig.Builder();


    @Override
    public AsyncHttpClientBuilder setThreadFactory(ThreadFactory threadFactory) {
        Objects.requireNonNull(threadFactory, "threadFactory");
        this.configBuilder.setThreadFactory(threadFactory);
        return this;
    }

    @Override
    public AsyncHttpClientBuilder setDefaultSocketConfig(IOReactorConfig ioReactorConfig) {
        Objects.requireNonNull(ioReactorConfig, "ioReactorConfig");
        this.configBuilder.setConnectTimeout(ioReactorConfig.getConnectTimeoutMilliseconds());
        this.configBuilder.setSoLinger(ioReactorConfig.getSoLingerSeconds());
        this.configBuilder.setSoRcvBuf(ioReactorConfig.getRcvBufSize());
        this.configBuilder.setSoSndBuf(ioReactorConfig.getSndBufSize());
        this.configBuilder.setTcpNoDelay(ioReactorConfig.isTcpNoDelay());
        this.configBuilder.setKeepAlive(ioReactorConfig.isSoKeepalive());
        this.configBuilder.setSoLinger(ioReactorConfig.getSoLingerSeconds());
        this.configBuilder.setSoReuseAddress(ioReactorConfig.isSoReuseAddress());
        this.configBuilder.setIoThreadsCount(ioReactorConfig.getIoThreadCount());

        return this;
    }


    @Override
    public AsyncHttpClientBuilder setSSLSessionStrategy(SSLSessionStrategy strategy) throws SSLException {

        this.configBuilder.setSslContext(
                SslContextBuilder
                        .forClient()
                        .protocols(strategy.getSupportedProtocols())
                        .ciphers(createIterableFrom(strategy.getSupportedCipherSuites()))
                        .sslProvider(SslProvider.JDK)
                        .keyManager(strategy.getKeyManagerFactory())
                        .trustManager(strategy.getTrustManagerFactory())
                        .trustManager()
                        .build());

        this.configBuilder.setUseInsecureTrustManager(strategy.getTrustManagerFactory() == null);

        return this;
    }

    @Override
    public AsyncHttpClientBuilder setDefaultRequestConfig(RequestConfig config) {
        this.configBuilder.setRequestTimeout(config.getSocketTimeoutMilliseconds());
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
    public AsyncHttpClient build() {
        return new ClosableAsyncAhcHttpClient(new DefaultAsyncHttpClient(configBuilder.build()));
    }

    @SafeVarargs
    private static <T> Iterable<T> createIterableFrom(final T... a){
        T[] arr = a;
        return () -> new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < arr.length;
            }

            @Override
            public T next() {
                return arr[index++];
            }
        };
    }
}
