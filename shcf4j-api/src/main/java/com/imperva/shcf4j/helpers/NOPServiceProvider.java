package com.imperva.shcf4j.helpers;

import com.imperva.shcf4j.HttpAsyncClientBuilder;
import com.imperva.shcf4j.HttpClientBuilder;
import com.imperva.shcf4j.spi.SHC4JServiceProvider;

public class NOPServiceProvider implements SHC4JServiceProvider {

    public static final SHC4JServiceProvider INSTANCE = new NOPServiceProvider();


    @Override
    public HttpClientBuilder getHttpClientBuilder() {
        return NOPHttpClientBuilder.INSTANCE;
    }

    @Override
    public HttpAsyncClientBuilder getHttpAsyncClientBuilder() {
        return NOPHttpAsyncClientBuilder.INSTANCE;
    }

}
