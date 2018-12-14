package com.imperva.shcf4j;

import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public class HttpRequestBuilder {

    protected List<Header> headers = new LinkedList<>();
    protected HttpRequest.SupportedHttpMethod httpMethod;
    protected URI uri;
    protected Path filePath;
    protected byte[] byteData;
    protected String stringData;
    protected ByteBuffer byteBufferData;
    protected InputStream inputStreamData;
    protected Charset charset;

    private HttpRequestBuilder() { }

    private HttpRequestBuilder(HttpRequest.SupportedHttpMethod httpMethod){
        this.httpMethod = httpMethod;
    }

    private HttpRequestBuilder(HttpRequest.SupportedHttpMethod httpMethod, URI uri){
        this.httpMethod = httpMethod;
        this.uri = uri;
    }

    public static HttpRequestBuilder GET() {
        return new HttpRequestBuilder(HttpRequest.SupportedHttpMethod.GET);
    }

    public static HttpRequestBuilder GET(URI uri) {
        return new HttpRequestBuilder(HttpRequest.SupportedHttpMethod.GET, uri);
    }

    public static HttpRequestBuilder POST() {
        return new HttpRequestBuilder(HttpRequest.SupportedHttpMethod.POST);
    }

    public static HttpRequestBuilder POST(URI uri) {
        return new HttpRequestBuilder(HttpRequest.SupportedHttpMethod.POST, uri);
    }


    public static HttpRequestBuilder PUT() {
        return new HttpRequestBuilder(HttpRequest.SupportedHttpMethod.PUT);
    }

    public static HttpRequestBuilder PUT(URI uri) {
        return new HttpRequestBuilder(HttpRequest.SupportedHttpMethod.PUT, uri);
    }

    public static HttpRequestBuilder DELETE() {
        return new HttpRequestBuilder(HttpRequest.SupportedHttpMethod.DELETE);
    }

    public static HttpRequestBuilder DELETE(URI uri) {
        return new HttpRequestBuilder(HttpRequest.SupportedHttpMethod.DELETE, uri);
    }




    public HttpRequestBuilder uri(String uri) {
        this.uri = URI.create(uri);
        return this;
    }

    public HttpRequestBuilder uri(URI uri) {
        this.uri = uri;
        return this;
    }

    public HttpRequestBuilder headers(Map<String, String> rawHeaders) {
        Objects.requireNonNull(rawHeaders, "rawHeaders");
        return headers(rawHeaders.entrySet().stream().map(e ->
            Header.builder().name(e.getKey()).value(e.getValue()).build()
        ).collect(Collectors.toSet()));
    }

    public HttpRequestBuilder header(String name, String value) {
        return this.header(Header.builder().name(name).value(value).build());
    }



    public HttpRequestBuilder header(Header header) {
        this.headers.add(header);
        return this;
    }

    public HttpRequestBuilder headers(Collection<? extends Header> headers) {
        this.headers.addAll(headers);
        return this;
    }

    public HttpRequestBuilder clearHeaders() {
        this.headers.clear();
        return this;
    }


    public HttpRequestBuilder filePath(Path filePath) {
        this.filePath = filePath;
        return this;
    }

    public HttpRequestBuilder byteData(byte[] byteData) {
        this.byteData = Arrays.copyOf(byteData, byteData.length);
        return this;
    }

    public HttpRequestBuilder stringData(String stringData) {
        this.stringData = stringData;
        return this;
    }

    public HttpRequestBuilder byteBufferData(ByteBuffer byteBufferData) {
        this.byteBufferData = byteBufferData;
        return this;
    }

    public HttpRequestBuilder inputStreamData(InputStream inputStreamData) {
        this.inputStreamData = inputStreamData;
        return this;
    }

    public HttpRequestBuilder charset(Charset charset) {
        this.charset = charset;
        return this;
    }

    public HttpRequest build() {
        return new HttpRequestImpl( this);
    }
}
