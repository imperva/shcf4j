package com.imperva.shcf4j.httpcomponents.client;

import com.imperva.shcf4j.client.HttpClient;
import com.imperva.shcf4j.httpcomponents.client.impl.client.HttpClientBuilder;
import com.imperva.shcf4j.httpcomponents.client.impl.client.HttpClients;
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
        HttpClientBuilder builder = HttpClients.custom();
        client = builder.build();
    }


}
