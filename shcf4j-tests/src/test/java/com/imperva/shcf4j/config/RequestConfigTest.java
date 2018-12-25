package com.imperva.shcf4j.config;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpClientBaseTest;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpRequestBuilder;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.client.protocol.ClientContext;
import org.apache.http.client.methods.RequestBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

/**
 * <b>RequestConfigTest</b>
 */
public abstract class RequestConfigTest extends HttpClientBaseTest {

    private static final String RESOURCE_URI = "/my/resource";


    @Test(expected = Exception.class)
    public void requestTimeoutTest() throws IOException {

        long millisecondsDelay = TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS);
        instanceRule.stubFor(get(urlEqualTo(RESOURCE_URI))
                .withHeader(HEADER_ACCEPT, equalTo("text/xml"))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                                .withFixedDelay((int) millisecondsDelay)
                                .withHeader(HEADER_CONTENT_TYPE, "text/xml")
                )
        );

        HttpRequest request =
                HttpRequestBuilder
                        .GET()
                        .uri(RESOURCE_URI)
                        .header(Header.builder().name(HttpClientBaseTest.HEADER_ACCEPT).value("text/xml").build())
                        .build();

        execute(HOST, request,
                ClientContext
                        .builder()
                        .requestConfig(
                                RequestConfig
                                        .builder()
                                        .socketTimeoutMilliseconds( (int)millisecondsDelay / 2)
                                        .build())
                        .build());
    }


    @Test
    public void requestInterceptorTest() throws IOException {

        String injectedHeaderName = "X-Injected", injectedHeaderValue = "X-Injected-Value";

        instanceRule.stubFor(get(urlEqualTo(RESOURCE_URI))
                .withHeader(injectedHeaderName, equalTo(injectedHeaderValue))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                )
        );

        execute(HOST,
                HttpRequestBuilder.GET(URI.create(RESOURCE_URI)).build(),
                resp -> {
                    Assert.assertEquals(200, resp.getStatusLine().getStatusCode());
                    return null;
                },
                null,
                b -> b.addRequestInterceptor(request -> request.addHeader(injectedHeaderName, injectedHeaderValue)));
    }

}



