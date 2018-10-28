package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.HttpClientBuilder;
import com.imperva.shcf4j.HttpClientBuilderFactory;
import com.imperva.shcf4j.client.HttpClient;
import org.junit.BeforeClass;

/**
 * <b>CustomClientTest</b>
 *
 * @author <font color="blue">Maxim Kirilov</font>
 *         Date: May 2014
 */
public class CustomClientTest {

    protected static HttpClient client;

    @BeforeClass
    public static void init() {
        HttpClientBuilder builder = HttpClientBuilderFactory.getHttpClientBuilder();
        client = builder.build();
    }


}
