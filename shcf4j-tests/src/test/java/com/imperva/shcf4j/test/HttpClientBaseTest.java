package com.imperva.shcf4j.test;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.client.protocol.ClientContext;
import org.junit.ClassRule;
import org.junit.Rule;

import java.io.IOException;
import java.util.function.Function;

public abstract class HttpClientBaseTest {

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


    protected final HttpResponse execute(HttpHost host, HttpRequest request) throws IOException {
        return execute(host, request, (ClientContext) null);
    }


    protected final HttpResponse execute(HttpHost host, HttpRequest request, ClientContext ctx) throws IOException {
        return execute(host, request, Function.identity(), ctx);
    }


    protected final <T> T execute(HttpHost host, HttpRequest request, Function<HttpResponse, T> callback) throws IOException {
        return execute(host, request, callback, null);
    }

    protected abstract  <T> T execute(HttpHost host, HttpRequest request, Function<HttpResponse, T> callback, ClientContext ctx) throws IOException;

}
