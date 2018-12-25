package com.imperva.shcf4j;

import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;


class HttpRequestImpl implements HttpRequest {

    private final List<Header> headers;
    private final SupportedHttpMethod httpMethod;
    private final URI uri;
    private final Path filePath;
    private final byte[] byteData;
    private final String stringData;
    private final ByteBuffer byteBufferData;
    private final InputStream inputStreamData;
    private final Charset charset;


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

    public SupportedHttpMethod getHttpMethod() {
        return httpMethod;
    }

    public URI getUri() {
        return uri;
    }

    public Path getFilePath() {
        return filePath;
    }

    public byte[] getByteData() {
        return byteData;
    }

    public String getStringData() {
        return stringData;
    }

    public ByteBuffer getByteBufferData() {
        return byteBufferData;
    }

    public InputStream getInputStreamData() {
        return inputStreamData;
    }

    public Charset getCharset() {
        return charset;
    }
}
