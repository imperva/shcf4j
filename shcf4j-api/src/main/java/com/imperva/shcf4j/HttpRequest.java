package com.imperva.shcf4j;


import lombok.ToString;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


@ToString(includeFieldNames=false, of= {"method", "uri"})
public class HttpRequest implements HttpMessage {

    public enum SupportedHttpMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

    private List<Header> headers = new LinkedList<>();
    private final SupportedHttpMethod method;
    private final URI uri;

    public HttpRequest(URI uri, SupportedHttpMethod method) {
        this.uri = uri;
        this.method = method;
    }


    public URI getUri() {
        return uri;
    }

    public SupportedHttpMethod getMethod() {
        return method;
    }

    @Override
    public boolean containsHeader(String headerName) {
        return headers.stream().anyMatch(h -> h.getName().equals(headerName));
    }

    @Override
    public List<? extends Header> getHeaders(String headerName) {
        return headers
                .stream()
                .filter(h -> h.getName().equals(headerName))
                .collect(Collectors.toList());
    }

    @Override
    public List<? extends Header> getAllHeaders() {
        return headers;
    }

    @Override
    public HttpMessage addHeader(String name, String value) {
        headers.add(Header.builder().name(name).value(value).build());
        return this;
    }

    @Override
    public HttpMessage setHeader(String name, String value) {
        List<? extends Header> lHeaders = getHeaders(name);
        if (! lHeaders.isEmpty() ) {
            Header h = getHeaders(name).get(0);
            if (headers.remove(h)) {
                headers.add(Header.builder().name(name).value(value).build());
            }
        } else {
            addHeader(name, value);
        }
        return this;
    }

    /**
     *
     * @param uri the request URI
     * @return {@code HttpRequest}
     */
    public static HttpRequest createGetRequest(URI uri) {
        return new HttpRequest(uri, SupportedHttpMethod.GET);
    }


    /**
     *
     * @param uri the request URI
     * @return {@code HttpRequest}
     */
    public static HttpRequest createGetRequest(String uri) {
        return createGetRequest(URI.create(uri));
    }

    /**
     * @param uri the request URI
     * @return {@code HttpEntityEnclosingRequest}
     */
    public static HttpEntityEnclosingRequest createPostRequest(URI uri) {
        return new HttpEntityEnclosingRequest(uri, SupportedHttpMethod.POST);
    }

    /**
     * @param uri the request URI
     * @return {@code HttpEntityEnclosingRequest}
     */
    public static HttpEntityEnclosingRequest createPostRequest(String uri) {
        return createPostRequest(URI.create(uri));
    }

    /**
     * @param uri the request URI
     * @return {@code HttpEntityEnclosingRequest}
     */
    public static HttpEntityEnclosingRequest createPutRequest(URI uri) {
        return new HttpEntityEnclosingRequest(uri, SupportedHttpMethod.PUT);
    }

    /**
     * @param uri the request URI
     * @return {@code HttpEntityEnclosingRequest}
     */
    public static HttpEntityEnclosingRequest createPutRequest(String uri) {
        return createPutRequest(URI.create(uri));
    }

    public static HttpRequest createDeleteRequest(URI uri) {
        return new HttpRequest(uri, SupportedHttpMethod.DELETE);
    }

    public static HttpRequest createDeleteRequest(String uri) {
        return new HttpRequest(URI.create(uri), SupportedHttpMethod.DELETE);
    }



}
