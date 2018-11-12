package com.imperva.shcf4j.httpcomponents.client4;


/**
 * <b>HttpAsyncClients</b>
 * Factory methods for {@link AsyncHttpClientBuilder} instances.
 *
 * @author maxim.kirilov
 */
public class HttpAsyncClients {


    /**
     * Creates builder object for construction of custom
     * {@link AsyncHttpClientBuilder} instances.
     *
     * @return a {@code AsyncHttpClientBuilder} for customized implementation
     */
    public static AsyncHttpClientBuilder custom() {
        return new AsyncHttpClientBuilder(org.apache.http.impl.nio.client.HttpAsyncClientBuilder.create());
    }
}
