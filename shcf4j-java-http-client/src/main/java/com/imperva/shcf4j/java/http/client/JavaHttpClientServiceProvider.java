package com.imperva.shcf4j.java.http.client;

import com.imperva.shcf4j.AsyncHttpClientBuilder;
import com.imperva.shcf4j.SyncHttpClientBuilder;
import com.imperva.shcf4j.spi.SHC4JServiceProvider;

public class JavaHttpClientServiceProvider implements SHC4JServiceProvider {


    @Override
    public SyncHttpClientBuilder getHttpClientBuilder() {
        return new JavaSyncHttpClientBuilder();
    }

    @Override
    public AsyncHttpClientBuilder getHttpAsyncClientBuilder() {
        return null;
    }
}
