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
     * Creates {@link HttpClient} instance with default
     * configuration based on system properties.
     *
     * @return an instance that based on system properties {@code HttpClient}
     */
    public static HttpClient createSystem() {
        return new SimpleHttpClient(
                org.apache.http.impl.client.HttpClients.createSystem()
        );
    }


    /**
     * Creates {@link HttpClient} instance that implements
     * the most basic HTTP protocol support.
     *
     * @return a minimal {@code HttpClient}
     */
    public static HttpClient createMinimal() {
        return new SimpleHttpClient(
                org.apache.http.impl.client.HttpClients.createMinimal()
        );
    }

    /**
     * Creates {@link HttpClient} instance with default configuration.
     *
     * @return a default {@code HttpClient}
     */
    public static HttpClient createDefault() {
        return new SimpleHttpClient(
                org.apache.http.impl.client.HttpClients.createDefault()
        );
    }


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
