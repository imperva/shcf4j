package com.imperva.shcf4j;

import com.imperva.shcf4j.request.body.multipart.Part;
import lombok.AccessLevel;
import lombok.Getter;

import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Getter
class HttpRequestImpl implements HttpRequest {

    @Getter(AccessLevel.NONE)
    private final List<Header> headers;
    private final SupportedHttpMethod httpMethod;
    private final URI uri;
    private final Path filePath;
    private final byte[] byteData;
    private final String stringData;
    private final ByteBuffer byteBufferData;
    private final InputStream inputStreamData;
    private final Charset charset;
    private final List<Part> parts;


    protected HttpRequestImpl(HttpRequestBuilder builder){
        this.headers = builder.headers;
        this.httpMethod = builder.httpMethod;
        this.uri = builder.uri;
        this.filePath = builder.filePath;
        this.byteData = builder.byteData;
        this.stringData = builder.stringData;
        this.byteBufferData = builder.byteBufferData;
        this.inputStreamData = builder.inputStreamData;
        this.charset = builder.charset;
        this.parts = builder.parts;
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

}
