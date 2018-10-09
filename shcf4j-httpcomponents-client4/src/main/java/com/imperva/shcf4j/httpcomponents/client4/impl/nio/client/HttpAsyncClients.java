package com.imperva.shcf4j.httpcomponents.client4.impl.nio.client;


import com.imperva.shcf4j.client.HttpAsyncClient;

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


    /**
     * Creates {@link HttpAsyncClient} instance that implements the most basic HTTP protocol support.
     *
     * @return a minimal {@code HttpAsyncClient} implementation
     */
    public static HttpAsyncClient createMinimal() {
        return new InternalClosableHttpAsyncClient(org.apache.http.impl.nio.client.HttpAsyncClients.createMinimal());
    }
}
