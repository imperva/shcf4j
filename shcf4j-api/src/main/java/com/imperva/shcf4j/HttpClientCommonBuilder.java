package com.imperva.shcf4j;

import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.conn.ssl.SSLSessionStrategy;

import javax.net.ssl.SSLException;
import java.util.function.Consumer;

/**
 * <b>HttpClientCommonBuilder</b>
 *
 * <p>
 *     A common methods for both {@link SyncHttpClientBuilder} and {@link AsyncHttpClientBuilder}
 * </p>
 *
 * @author maxim.kirilov
 */
public interface HttpClientCommonBuilder<T> {

    T setSSLSessionStrategy(final SSLSessionStrategy strategy) throws SSLException;

    T setDefaultRequestConfig(final RequestConfig config);

    T setProxy(HttpHost proxy);

    T setDefaultCredentialsProvider(CredentialsProvider cp);

    T addRequestInterceptor(Consumer<MutableHttpRequest> interceptor);

}
