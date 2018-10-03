package com.imperva.shcf4j.httpcomponents.client4;

import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * <b>URIBuilderTest</b>
 *
 * @author <font color="blue">Maxim Kirilov</font>
 *         Date: 4/10/14
 *         Time: 7:59 AM
 */
public class URIBuilderTest {


    @Test(expected = URISyntaxException.class)
    public void illegalUriTest() throws Exception {
        String illegalUri = "example.com/file[/].html";
        URIBuilder.valueOf(illegalUri).build();
    }

    @Test
    public void legalUriTest() throws Exception {
        String[] uris = {
                "http://example.org/absolute/URI/with/absolute/path/to/resource.txt",
                "ftp://example.org/resource.txt",
                "urn:issn:1535-3613",
                "/relative/URI/with/absolute/path/to/resource.txt",
                "../../../resource.txt",
                "./resource.txt#frag01",
                "resource.txt",
                "#frag01"
        };

        for (String uri : uris) {
            URIBuilder.valueOf(uri).build();
        }
    }


    @Test
    public void uriParamsTest() throws Exception {
        String uriString = "http://server/program/path/";
        String query = "field1=value1";
        URI uri = URIBuilder
                .valueOf(uriString)
                .setParameter("field1", "value1")
                .build();
        Assert.assertEquals("The query parameters wasn't parsed correctly",
                query, uri.getQuery());
        Assert.assertEquals("The builder failed to create correct URI",
                new URI(uriString + "?" + query), uri);

    }


    @Test
    public void setHostTest() throws Exception {

        URI uri = URIBuilder
                .emptyInstance()
                .setHost("localhost")
                .build();

        Assert.assertEquals("The host wasn't parsed correctly", "localhost", uri.getHost());
    }

    @Test
    public void setPortTest() throws Exception {
        URI uri = URIBuilder
                .emptyInstance()
                .setHost("localhost")
                .setPort(8083)
                .build();

        Assert.assertEquals("The port wasn't parsed correctly", 8083, uri.getPort());
    }

    @Test
    public void setPathTest() throws Exception {
        URI uri = URIBuilder
                .emptyInstance()
                .setHost("localhost")
                .setPort(8083)
                .setPath("/program/path/")
                .build();
        Assert.assertEquals("The path wasn't parsed correctly", "/program/path/", uri.getPath());
    }

}
