package com.imperva.shcf4j.config;

import com.imperva.shcf4j.HttpClientBaseTest;
import com.imperva.shcf4j.HttpClientCommonBuilder;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpRequestBuilder;
import com.imperva.shcf4j.Request;
import com.imperva.shcf4j.client.config.CookieSpecs;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.client.protocol.ClientContext;
import org.junit.Assert;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.absent;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * <b>RequestConfigTest</b>
 */
public abstract class RequestConfigTest extends HttpClientBaseTest {

    protected static final String RESOURCE_URI = "/my/resource";


    private void registerStubWithDelayedResponse(long delayMillis) {
        instanceRule.stubFor(get(urlEqualTo(RESOURCE_URI))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                                .withFixedDelay((int) delayMillis)
                )
        );
    }


    @Test(expected = Exception.class)
    public void requestTimeoutTest() {

        long millisecondsDelay = TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS);
        registerStubWithDelayedResponse(millisecondsDelay);

        HttpRequest request =
                HttpRequestBuilder
                        .GET()
                        .uri(RESOURCE_URI)
                        .build();

        execute(HOST, request,
                ClientContext
                        .builder()
                        .requestConfig(
                                RequestConfig
                                        .builder()
                                        .socketTimeoutMilliseconds((int) millisecondsDelay / 2)
                                        .build())
                        .build());
    }

    @Test(expected = Exception.class)
    public void globalRequestTimeoutTest() {

        long millisecondsDelay = TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS);
        registerStubWithDelayedResponse(millisecondsDelay);
        HttpRequest request =
                HttpRequestBuilder
                        .GET()
                        .uri(RESOURCE_URI)
                        .build();
        RequestConfig rc =
                RequestConfig
                        .builder()
                        .socketTimeoutMilliseconds((int) millisecondsDelay / 2)
                        .build();


        execute(HOST, request, Function.identity(), null, b -> b.setDefaultRequestConfig(rc));
    }


    @Test
    public void requestInterceptorTest() {

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

    @Test
    public void requestIgnoreCookiesWithClientContextTest() {
        String cookie = "sessionToken=abc123";

        instanceRule.stubFor(get(urlEqualTo(RESOURCE_URI))
                .withHeader("Cookie", absent())
                .willReturn(
                        aResponse()
                                .withHeader("Set-Cookie", cookie)
                                .withStatus(HttpURLConnection.HTTP_OK)
                )
        );

        RequestConfig rc = RequestConfig.builder().cookieSpec(CookieSpecs.IGNORE_COOKIES).build();
        ClientContext ctx = ClientContext.builder().requestConfig(rc).build();

        Request<?> request =
                Request
                        .builder()
                        .host(HOST)
                        .request(HttpRequestBuilder.GET(URI.create(RESOURCE_URI)).build())
                        .callback(resp -> {
                            assertThat(resp.getStatusLine().getStatusCode()).isEqualTo(200);
                            return null;
                        })
                        .ctx(ctx)
                        .build();

        execute(Arrays.asList(request, request), getHttpClientBuilderConsumer());

    }

    @Test
    public void requestIgnoreCookiesWithHttpClientBuilderTest() {
        String cookie = "sessionToken=abc123";

        instanceRule.stubFor(get(urlEqualTo(RESOURCE_URI))
                .withHeader("Cookie", absent())
                .willReturn(
                        aResponse()
                                .withHeader("Set-Cookie", cookie)
                                .withStatus(HttpURLConnection.HTTP_OK)
                )
        );

        RequestConfig rc = RequestConfig.builder().cookieSpec(CookieSpecs.IGNORE_COOKIES).build();

        Request<?> request =
                Request
                        .builder()
                        .host(HOST)
                        .request(HttpRequestBuilder.GET(URI.create(RESOURCE_URI)).build())
                        .callback(resp -> {
                            assertThat(resp.getStatusLine().getStatusCode()).isEqualTo(200);
                            return null;
                        })
                        .build();

        execute(Arrays.asList(request, request), b -> b.setDefaultRequestConfig(rc));
    }

    @Test
    public void requestStoreCookieWithClientContextTest() {
        String cookieName = "sessionToken", cookieValue = "abc123";

        instanceRule.stubFor(get(urlEqualTo(RESOURCE_URI))
                .withHeader("Cookie", absent())
                .willReturn(
                        aResponse()
                                .withHeader("Set-Cookie", cookieName + "=" + cookieValue)
                                .withStatus(HttpURLConnection.HTTP_OK)
                )
        );


        String subResourceUri = RESOURCE_URI + "/hello";
        instanceRule.stubFor(get(urlEqualTo(subResourceUri))
                .withHeader("Cookie", equalTo(cookieName + "=" + cookieValue))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                )
        );


        RequestConfig rc = RequestConfig.builder().cookieSpec(CookieSpecs.STANDARD_RFC_6265).build();
        ClientContext ctx = ClientContext.builder().requestConfig(rc).build();


        Request<?> request1 =
                Request
                        .builder()
                        .host(HOST)
                        .request(HttpRequestBuilder.GET(URI.create(RESOURCE_URI)).build())
                        .callback(resp -> {
                            assertThat(resp.getStatusLine().getStatusCode()).isEqualTo(200);
                            return null;
                        })
                        .ctx(ctx)
                        .build();

        Request<?> request2 =
                Request
                        .builder()
                        .host(HOST)
                        .request(HttpRequestBuilder.GET(URI.create(subResourceUri)).build())
                        .callback(resp -> {
                            assertThat(resp.getStatusLine().getStatusCode()).isEqualTo(200);
                            return null;
                        })
                        .ctx(ctx)
                        .build();


        execute(Arrays.asList(request1, request2), getHttpClientBuilderConsumer());
    }

    @Test
    public void requestStoreCookieWithHttpClientBuilderTest() {
        String cookieName = "sessionToken", cookieValue = "abc123";

        instanceRule.stubFor(get(urlEqualTo(RESOURCE_URI))
                .withHeader("Cookie", absent())
                .willReturn(
                        aResponse()
                                .withHeader("Set-Cookie", cookieName + "=" + cookieValue)
                                .withStatus(HttpURLConnection.HTTP_OK)
                )
        );


        String subResourceUri = RESOURCE_URI + "/hello";
        instanceRule.stubFor(get(urlEqualTo(subResourceUri))
                .withHeader("Cookie", equalTo(cookieName + "=" + cookieValue))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                )
        );


        RequestConfig rc = RequestConfig.builder().cookieSpec(CookieSpecs.STANDARD_RFC_6265).build();


        Request<?> request1 =
                Request
                        .builder()
                        .host(HOST)
                        .request(HttpRequestBuilder.GET(URI.create(RESOURCE_URI)).build())
                        .callback(resp -> {
                            assertThat(resp.getStatusLine().getStatusCode()).isEqualTo(200);
                            return null;
                        })
                        .build();

        Request<?> request2 =
                Request
                        .builder()
                        .host(HOST)
                        .request(HttpRequestBuilder.GET(URI.create(subResourceUri)).build())
                        .callback(resp -> {
                            assertThat(resp.getStatusLine().getStatusCode()).isEqualTo(200);
                            return null;
                        })
                        .build();


        execute(Arrays.asList(request1, request2), b -> b.setDefaultRequestConfig(rc));
    }


    protected Consumer<HttpClientCommonBuilder<?>> getHttpClientBuilderConsumer() {
        return b -> {};
    }
}



