package com.imperva.shcf4j.httpcomponents.client;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.imperva.shcf4j.HttpHost;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * <b>HttpRequestTest</b>
 *
 * @author <font color="blue">Maxim Kirilov</font>
 *         Date: April 2014
 */
public class HttpClientBaseTest {

    static {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "DEBUG");
    }

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
