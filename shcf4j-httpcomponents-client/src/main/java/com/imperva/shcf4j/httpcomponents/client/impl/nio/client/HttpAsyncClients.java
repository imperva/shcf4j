package com.imperva.shcf4j.httpcomponents.client.impl.nio.client;


import com.imperva.shcf4j.client.HttpAsyncClient;

/**
 * <b>HttpAsyncClients</b>
 * <p/>
 * Factory methods for {@link HttpAsyncClientBuilder} instances.
 *
 * @author <font color="blue">Maxim Kirilov</font>
 */
public class HttpAsyncClients {


    /**
     * Creates builder object for construction of custom
     * {@link HttpAsyncClientBuilder} instances.
     */
    public static HttpAsyncClientBuilder custom() {
        return new HttpAsyncClientBuilder(org.apache.http.impl.nio.client.HttpAsyncClientBuilder.create());
    }


    /**
     * Creates {@link HttpAsyncClient} instance that implements the most basic HTTP protocol support.
     */
    public static HttpAsyncClient createMinimal() {
        return new InternalClosableHttpAsyncClient(org.apache.http.impl.nio.client.HttpAsyncClients.createMinimal());
    }
}
