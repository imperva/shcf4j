package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.config.ConnectionConfig;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.conn.routing.HttpRoutePlanner;
import com.imperva.shcf4j.conn.ssl.SSLSessionStrategy;
import com.imperva.shcf4j.nio.reactor.IOReactorConfig;
import org.apache.http.impl.conn.DefaultRoutePlanner;
import org.apache.http.impl.conn.DefaultSchemePortResolver;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.ThreadFactory;

/**
 * <b>AsyncHttpClientBuilder</b>
 * <p>
 * Builder for {@link AsyncHttpClient} instances.
 * </p>
 * When a particular component is not explicitly this class will
 * use its default implementation. System properties will be taken
 * into account when configuring the default implementations when
 * {@link #useSystemProperties()} method is called prior to calling
 * {@link #build()}.
 * <ul>
 * <li>ssl.TrustManagerFactory.algorithm</li>
 * <li>javax.net.ssl.trustStoreType</li>
 * <li>javax.net.ssl.trustStore</li>
 * <li>javax.net.ssl.trustStoreProvider</li>
 * <li>javax.net.ssl.trustStorePassword</li>
 * <li>ssl.KeyManagerFactory.algorithm</li>
 * <li>javax.net.ssl.keyStoreType</li>
 * <li>javax.net.ssl.keyStore</li>
 * <li>javax.net.ssl.keyStoreProvider</li>
 * <li>javax.net.ssl.keyStorePassword</li>
 * <li>https.protocols</li>
 * <li>https.cipherSuites</li>
 * <li>http.proxyHost</li>
 * <li>http.proxyPort</li>
 * <li>http.keepAlive</li>
 * <li>http.maxConnections</li>
 * <li>http.agent</li>
 * </ul>
 * <p>
 * Please note that some settings used by this class can be mutually
 * exclusive and may not apply when building {@link AsyncHttpClient}
 * instances.
 *
 * @author maxim.kirilov
 */
class AsyncHttpClientBuilder implements com.imperva.shcf4j.AsyncHttpClientBuilder {

    private final org.apache.http.impl.nio.client.HttpAsyncClientBuilder asyncClientBuilder;

    protected AsyncHttpClientBuilder(org.apache.http.impl.nio.client.HttpAsyncClientBuilder asyncClient) {
        this.asyncClientBuilder = asyncClient;
    }

    protected AsyncHttpClientBuilder() {
        this(org.apache.http.impl.nio.client.HttpAsyncClientBuilder.create());
    }

    /**
     * Assigns maximum total connection value.
     *
     * @param maxConnTotal allowed in this client instance
     * @return {@code AsyncHttpClientBuilder}
     */
    public final AsyncHttpClientBuilder setMaxConnTotal(final int maxConnTotal) {
        this.asyncClientBuilder.setMaxConnTotal(maxConnTotal);
        return this;
    }

    /**
     * Assigns maximum connection per route value.
     *
     * @param maxConnPerRoute a limit of open connections for a single route
     * @return {@code SyncHttpClientBuilder}
     */
    public final AsyncHttpClientBuilder setMaxConnPerRoute(final int maxConnPerRoute) {
        this.asyncClientBuilder.setMaxConnPerRoute(maxConnPerRoute);
        return this;
    }

    /**
     * Disables connection state tracking.
     *
     * @return {@code SyncHttpClientBuilder}
     */
    public AsyncHttpClientBuilder disableConnectionState() {
        this.asyncClientBuilder.disableConnectionState();
        return this;
    }

