package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.client.SyncHttpClient;

/**
 * <b>HttpClients</b>
 * <p>
 * Factory methods for {@link SyncHttpClient} instances.
 * </p>
 *
 * @author maxim.kirilov
 */
public class HttpClients {


    /**
     * Creates builder object for construction of custom
     * {@link SyncHttpClient} instances.
     *
     * @return {@code SyncHttpClientBuilder}
     */
    public static SyncHttpClientBuilder custom() {
        return new SyncHttpClientBuilder(
                org.apache.http.impl.client.HttpClients.custom()
        );
    }

}
