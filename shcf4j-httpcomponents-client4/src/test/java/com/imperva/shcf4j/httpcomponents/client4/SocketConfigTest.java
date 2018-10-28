package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.HttpClientBuilder;
import com.imperva.shcf4j.HttpClientBuilderFactory;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.client.HttpClient;
import com.imperva.shcf4j.config.SocketConfig;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

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
        HttpClientBuilder builder = HttpClientBuilderFactory.getHttpClientBuilder();
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
