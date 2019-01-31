package com.imperva.shcf4j.conn.ssl;

import com.imperva.shcf4j.HttpClientBaseTest;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpRequestBuilder;
import com.imperva.shcf4j.HttpResponse;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.util.function.Function;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * <b>SecureConnectionTest</b>
 *
 * <p>
 * A test suite that tet various HTTP client providers configurations against
 * HTTPS endpoints.
 * </p>
 *
 * @author maxim.kirilov
 */
public abstract class SecureConnectionTest extends HttpClientBaseTest {


    @Test
    public void allowAllHostnameVerifierTest() {
        String uri = "/hello-ssl";
        instanceRule.stubFor(get(urlEqualTo(uri))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                                .withHeader(HttpClientBaseTest.HEADER_CONTENT_TYPE, "text/xml")
                )
        );

        HttpRequest request =
                HttpRequestBuilder
                        .GET()
                        .uri(uri)
                        .header(HttpClientBaseTest.HEADER_ACCEPT, "text/xml")
                        .build();

        DefaultSSLSessionStrategy sslSessionStrategy = new DefaultSSLSessionStrategy();

        sslSessionStrategy.setHostnameVerifier(new AllowAllHostnameVerifier());
        sslSessionStrategy.setTrustManagerFactory(InsecureTrustManagerFactory.INSTANCE);

        HttpResponse response = execute(HttpClientBaseTest.SECURED_HOST, request, Function.identity(), null, builder -> {
            try {
                builder.setSSLSessionStrategy(sslSessionStrategy);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
    }

    @Test(expected = Exception.class)
    public void strictHostnameVerifierTest() {
        String uri = "/hello-ssl";
        instanceRule.stubFor(get(urlEqualTo(uri))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                                .withHeader(HttpClientBaseTest.HEADER_CONTENT_TYPE, "text/xml")
                )
        );

        HttpRequest request =
                HttpRequestBuilder
                        .GET()
                        .uri(uri)
                        .header(HttpClientBaseTest.HEADER_ACCEPT, "text/xml")
                        .build();

        DefaultSSLSessionStrategy sslSessionStrategy = new DefaultSSLSessionStrategy();

        execute(HttpClientBaseTest.SECURED_HOST, request, Function.identity(), null, builder -> {
            try {
                builder.setSSLSessionStrategy(sslSessionStrategy);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }
}
