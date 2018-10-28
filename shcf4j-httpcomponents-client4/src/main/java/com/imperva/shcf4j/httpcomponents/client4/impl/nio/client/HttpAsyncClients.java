package com.imperva.shcf4j.httpcomponents.client4.impl.nio.client;


/**
 * <b>HttpAsyncClients</b>
 * Factory methods for {@link HttpAsyncClientBuilder} instances.
 *
 * @author maxim.kirilov
 */
public class HttpAsyncClients {


    /**
     * Creates builder object for construction of custom
     * {@link HttpAsyncClientBuilder} instances.
     *
     * @return a {@code HttpAsyncClientBuilder} for customized implementation
     */
    public static HttpAsyncClientBuilder custom() {
        return new HttpAsyncClientBuilder(org.apache.http.impl.nio.client.HttpAsyncClientBuilder.create());
    }
}
