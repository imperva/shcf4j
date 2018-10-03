package com.imperva.shcf4j.httpcomponents.client4.impl.client;

import com.imperva.shcf4j.client.HttpClient;

/**
 * <b>HttpClients</b>
 * <p/>
 * <p>
 * Factory methods for {@link HttpClient} instances.
 * </p>
 *
 * @author <font color="blue">Maxim Kirilov</font>
 *         <p/>
 *         Date: April 2014
 */
public class HttpClients {


    /**
     * Creates {@link HttpClient} instance with default
     * configuration based on system properties.
     */
    public static HttpClient createSystem() {
        return new SimpleHttpClient(
                org.apache.http.impl.client.HttpClients.createSystem()
        );
    }


    /**
     * Creates {@link HttpClient} instance that implements
     * the most basic HTTP protocol support.
     */
    public static HttpClient createMinimal() {
        return new SimpleHttpClient(
                org.apache.http.impl.client.HttpClients.createMinimal()
        );
    }

    /**
     * Creates {@link HttpClient} instance with default configuration.
     */
    public static HttpClient createDefault() {
        return new SimpleHttpClient(
                org.apache.http.impl.client.HttpClients.createDefault()
        );
    }


    /**
     * Creates builder object for construction of custom
     * {@link HttpClient} instances.
     */
    public static HttpClientBuilder custom() {
        return new HttpClientBuilder(
                org.apache.http.impl.client.HttpClients.custom()
        );
    }

}
