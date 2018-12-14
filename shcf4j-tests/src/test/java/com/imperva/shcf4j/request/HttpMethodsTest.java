package com.imperva.shcf4j.request;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpRequestBuilder;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.auth.AuthScope;
import com.imperva.shcf4j.auth.UsernamePasswordCredentials;
import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.protocol.ClientContext;
import com.imperva.shcf4j.HttpClientBaseTest;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

/**
 * <b>HttpMethodsTest</b>
 */
public abstract class HttpMethodsTest extends HttpClientBaseTest {

    @Test
    public void simpleGetTest() throws IOException {
        String uri = "/my/resource";
        instanceRule.stubFor(get(urlEqualTo(uri))
                .withHeader(HttpClientBaseTest.HEADER_ACCEPT, equalTo("text/xml"))
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

        Assert.assertTrue("Accept header is missing", request.containsHeader(HttpClientBaseTest.HEADER_ACCEPT));
        HttpResponse response = execute(HttpClientBaseTest.HOST, request);

        Assert.assertEquals("Response code is wrong",
                HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());

        Assert.assertEquals("Content Header is wrong",
                "text/xml", response.getHeaders(HttpClientBaseTest.HEADER_CONTENT_TYPE).get(0).getValue());

    }


    @Test
    public void simpleEntityGetTest() throws IOException {
        String uri = "/my/entity";
        final String responseContent = "My content";
        instanceRule.stubFor(get(urlEqualTo(uri))
                .withHeader(HttpClientBaseTest.HEADER_ACCEPT, equalTo("text/xml"))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                                .withHeader(HttpClientBaseTest.HEADER_CONTENT_TYPE, "text/xml")
                                .withBody(responseContent)
                )
        );

        HttpRequest request =
                HttpRequestBuilder
                        .GET()
                        .uri(uri)
                        .header(Header.builder().name(HttpClientBaseTest.HEADER_ACCEPT).value("text/xml").build())
                        .build();

        boolean isEquals = execute(HttpClientBaseTest.HOST, request, response -> {
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                return responseContent.equals(rd.readLine());
            } catch (IOException ioException) {
                return false;
            }
        });

        Assert.assertTrue("Wrong content received", isEquals);

    }


    @Test
    public void emptyEntityPostTest() throws IOException {
        String uri = "/resource";
        instanceRule.stubFor(post(urlEqualTo(uri))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                )
        );

        HttpResponse response = execute(HttpClientBaseTest.HOST, HttpRequestBuilder.POST().uri(uri).build());
        Assert.assertEquals("Wrong status code", HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
    }


    @Test
    public void stringEntityPostTest() throws IOException {
        String uri = "/my/entity";
        String entity = "String Entity";
        instanceRule.stubFor(post(urlEqualTo(uri))
                .withRequestBody(matching(entity))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                )
        );

        HttpResponse response = execute(HttpClientBaseTest.HOST,
                HttpRequestBuilder
                        .POST()
                        .uri(uri)
                        .stringData(entity)
                        .build());
        Assert.assertEquals("Wrong status code", HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
    }


    @Test
    public void fileEntityPostTest() throws IOException {
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

        HttpResponse response = execute(HttpClientBaseTest.HOST,
                HttpRequestBuilder
                        .POST()
                        .uri(uri)
                        .filePath(f.toPath())
                        .build()
        );
        Assert.assertEquals("Wrong status code", HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void basicAuthenticationTest() throws IOException {
        String uri = "/basic/auth";

        String user = "user";
        String password = "password";


        instanceRule.stubFor(get(urlEqualTo(uri))
                .willReturn(aResponse()
                        .withStatus(401)
                        .withHeader("WWW-Authenticate", "Basic realm=localhost")
                ));

        instanceRule.stubFor(get(urlEqualTo(uri))
                .withBasicAuth(user, password)
                .willReturn(aResponse()
                        .withStatus(200)
                ));

        HttpRequest request = HttpRequestBuilder.GET(URI.create(uri)).build();
        CredentialsProvider cp = CredentialsProvider
                .builder()
                .credential(AuthScope.createAnyAuthScope(),
                    UsernamePasswordCredentials.builder().username(user).password(password).build())
                .build();

        ClientContext ctx = ClientContext.builder().credentialsProvider(cp).build();

        HttpResponse response = execute(HttpClientBaseTest.HOST, request, ctx);
        Assert.assertEquals("Wrong status code", HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());

    }


    @Test
    public void queryParametersTest() throws Exception {
        String uri = "/my/resource?queryParameter=someValue";
        instanceRule.stubFor(get(urlEqualTo(uri))
                .withQueryParam("queryParameter", equalTo("someValue"))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                )
        );

        HttpRequest request = HttpRequestBuilder.GET().uri(uri).build();
        HttpResponse response = execute(HttpClientBaseTest.HOST, request);
        Assert.assertEquals("Wrong status code", HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());


    }


    @Test
    public void encodedQueryParametersTest() {

    }

    @Test
    public void nonEncodedQueryParametersTest() throws Exception {
        String uri = "/my/resource?queryParameter=someValue/slash/slash";
        instanceRule.stubFor(get(urlEqualTo(uri))
                .withQueryParam("queryParameter", equalTo("someValue/slash/slash"))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                )
        );

        HttpRequest request = HttpRequestBuilder.GET().uri(uri).build();
        HttpResponse response = execute(HttpClientBaseTest.HOST, request);
        Assert.assertEquals("Wrong status code", HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
    }


}
