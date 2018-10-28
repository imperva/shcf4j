package com.imperva.shcf4j;

import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.conn.ssl.SSLSessionStrategy;

/**
 * <b>HttpClientCommonBuilder</b>
 *
 * <p>
 *     A common methods for both {@link HttpClientBuilder} and {@link HttpAsyncClientBuilder}
 * </p>
 *
 * @author maxim.kirilov
 */
public interface HttpClientCommonBuilder<T> {

    T setSSLSessionStrategy(final SSLSessionStrategy strategy);

    T setDefaultRequestConfig(final RequestConfig config);

    T setProxy(HttpHost proxy);

    T setDefaultCredentialsProvider(CredentialsProvider cp);

}
