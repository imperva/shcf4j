package com.imperva.shcf4j.httpcomponents.client4;


/**
 * <b>HttpAsyncClients</b>
 * Factory methods for {@link HttpComponentsAsyncHttpClientBuilder} instances.
 *
 * @author maxim.kirilov
 */
public class HttpAsyncClients {


    /**
     * Creates builder object for construction of custom
     * {@link HttpComponentsAsyncHttpClientBuilder} instances.
     *
     * @return a {@code AsyncHttpClientBuilder} for customized implementation
     */
    public static HttpComponentsAsyncHttpClientBuilder custom() {
        return new HttpComponentsAsyncHttpClientBuilder(org.apache.http.impl.nio.client.HttpAsyncClientBuilder.create());
    }
}
