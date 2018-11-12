package com.imperva.shcf4j.helpers;

import com.imperva.shcf4j.AsyncHttpClientBuilder;
import com.imperva.shcf4j.SyncHttpClientBuilder;
import com.imperva.shcf4j.spi.SHC4JServiceProvider;

public class NOPServiceProvider implements SHC4JServiceProvider {

    public static final SHC4JServiceProvider INSTANCE = new NOPServiceProvider();


    @Override
    public SyncHttpClientBuilder getHttpClientBuilder() {
        return NOPSyncHttpClientBuilder.INSTANCE;
    }

    @Override
    public AsyncHttpClientBuilder getHttpAsyncClientBuilder() {
        return NOPAsyncHttpClientBuilder.INSTANCE;
    }

}
