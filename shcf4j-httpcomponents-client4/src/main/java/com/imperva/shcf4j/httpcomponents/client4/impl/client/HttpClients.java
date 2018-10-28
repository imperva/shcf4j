package com.imperva.shcf4j.httpcomponents.client4.impl.client;

import com.imperva.shcf4j.client.HttpClient;

/**
 * <b>HttpClients</b>
 * <p>
 * Factory methods for {@link HttpClient} instances.
 * </p>
 *
 * @author maxim.kirilov
 */
public class HttpClients {


    /**
     * Creates builder object for construction of custom
     * {@link HttpClient} instances.
     *
     * @return {@code HttpClientBuilder}
     */
    public static HttpClientBuilder custom() {
        return new HttpClientBuilder(
                org.apache.http.impl.client.HttpClients.custom()
        );
    }

}
