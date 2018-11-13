package com.imperva.shcf4j.ahc2.client;

import com.imperva.shcf4j.HttpClientBuilderFactory;
import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.test.AsyncHttpMethodsTest;

public class AsyncNettyClientMethodsTest extends AsyncHttpMethodsTest {


    @Override
    protected AsyncHttpClient getHttpClient() {
        return HttpClientBuilderFactory.getHttpAsyncClientBuilder().build();
    }

}
