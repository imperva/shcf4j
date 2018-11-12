package com.imperva.shcf4j.test;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.client.protocol.ClientContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

/**
 */
public abstract class AsyncHttpMethodsTest extends AsyncHttpClientBaseTest {

    protected AsyncHttpClient asyncHttpClient;

    private static final long TEST_TMOUT = 10000L;


    @Before
    public void setup() {
        asyncHttpClient = getHttpClient();
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
    public void getMethodTest() throws Exception {
        String uri = "/my/resource";
        instanceRule.stubFor(get(urlEqualTo(uri))
                .withHeader(HEADER_ACCEPT, equalTo("text/xml"))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                                .withHeader(HEADER_CONTENT_TYPE, "text/xml")
                )
        );

        HttpRequest request =
                HttpRequest
                        .builder()
                        .getRequest()
                        .uri(uri)
                        .header(Header.builder().name(HttpClientBaseTest.HEADER_ACCEPT).value("text/xml").build())
                        .build();
        Assert.assertTrue("Accept header is missing", request.containsHeader(HEADER_ACCEPT));

        HttpResponse response = getHttpClient()
                .execute(HOST, request)
                .get();


        Assert.assertEquals("Response code is wrong",
                HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
        Assert.assertEquals("Content Header is wrong",
                "text/xml", response.getHeaders(HEADER_CONTENT_TYPE).get(0).getValue());
    }

    @Test(timeout = TEST_TMOUT, expected = Throwable.class)
    public void failedCallbackTest() throws Exception {
        String uri = "/delayed";
        instanceRule.stubFor(get(urlEqualTo(uri))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                                .withFixedDelay(60 * 1000)
                )
        );
        HttpRequest request =
                HttpRequest
                        .builder()
                        .getRequest()
                        .uri(uri)
                        .build();

        ClientContext ctx = ClientContext
                .builder()
                .requestConfig(
                        RequestConfig
                                .builder()
                                .socketTimeoutMilliseconds(500)
                                .build())
                .build();

        getHttpClient().execute(HOST, request, ctx).get();
    }



    @Test
    public void emptyEntityPostTest() throws Exception {
        String uri = "/resource";
        instanceRule.stubFor(post(urlEqualTo(uri))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                )
        );

        HttpResponse response = getHttpClient()
                .execute(HttpClientBaseTest.HOST, HttpRequest.builder().postRequest().uri(uri).build())
                .get();


        Assert.assertEquals("Wrong status code", 200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void fileEntityPostTest() throws Exception {
        File f = File.createTempFile("fileEntity", ".tmp");
        f.deleteOnExit();
        String fileContent = "fileContent";
        Writer w = new PrintWriter(f, "UTF-8");
        w.write(fileContent);
        w.close();
        String uri = "/my/entity";
        instanceRule.stubFor(post(urlEqualTo(uri))
                .withRequestBody(matching(fileContent))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                )
        );

        HttpResponse response =
                getHttpClient()
                        .execute(HttpClientBaseTest.HOST,
                                HttpRequest
                                        .builder()
                                        .postRequest()
                                        .uri(uri)
                                        .filePath(f.toPath())
                                        .build()).get();
        Assert.assertEquals("Wrong status code", HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
    }


}
