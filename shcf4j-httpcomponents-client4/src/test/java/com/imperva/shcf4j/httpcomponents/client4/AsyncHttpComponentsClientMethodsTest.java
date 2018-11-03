package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.HttpClientBuilderFactory;
import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.test.AsyncHttpMethodsTest;

public class AsyncHttpComponentsClientMethodsTest extends AsyncHttpMethodsTest {


    @Override
    protected AsyncHttpClient getHttpClient() {
        return HttpClientBuilderFactory.getHttpAsyncClientBuilder().build();
    }

}
