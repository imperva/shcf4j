package com.imperva.shcf4j;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.ClassRule;
import org.junit.Rule;

public abstract class HttpClientBaseTest implements AbstractBasicTest {

    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";

    private static final int MOCK_PORT = 8089;
    protected static final HttpHost HOST = HttpHost
            .builder()
            .hostname("localhost")
            .port(MOCK_PORT)
            .build();

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(MOCK_PORT);

    @Rule
    public WireMockClassRule instanceRule = wireMockRule;

}
