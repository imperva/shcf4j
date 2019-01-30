package com.imperva.shcf4j.request.multipart;

import com.imperva.shcf4j.HttpClientBaseTest;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpRequestBuilder;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.entity.ContentType;
import com.imperva.shcf4j.request.body.multipart.PartBuilder;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.aMultipart;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class MultipartRequestsTest extends HttpClientBaseTest {

    private String resourceUrl = "/hello";
    private String partName = "part-name";
    private String partBody = "hello";


    @Test
    public void stringPartRequestTest() {


        instanceRule.stubFor(any(urlPathEqualTo(resourceUrl))
                .withMultipartRequestBody(
                        aMultipart()
                                .withName(partName)
                                .withHeader("Content-Type", containing("charset"))
                                .withHeader("Content-Transfer-Encoding", equalTo("8bit"))
                                .withBody(equalTo(partBody))
                )
                .willReturn(aResponse()));

        HttpRequest request =
                HttpRequestBuilder
                        .POST(URI.create(resourceUrl))
                        .part(PartBuilder
                                .stringPart()
                                .name(partName)
                                .value(partBody)
                                .contentType(ContentType.createTextPlain())
                                .build())
                        .build();

        HttpResponse response = execute(HttpClientBaseTest.HOST, request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
    }

    @Test
    public void multipleStringPartRequestsTest() {
        instanceRule.stubFor(any(urlPathEqualTo(resourceUrl))
                .withMultipartRequestBody(
                        aMultipart()
                                .withName(partName)
                                .withHeader("Content-Type", containing("charset"))
                                .withBody(equalTo(partBody))
                )
                .withMultipartRequestBody(
                        aMultipart()
                                .withName(partName + '2')
                                .withHeader("Content-Type", containing("charset"))
                                .withBody(equalTo(partBody))
                )
                .willReturn(aResponse()));

        HttpRequest request =
                HttpRequestBuilder
                        .POST(URI.create(resourceUrl))
                        .part(PartBuilder
                                .stringPart()
                                .name(partName)
                                .value(partBody)
                                .contentType(ContentType.createTextPlain())
                                .build())
                        .part(PartBuilder
                                .stringPart()
                                .name(partName + '2')
                                .value(partBody)
                                .contentType(ContentType.createTextPlain())
                                .build())
                        .build();

        HttpResponse response = execute(HttpClientBaseTest.HOST, request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
    }

    @Test
    public void byteArrayPartTest() {
        instanceRule.stubFor(any(urlPathEqualTo(resourceUrl))
                .withMultipartRequestBody(
                        aMultipart()
                                .withName(partName)
                                .withHeader("Content-Type", containing("charset"))
                                .withHeader("Content-Transfer-Encoding", equalTo("binary"))
                                .withBody(equalTo(partBody))
                )
                .willReturn(aResponse()));

        HttpRequest request =
                HttpRequestBuilder
                        .POST(URI.create(resourceUrl))
                        .part(PartBuilder
                                .byteArrayPart()
                                .name(partName)
                                .bytes(partBody.getBytes(UTF_8))
                                .contentType(ContentType.createTextPlain())
                                .build())
                        .build();

        HttpResponse response = execute(HttpClientBaseTest.HOST, request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
    }

    @Test
    public void inputStreamPartTest() {
        instanceRule.stubFor(any(urlPathEqualTo(resourceUrl))
                .withMultipartRequestBody(
                        aMultipart()
                                .withName(partName)
                                .withHeader("Content-Type", equalTo("application/octet-stream"))
                                .withHeader("Content-Transfer-Encoding", equalTo("binary"))
                                .withBody(equalTo(partBody))
                )
                .willReturn(aResponse()));

        byte[] body = partBody.getBytes(UTF_8);

        HttpRequest request =
                HttpRequestBuilder
                        .POST(URI.create(resourceUrl))
                        .part(PartBuilder
                                .inputStreamPart()
                                .name(partName)
                                .inputStream(new ByteArrayInputStream(body))
                                .build())
                        .build();

        HttpResponse response = execute(HttpClientBaseTest.HOST, request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
    }


    @Test
    public void inputStreamPartWithContentTypeTest() {
        instanceRule.stubFor(any(urlPathEqualTo(resourceUrl))
                .withMultipartRequestBody(
                        aMultipart()
                                .withName(partName)
                                .withHeader("Content-Type", containing(ContentType.createTextPlain().getMimeType()))
                                .withHeader("Content-Transfer-Encoding", equalTo("binary"))
                                .withBody(equalTo(partBody))
                )
                .willReturn(aResponse()));

        byte[] body = partBody.getBytes(UTF_8);
        HttpRequest request =
                HttpRequestBuilder
                        .POST(URI.create(resourceUrl))
                        .part(PartBuilder
                                .inputStreamPart()
                                .name(partName)
                                .inputStream(new ByteArrayInputStream(body))
                                .contentType(ContentType.createTextPlain())
                                .build())
                        .build();

        HttpResponse response = execute(HttpClientBaseTest.HOST, request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
    }


    @Test
    public void filePartTest() throws Exception {
        instanceRule.stubFor(any(urlPathEqualTo(resourceUrl))
                .withMultipartRequestBody(
                        aMultipart()
                                .withName(partName)
                                .withHeader("Content-Type", equalTo("application/octet-stream"))
                                .withHeader("Content-Transfer-Encoding", equalTo("binary"))
                                .withBody(equalTo(partBody))
                )
                .willReturn(aResponse()));

        HttpRequest request =
                HttpRequestBuilder
                        .POST(URI.create(resourceUrl))
                        .part(PartBuilder
                                .filePart()
                                .name(partName)
                                .filePath(Paths.get(getClass().getClassLoader()
                                        .getResource("multipart/payload-file.tmp").toURI()))
                                .build())
                        .build();

        HttpResponse response = execute(HttpClientBaseTest.HOST, request);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
    }


    @Test
    public void customHeaderInMultipartRequestTest() {

        String customHeaderName1 = "X-My-Custom-Header-1";
        String customHeaderValue1 = "X-My-Custom-Header-Value-1";
        String customHeaderName2 = "X-My-Custom-Header-2";
        String customHeaderValue2 = "X-My-Custom-Header-Value-2";

        instanceRule.stubFor(any(urlPathEqualTo(resourceUrl))
                .withMultipartRequestBody(
                        aMultipart()
                                .withName(partName)
                                .withHeader("Content-Type", containing("charset"))
                                .withHeader(customHeaderName1, equalTo(customHeaderValue1))
                                .withBody(equalTo(partBody))
                )
                .withMultipartRequestBody(
                        aMultipart()
                                .withName(partName + '2')
                                .withHeader("Content-Type", containing("charset"))
                                .withHeader(customHeaderName2, equalTo(customHeaderValue2))
                                .withBody(equalTo(partBody))
                )
                .willReturn(aResponse()));

        HttpRequest request =
                HttpRequestBuilder
                        .POST(URI.create(resourceUrl))
                        .part(PartBuilder
                                .stringPart()
                                .name(partName)
                                .value(partBody)
                                .contentType(ContentType.createTextPlain())
                                .customHeader(customHeaderName1, customHeaderValue1)
                                .build())
                        .part(PartBuilder
                                .stringPart()
                                .name(partName + '2')
                                .value(partBody)
                                .contentType(ContentType.createTextPlain())
                                .customHeader(customHeaderName2, customHeaderValue2)
                                .build())
                        .build();

        HttpResponse response = execute(HttpClientBaseTest.HOST, request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
    }
}
