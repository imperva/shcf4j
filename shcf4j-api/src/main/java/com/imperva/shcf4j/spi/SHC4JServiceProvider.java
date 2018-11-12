package com.imperva.shcf4j.spi;

import com.imperva.shcf4j.AsyncHttpClientBuilder;
import com.imperva.shcf4j.SyncHttpClientBuilder;


/**
 * <b>SHC4JServiceProvider</b>
 *
 * <p>
 * An interface for HTTP client libraries vendors integrations.
 * </p>
 *
 * @author maxim.kirilov
 */
public interface SHC4JServiceProvider {

    SyncHttpClientBuilder getHttpClientBuilder();

    AsyncHttpClientBuilder getHttpAsyncClientBuilder();

    default void initialize() {
    }
}
