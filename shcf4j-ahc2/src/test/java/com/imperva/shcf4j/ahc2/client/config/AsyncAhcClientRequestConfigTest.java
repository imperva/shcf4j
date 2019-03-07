package com.imperva.shcf4j.ahc2.client.config;

import com.imperva.shcf4j.AsyncClientBaseTest;
import com.imperva.shcf4j.HttpRequestBuilder;
import com.imperva.shcf4j.Request;
import com.imperva.shcf4j.client.config.CookieSpecs;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.client.protocol.ClientContext;
import com.imperva.shcf4j.config.RequestConfigTest;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Arrays;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.absent;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

public class AsyncAhcClientRequestConfigTest extends RequestConfigTest implements AsyncClientBaseTest {

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

        execute(Arrays.asList(request, request), builder -> builder.setDefaultRequestConfig(rc));

    }

}
