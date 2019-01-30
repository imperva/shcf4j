package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.MutableHttpRequest;
import com.imperva.shcf4j.request.body.multipart.Part;
import org.apache.http.HttpRequest;

import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;


/**
 * <b>HttpCommonsHttpRequestAdapter</b>
 *
 * <p>
 *     An adapter that used to 'unwrap' specific implementation and provide the original submitted request.
 *     Mainly used for request interceptors.
 * </p>
 *
 */
class HttpCommonsHttpRequestAdapter implements MutableHttpRequest {

    private final HttpRequest request;

    public HttpCommonsHttpRequestAdapter(HttpRequest request) {
        this.request = request;
    }

    @Override
    public void addHeader(String name, String value) {
        request.addHeader(name, value);
    }

    @Override
    public void addHeader(Header header) {
        addHeader(header.getName(), header.getValue());
    }

    @Override
    public void setHeader(String name, String value) {
        request.setHeader(name, value);
    }

    @Override
    public void setHeader(Header header) {
        setHeader(header.getName(), header.getValue());
    }

    @Override
    public SupportedHttpMethod getHttpMethod() {
        return SupportedHttpMethod.valueOf(request.getRequestLine().getMethod().toUpperCase());
    }

    @Override
    public URI getUri() {
        return URI.create(request.getRequestLine().getUri());
    }

    @Override
    public Path getFilePath() {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] getByteData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getStringData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ByteBuffer getByteBufferData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getInputStreamData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Charset getCharset() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Part> getParts() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsHeader(String name) {
        return request.containsHeader(name);
    }

    @Override
    public List<? extends Header> getHeaders(String name) {
        return ConversionUtils.convert(request.getHeaders(name));
    }

    @Override
    public List<? extends Header> getAllHeaders() {
        return ConversionUtils.convert(request.getAllHeaders());
    }
}
