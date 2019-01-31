package com.imperva.shcf4j;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.Rule;

import java.net.URI;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public abstract class HttpClientBaseTest implements AbstractBasicTest {

    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";

    private static final int MOCK_PORT = 8089;
    private static final int MOCK_SSL_PORT = 8443;

    protected static final HttpHost HOST = HttpHost
            .builder()
            .hostname("localhost")
            .port(MOCK_PORT)
            .build();

    protected static final HttpHost SECURED_HOST = HttpHost
            .builder()
            .hostname("localhost")
            .port(MOCK_SSL_PORT)
            .schemeName("https")
            .build();


    @Rule
    public WireMockClassRule instanceRule =
            new WireMockClassRule(
                    wireMockConfig()
                            .port(MOCK_PORT)
                    .httpsPort(MOCK_SSL_PORT)
                    .keystorePath(Paths.get(
                            URI.create(HttpClientBaseTest.class.getClassLoader().getResource("wiremock-keystore.jks").toString())).toString())
            );

}
