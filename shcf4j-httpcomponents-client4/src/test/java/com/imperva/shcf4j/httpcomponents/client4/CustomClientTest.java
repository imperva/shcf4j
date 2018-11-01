package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.SyncHttpClientBuilder;
import com.imperva.shcf4j.HttpClientBuilderFactory;
import com.imperva.shcf4j.client.SyncHttpClient;
import org.junit.BeforeClass;

/**
 * <b>CustomClientTest</b>
 *
 * @author <font color="blue">Maxim Kirilov</font>
 *         Date: May 2014
 */
public class CustomClientTest {

    protected static SyncHttpClient client;

    @BeforeClass
    public static void init() {
        SyncHttpClientBuilder builder = HttpClientBuilderFactory.getHttpClientBuilder();
        client = builder.build();
    }


}
