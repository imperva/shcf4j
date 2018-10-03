package com.imperva.shcf4j.httpcomponents.client;

import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.client.HttpClient;
import com.imperva.shcf4j.config.SocketConfig;
import com.imperva.shcf4j.httpcomponents.client.impl.client.HttpClientBuilder;
import com.imperva.shcf4j.httpcomponents.client.impl.client.HttpClients;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * <b>SocketConfigTest</b>
 *
 * @author <font color="blue">Maxim Kirilov</font>
 *         <p/>
 *         Date: June 2014
 */
public class SocketConfigTest extends SyncHttpClientBaseTest {

    protected static HttpClient client;

    private static final int tmoutMilliseconds = 1000 * 10;


    @BeforeClass
    public static void init() {
        HttpClientBuilder builder = HttpClients.custom();
        builder.setDefaultSocketConfig(
                SocketConfig
                        .builder()
                        .soTimeoutMilliseconds(tmoutMilliseconds)
                        .build()
        );
        client = builder.build();
    }

    @Test(expected = SocketTimeoutException.class)
    public void requestTimeoutTest() throws IOException {

        String uri = "/my/resource";
        instanceRule.stubFor(get(urlEqualTo(uri))
                .withHeader(HEADER_ACCEPT, equalTo("text/xml"))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                                .withFixedDelay((int) tmoutMilliseconds + 1000)
                                .withHeader(HEADER_CONTENT_TYPE, "text/xml")
                )
        );
        HttpRequest request = HttpRequest.createGetRequest(uri);
        request.addHeader(HEADER_ACCEPT, "text/xml");
        getHttpClient().execute(HOST, request);
    }


    @Override
    HttpClient getHttpClient() {
        return client;
    }
}
