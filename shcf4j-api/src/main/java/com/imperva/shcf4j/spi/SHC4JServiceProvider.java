package com.imperva.shcf4j.spi;

import com.imperva.shcf4j.HttpAsyncClientBuilder;
import com.imperva.shcf4j.HttpClientBuilder;


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

    HttpClientBuilder getHttpClientBuilder();

    HttpAsyncClientBuilder getHttpAsyncClientBuilder();

    default void initialize() {
    }
}
