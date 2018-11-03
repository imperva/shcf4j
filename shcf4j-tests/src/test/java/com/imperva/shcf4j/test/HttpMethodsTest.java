package com.imperva.shcf4j.test;

import com.imperva.shcf4j.HttpEntityEnclosingRequest;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.auth.AuthScope;
import com.imperva.shcf4j.auth.UsernamePasswordCredentials;
import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.protocol.ClientContext;
import com.imperva.shcf4j.entity.ContentType;
import com.imperva.shcf4j.entity.FileEntity;
import com.imperva.shcf4j.entity.StringEntity;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * <b>HttpMethodsTest</b>
 */
public abstract class HttpMethodsTest extends SyncHttpClientBaseTest {

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

        HttpRequest request = HttpRequest.createGetRequest(uri);
        request.addHeader(HttpClientBaseTest.HEADER_ACCEPT, "text/xml");

        Assert.assertTrue("Accept header is missing", request.containsHeader(HttpClientBaseTest.HEADER_ACCEPT));
        HttpResponse response = getHttpClient().execute(HttpClientBaseTest.HOST, request);

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

        HttpRequest request = HttpRequest.createGetRequest(uri);
        request.addHeader(HttpClientBaseTest.HEADER_ACCEPT, "text/xml");
        HttpResponse response = getHttpClient().
                execute(
                        HttpClientBaseTest.HOST,
                        request,
                        resp -> {
                            try (BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()))) {
                                Assert.assertEquals("Wrong content received", responseContent, rd.readLine());
                            } catch (IOException ioException) {
                                Assert.fail("Unexpected exception caught");
                            }
                            return null;
                        }
                );
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

        HttpEntityEnclosingRequest request = HttpRequest.createPostRequest(uri);
        HttpResponse response = getHttpClient().execute(HttpClientBaseTest.HOST, request);
        Assert
                .assertEquals("Wrong status code", HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
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

        HttpEntityEnclosingRequest request = HttpRequest.createPostRequest(uri);
        request.setEntity(StringEntity.builder().entity(entity).contentType(ContentType.createTextXML()).build());
        HttpResponse response = getHttpClient().execute(HttpClientBaseTest.HOST, request);
        Assert
                .assertEquals("Wrong status code", HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
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

        HttpEntityEnclosingRequest request = HttpRequest.createPostRequest(uri);
        request.setEntity(FileEntity.builder().path(f.toPath()).build());
        HttpResponse response = getHttpClient().execute(HttpClientBaseTest.HOST, request);
        Assert
                .assertEquals("Wrong status code", HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
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

        HttpRequest request = HttpRequest.createGetRequest(uri);
        CredentialsProvider cp = CredentialsProvider.createSystemDefaultCredentialsProvider();
        cp.setCredentials(
                AuthScope.createAnyAuthScope(),
                UsernamePasswordCredentials.builder().username(user).password(password).build()
        );

        ClientContext ctx = ClientContext.builder().credentialsProvider(cp).build();

        HttpResponse response = getHttpClient().execute(HttpClientBaseTest.HOST, request, ctx);
        Assert
                .assertEquals("Wrong status code", HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());

    }

}
