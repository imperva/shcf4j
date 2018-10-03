package com.imperva.shcf4j.httpcomponents.client.impl.client;

import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpMessage;
import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.HttpClient;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.config.SocketConfig;
import com.imperva.shcf4j.conn.routing.HttpRoutePlanner;
import com.imperva.shcf4j.conn.ssl.SSLSessionStrategy;
import com.imperva.shcf4j.conn.ssl.X509HostnameVerifier;
import com.imperva.shcf4j.httpcomponents.client.impl.ConversionUtils;
import com.imperva.shcf4j.httpcomponents.client.impl.HttpMessageWrapper;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.AbstractVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.DefaultRoutePlanner;
import org.apache.http.impl.conn.DefaultSchemePortResolver;

import javax.net.ssl.SSLException;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * <b>HttpClientBuilder</b>
 * <p/>
 * Builder for {@link HttpClient} instances.
 * <p/>
 * When a particular component is not explicitly this class will
 * use its default implementation.
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
 * <li>http.nonProxyHosts</li>
 * <li>http.keepAlive</li>
 * <li>http.maxConnections</li>
 * <li>http.agent</li>
 * </ul>
 * <p/>
 * Please note that some settings used by this class can be mutually
 * exclusive and may not apply when building {@link HttpClient}
 * instances.
 *
 * @author <font color="blue">Maxim Kirilov</font>
 *         <p/>
 *         Date: April 2014
 */
public class HttpClientBuilder {

    private final org.apache.http.impl.client.HttpClientBuilder builder;


    protected HttpClientBuilder(org.apache.http.impl.client.HttpClientBuilder httpClientBuilder) {
        this.builder = httpClientBuilder;
    }

    protected HttpClientBuilder(){
        this(org.apache.http.impl.client.HttpClients.custom());
    }


    public HttpClient build() {
        return new SimpleHttpClient(builder.build());
    }


    /**
     * @param strategy
     * @return
     */
    public HttpClientBuilder setSSLSessionStrategy(final SSLSessionStrategy strategy) {
        Objects.requireNonNull(strategy, "strategy");
        LayeredConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                strategy.getSslContext(),
                strategy.getSupportedProtocols(),
                strategy.getSupportedCipherSuites(),
                new X509HostnameVerifierAdapter(strategy.getHostnameVerifier()));

        this.builder.setSSLSocketFactory(socketFactory);
        return this;
    }



    /**
     * Disables connection state tracking.
     *
     * @return
     */
    public HttpClientBuilder disableConnectionState() {
        builder.disableConnectionState();
        return this;
    }

    /**
     * Disables automatic content decompression.
     */
    public HttpClientBuilder disableContentCompression() {
        builder.disableContentCompression();
        return this;
    }


    /**
     * Assigns maximum total connection value.
     *
     * @param maxConnTotal
     * @return
     */
    public HttpClientBuilder setMaxConnTotal(int maxConnTotal) {
        builder.setMaxConnTotal(maxConnTotal);
        return this;
    }

    /**
     * Assigns default {@link SocketConfig}.
     *
     * @param socketConfig
     */
    public HttpClientBuilder setDefaultSocketConfig(SocketConfig socketConfig) {
        Objects.requireNonNull(socketConfig, "socketConfig");
        org.apache.http.config.SocketConfig.Builder SocketBuilder = org.apache.http.config.SocketConfig.custom();
        SocketBuilder
                .setSoKeepAlive(socketConfig.isSoKeepAlive())
                .setSoLinger(socketConfig.getSoLingerSeconds())
                .setSoReuseAddress(socketConfig.isSoReuseAddress())
                .setSoTimeout(socketConfig.getSoTimeoutMilliseconds())
                .setTcpNoDelay(socketConfig.isTcpNoDelay());

        builder.setDefaultSocketConfig(SocketBuilder.build());
        return this;
    }

    /**
     * Assigns retryCount.
     *
     * @param retryCount
     */
    public HttpClientBuilder setRetryCount(Integer retryCount) {

        DefaultHttpRequestRetryHandler retryhandler = new DefaultHttpRequestRetryHandler(retryCount, true);
        builder.setRetryHandler(retryhandler);
        return this;
    }

        /**
     * Assigns default {@link RequestConfig} instance which will be used
     * for request execution if not explicitly set in the client execution
     * context.
     */
    public final HttpClientBuilder setDefaultRequestConfig(final RequestConfig config) {
        Objects.requireNonNull(config, "config");
        builder.setDefaultRequestConfig(ConversionUtils.convert(config));
        return this;
    }

    /**
     * Assigns default proxy value.
     */
    public HttpClientBuilder setProxy(HttpHost proxy) {
        Objects.requireNonNull(proxy, "proxy");
        builder.setProxy(ConversionUtils.convert(proxy));
        return this;
    }


    public HttpClientBuilder setDefaultCredentialsProvider(CredentialsProvider cp) {
        Objects.requireNonNull(cp, "cp");
        builder.setDefaultCredentialsProvider(ConversionUtils.convert(cp));
        return this;
    }


    public HttpClientBuilder setRoutePlanner(final HttpRoutePlanner routePlanner){
        Objects.requireNonNull(routePlanner, "routePlanner");
        final org.apache.http.conn.routing.HttpRoutePlanner defaultRoutePlanner =
                new DefaultRoutePlanner(DefaultSchemePortResolver.INSTANCE);
        builder.setRoutePlanner((target, request, context) -> {

            HttpHost t1 =
                    HttpHost
                    .builder()
                    .hostname(target.getHostName())
                    .schemeName(target.getSchemeName())
                    .port(target.getPort())
                    .build();

            t1 = routePlanner.determineRoute(t1, new HttpMessageWrapper(request));

            return defaultRoutePlanner.determineRoute(ConversionUtils.convert(t1), request , context);
        });
        return this;
    }

    /**
     * Adds a list of interceptors to the client, the order is according to the passed list iterator
     * implementation.
     *
     *
     * @param interceptors
     * @return
     */
    public HttpClientBuilder setRequestInterceptors(List<Consumer<HttpMessage>> interceptors){
        for (Consumer<HttpMessage> interceptor : interceptors){
            builder.addInterceptorLast(new HttpRequestInterceptorAdapter(interceptor));
        }
        return this;
    }

    /**
     * <b>X509HostnameVerifierAdapter</b>
     * <p/>
     * <p>
     * This inner class used to adapt user implementation to vendors one,
     * by using composition.
     * </p>
     *
     * @author <font color="blue">Maxim Kirilov</font>
     *         <p/>
     *         Date: May 2014
     */
    private static class X509HostnameVerifierAdapter extends AbstractVerifier {

        private final X509HostnameVerifier hostnameVerifier;

        private X509HostnameVerifierAdapter(X509HostnameVerifier hostnameVerifier) {
            this.hostnameVerifier = hostnameVerifier;
        }

        @Override
        public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
            this.hostnameVerifier.verify(host, cns, subjectAlts);
        }
    }


}
