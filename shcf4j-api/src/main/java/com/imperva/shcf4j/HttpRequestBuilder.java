package com.imperva.shcf4j;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public class HttpRequestBuilder {

    protected List<Header> headers = new LinkedList<>();
    protected HttpRequest.SupportedHttpMethod httpMethod;
    protected URI uri = URI.create("http://localhost"); //Default value
    protected Path filePath;
    protected byte[] byteData;
    protected String stringData;
    protected ByteBuffer byteBufferData;
    protected InputStream inputStreamData;
    protected Charset charset = Charset.forName("UTF-8");
    // keep parameters order
    protected Map<String, String> queryParams = new LinkedHashMap<>();

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

    public HttpRequestBuilder queryParam(String name, String value){
        try {
            this.queryParams.put(name, URLEncoder.encode(value, this.charset.displayName()));
        } catch (UnsupportedEncodingException e){
            // Impossible to happen, since we pass a name of a charset that was already exists
            // Java 10 have an overload that gets the charset itself
        }
        return this;
    }

    public HttpRequest build() {
        appendQueryParameterToUri();
        return new HttpRequestImpl( this);
    }


    private void appendQueryParameterToUri(){
        if (this.queryParams.isEmpty()){
            return;
        }
        String query = this.uri.getQuery();
        if (query == null){
            query = getQueryParametersAsString(this.queryParams);
        } else {
            query += '&' + getQueryParametersAsString(this.queryParams);
        }
        try {
            this.uri = new URI(this.uri.getScheme(), this.uri.getAuthority(), this.uri.getPath(), query, this.uri.getFragment());
        } catch (URISyntaxException e){
            throw new RuntimeException(e);
        }
    }

    private String getQueryParametersAsString(Map<String, String> queryParams){
        return queryParams
                .entrySet()
                .stream()
                .map(e -> e.getKey() + '=' + e.getValue())
                .collect(Collectors.joining("&"));
    }
}
