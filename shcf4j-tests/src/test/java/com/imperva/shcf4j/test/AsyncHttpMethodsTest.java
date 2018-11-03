package com.imperva.shcf4j.test;

import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.client.protocol.ClientContext;
import com.imperva.shcf4j.concurrent.FutureCallback;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

/**
 */
public abstract class AsyncHttpMethodsTest extends AsyncHttpClientBaseTest {

    protected AsyncHttpClient asyncHttpClient;

    private static final long TEST_TMOUT = 10000L;

    private volatile boolean isCallbackExecuted;


    @Before
    public void setup() {
        asyncHttpClient = getHttpClient();
        isCallbackExecuted = false;
    }

    @After
    public void tear() {
        try {
            asyncHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test(timeout = TEST_TMOUT)
    public void getMethodTest() {
        String uri = "/my/resource";
        instanceRule.stubFor(get(urlEqualTo(uri))
                .withHeader(HEADER_ACCEPT, equalTo("text/xml"))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                                .withHeader(HEADER_CONTENT_TYPE, "text/xml")
                )
        );

        HttpRequest request = HttpRequest.createGetRequest(uri);
        request.addHeader(HEADER_ACCEPT, "text/xml");
        Assert.assertTrue("Accept header is missing", request.containsHeader(HEADER_ACCEPT));

        getHttpClient().execute(HOST, request, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse response) {
                Assert.assertEquals("Response code is wrong",
                        HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
                Assert.assertEquals("Content Header is wrong",
                        "text/xml", response.getHeaders(HEADER_CONTENT_TYPE).get(0).getValue());
                isCallbackExecuted = true;
            }
        });

        while (!isCallbackExecuted) {
            sleep(1000L);
        }
    }

    @Test(timeout = TEST_TMOUT)
    public void failedCallbackTest() {
        String uri = "/delayed";
        instanceRule.stubFor(get(urlEqualTo(uri))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                                .withFixedDelay(60 * 1000)
                )
        );
        HttpRequest request = HttpRequest.createGetRequest(uri);
        ClientContext ctx = ClientContext
                .builder()
                .requestConfig(
                        RequestConfig
                                .builder()
                                .socketTimeoutMilliseconds(500)
                                .build())
                .build();

        getHttpClient().execute(HOST, request, ctx, new FutureCallback<HttpResponse>() {
            @Override
            public void failed(Exception ex) {
                isCallbackExecuted = true;
            }
        });

        while (!isCallbackExecuted) {
            sleep(1000L);
        }
    }


    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
        }
    }


}