    /**
     * @param strategy the SSL socket creation strategy
     * @return {@code SyncHttpClientBuilder}
     */
    @Override
    public AsyncHttpClientBuilder
    setSSLSessionStrategy(final SSLSessionStrategy strategy) throws SSLException {
        Objects.requireNonNull(strategy, "strategy");

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(
                    strategy.getKeyManagerFactory() != null ?
                            strategy.getKeyManagerFactory().getKeyManagers() : null,
                    strategy.getTrustManagerFactory() != null ?
                            strategy.getTrustManagerFactory().getTrustManagers() : null,
                    null
            );

            SSLIOSessionStrategy sslStrategy = new SSLIOSessionStrategy(
                    sslContext,
                    strategy.getSupportedProtocols(),
                    strategy.getSupportedCipherSuites(),
                    strategy.getHostnameVerifier());

            this.asyncClientBuilder.setSSLStrategy(sslStrategy);
            return this;
        } catch (NoSuchAlgorithmException | KeyManagementException e){
            throw new SSLException(e);
        }
    }


    /**
     * Assigns default {@link IOReactorConfig}.
     *
     * @param ioReactorConfig object
     * @return {@code SyncHttpClientBuilder}
     */
    @Override
    public AsyncHttpClientBuilder setDefaultSocketConfig(IOReactorConfig ioReactorConfig) {
        Objects.requireNonNull(ioReactorConfig, "ioReactorConfig");
        org.apache.http.impl.nio.reactor.IOReactorConfig.Builder builder =
                org.apache.http.impl.nio.reactor.IOReactorConfig.custom();

        builder.setSelectInterval(ioReactorConfig.getSelectIntervalMilliseconds())
                .setShutdownGracePeriod(ioReactorConfig.getShutdownGracePeriod())
                .setInterestOpQueued(ioReactorConfig.isInterestOpQueued())
                .setIoThreadCount(ioReactorConfig.getIoThreadCount())
                .setSoTimeout(ioReactorConfig.getSoTimeoutMilliseconds())
                .setSoReuseAddress(ioReactorConfig.isSoReuseAddress())
                .setSoLinger(ioReactorConfig.getSoLingerSeconds())
                .setSoKeepAlive(ioReactorConfig.isSoKeepalive())
                .setTcpNoDelay(ioReactorConfig.isTcpNoDelay())
                .setConnectTimeout(ioReactorConfig.getConnectTimeoutMilliseconds())
                .setSndBufSize(ioReactorConfig.getSndBufSize())
                .setRcvBufSize(ioReactorConfig.getRcvBufSize());

        this.asyncClientBuilder.setDefaultIOReactorConfig(builder.build());
        return this;
    }

    /**
     * Assigns default {@link ConnectionConfig}.
     *
     * @param config default config for every outgoing request
     * @return {@code SyncHttpClientBuilder}
     */
    public final AsyncHttpClientBuilder setDefaultConnectionConfig(final ConnectionConfig config) {

        org.apache.http.config.ConnectionConfig.Builder builder = org.apache.http.config.ConnectionConfig.custom();
        builder.setBufferSize(config.getBufferSize())
                .setCharset(config.getCharset())
                .setMalformedInputAction(config.getMalformedInputAction())
                .setUnmappableInputAction(config.getUnmappableInputAction())
                .setFragmentSizeHint(config.getFragmentSizeHint());

        this.asyncClientBuilder.setDefaultConnectionConfig(builder.build());
        return this;
    }

    /**
     * Assigns default {@link RequestConfig} instance which will be used
     * for request execution if not explicitly set in the client4 execution
     * context.
     *
     * @param config default config for every outgoing request
     * @return {@code AsyncHttpClientBuilder}
     */
    public final AsyncHttpClientBuilder setDefaultRequestConfig(final RequestConfig config) {

        Objects.requireNonNull(config, "config");
        this.asyncClientBuilder.setDefaultRequestConfig(ConversionUtils.convert(config));
        return this;
    }

    /**
     * Assigns {@link java.util.concurrent.ThreadFactory} instance.
     * @param threadFactory the factory that creates the threads
     * @return {@code AsyncHttpClientBuilder}
     */
    public final AsyncHttpClientBuilder setThreadFactory(final ThreadFactory threadFactory) {
        this.asyncClientBuilder.setThreadFactory(threadFactory);
        return this;
    }


    /**
     * Use system properties when creating and configuring default
     * implementations.
     *
     * @return an {@code AsyncHttpClientBuilder} that initialized with system properties
     */
    public final AsyncHttpClientBuilder useSystemProperties() {
        this.asyncClientBuilder.useSystemProperties();
        return this;
    }

    /**
     * Assigns default proxy value.
     *
     * @param proxy object for every outgoing request
     * @return {@code SyncHttpClientBuilder}
     */
    public AsyncHttpClientBuilder setProxy(HttpHost proxy) {
        Objects.requireNonNull(proxy, "proxy");
        asyncClientBuilder.setProxy(ConversionUtils.convert(proxy));
        return this;
    }


    public AsyncHttpClientBuilder setDefaultCredentialsProvider(CredentialsProvider cp) {
        Objects.requireNonNull(cp, "cp");
        asyncClientBuilder.setDefaultCredentialsProvider(ConversionUtils.convert(cp));
        return this;
    }

    public AsyncHttpClientBuilder setRoutePlanner(final HttpRoutePlanner routePlanner) {
        Objects.requireNonNull(routePlanner, "routePlanner");
        final org.apache.http.conn.routing.HttpRoutePlanner defaultRoutePlanner =
                new DefaultRoutePlanner(DefaultSchemePortResolver.INSTANCE);
        asyncClientBuilder.setRoutePlanner((target, request, context) -> {

            HttpHost t1 =
                    HttpHost
                            .builder()
                            .hostname(target.getHostName())
                            .schemeName(target.getSchemeName())
                            .port(target.getPort())
                            .build();

            t1 = routePlanner.determineRoute(t1, new HttpMessageWrapper(request));

            return defaultRoutePlanner.determineRoute(ConversionUtils.convert(t1), request, context);
        });
        return this;
    }

    public AsyncHttpClient build() {
        return new ClosableAsyncHttpClient(this.asyncClientBuilder.build());
    }

}
