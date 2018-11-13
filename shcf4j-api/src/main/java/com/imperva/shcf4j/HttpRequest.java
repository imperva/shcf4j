package com.imperva.shcf4j;


import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Builder
@Getter
@ToString(includeFieldNames=false, of= {"httpMethod", "uri"})
public class HttpRequest implements HttpMessage {

    public enum SupportedHttpMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

    @Singular
    private List<Header> headers;
    private final SupportedHttpMethod httpMethod;
    private final URI uri;
    private final Path filePath;
    private final byte[] byteData;
    private final String stringData;
    private final ByteBuffer byteBufferData;
    private final InputStream inputStreamData;
    private final Charset charset;



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


    public static class HttpRequestBuilder {

        public HttpRequestBuilder getRequest(){
            this.httpMethod = SupportedHttpMethod.GET;
            return this;
        }

        public HttpRequestBuilder postRequest(){
            this.httpMethod = SupportedHttpMethod.POST;
            return this;
        }

        public HttpRequestBuilder putRequest(){
            this.httpMethod = SupportedHttpMethod.PUT;
            return this;
        }

        public HttpRequestBuilder deleteRequest(){
            this.httpMethod = SupportedHttpMethod.DELETE;
            return this;
        }

        public HttpRequestBuilder uri(String uri){
            this.uri = URI.create(uri);
            return this;
        }

        public HttpRequestBuilder uri(URI uri){
            this.uri = uri;
            return this;
        }

        public HttpRequestBuilder rawHeaders(Map<String, String> rawHeaders){
            Objects.requireNonNull(rawHeaders, "rawHeaders");
            return headers(rawHeaders
                    .entrySet()
                    .stream()
                    .map(e -> Header
                            .builder()
                            .name(e.getKey())
                            .value(e.getValue())
                            .build())
                    .collect(Collectors.toSet()));
        }

        public HttpRequestBuilder rawHeader(String name, String value){
            return header(Header.builder().name(name).value(value).build());
        }

    }


}
