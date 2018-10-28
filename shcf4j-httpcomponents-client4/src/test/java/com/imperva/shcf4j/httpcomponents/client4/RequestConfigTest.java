package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.HttpClientBuilderFactory;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.client.HttpClient;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.client.protocol.ClientContext;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

/**
 * <b>RequestConfigTest</b>
 */
public class RequestConfigTest extends SyncHttpClientBaseTest {

    private final HttpClient client = HttpClientBuilderFactory.getHttpClientBuilder().build();

    @Override
    HttpClient getHttpClient() {
        return client;
    }

    @Test(expected = SocketTimeoutException.class)
    public void requestTimeoutTest() throws IOException {

        long millisecondsDelay = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES);
        String uri = "/my/resource";
        instanceRule.stubFor(get(urlEqualTo(uri))
                .withHeader(HEADER_ACCEPT, equalTo("text/xml"))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                                .withFixedDelay((int) millisecondsDelay)
                                .withHeader(HEADER_CONTENT_TYPE, "text/xml")
                )
        );

        HttpRequest request = HttpRequest.createGetRequest(uri);
        request.addHeader(HEADER_ACCEPT, "text/xml");

        getHttpClient().execute(HOST, request,
                ClientContext
                        .builder()
                        .requestConfig(
                                RequestConfig
                                        .builder()
                                        .socketTimeoutMilliseconds( (int)millisecondsDelay - 1000)
                                        .build())
                        .build());
    }

}



