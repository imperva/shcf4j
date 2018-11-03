package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.HttpClientBuilderFactory;
import com.imperva.shcf4j.client.SyncHttpClient;
import com.imperva.shcf4j.test.RequestConfigTest;

public class SyncHttpComponentsClientRequestConfigTest extends RequestConfigTest {

    @Override
    public SyncHttpClient getHttpClient() {
        return HttpClientBuilderFactory.getHttpClientBuilder().build();
    }
}
