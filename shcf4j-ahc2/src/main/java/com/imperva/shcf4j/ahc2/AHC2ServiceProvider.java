package com.imperva.shcf4j.ahc2;

import com.imperva.shcf4j.AsyncHttpClientBuilder;
import com.imperva.shcf4j.SyncHttpClientBuilder;
import com.imperva.shcf4j.ahc2.client.async.AsyncAhcClientBuilder;
import com.imperva.shcf4j.spi.SHC4JServiceProvider;


/**
 * @author maxim.kirilov
 */
public class AHC2ServiceProvider implements SHC4JServiceProvider {


    @Override
    public SyncHttpClientBuilder getHttpClientBuilder() {
        return null;
    }

    @Override
    public AsyncHttpClientBuilder getHttpAsyncClientBuilder() {
        return new AsyncAhcClientBuilder();
    }
}
