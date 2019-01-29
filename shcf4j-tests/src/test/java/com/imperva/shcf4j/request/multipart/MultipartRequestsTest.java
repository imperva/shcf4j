package com.imperva.shcf4j.request.multipart;

import com.imperva.shcf4j.HttpClientBaseTest;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpRequestBuilder;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.entity.ContentType;
import com.imperva.shcf4j.request.body.multipart.PartBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.aMultipart;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

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

        Assert.assertEquals("Response code is wrong",
                HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
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

        Assert.assertEquals("Response code is wrong",
                HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
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
                                .bytes(partBody.getBytes())
                                .contentType(ContentType.createTextPlain())
                                .build())
                        .build();

        HttpResponse response = execute(HttpClientBaseTest.HOST, request);

        Assert.assertEquals("Response code is wrong",
                HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
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

        HttpRequest request =
                HttpRequestBuilder
                        .POST(URI.create(resourceUrl))
                        .part(PartBuilder
                                .inputStreamPart()
                                .name(partName)
                                .inputStream(new ByteArrayInputStream( partBody.getBytes() ))
                                .build())
                        .build();

        HttpResponse response = execute(HttpClientBaseTest.HOST, request);

        Assert.assertEquals("Response code is wrong",
                HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
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

        HttpRequest request =
                HttpRequestBuilder
                        .POST(URI.create(resourceUrl))
                        .part(PartBuilder
                                .inputStreamPart()
                                .name(partName)
                                .inputStream(new ByteArrayInputStream( partBody.getBytes() ))
                                .contentType(ContentType.createTextPlain())
                                .build())
                        .build();

        HttpResponse response = execute(HttpClientBaseTest.HOST, request);

        Assert.assertEquals("Response code is wrong",
                HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());
    }
}
