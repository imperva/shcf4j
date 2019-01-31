package com.imperva.shcf4j.ahc2.client.async;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.MutableHttpRequest;
import com.imperva.shcf4j.request.body.multipart.Part;
import org.asynchttpclient.Request;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <b>AhcHttpRequestAdapter</b>
 *
 * <p>
 * Adapts {@link org.asynchttpclient.Request} to {@link com.imperva.shcf4j.MutableHttpRequest}
 * </p>
 *
 * @author maxim.kirilov
 */
public class AhcHttpRequestAdapter implements MutableHttpRequest {

    private final Request request;

    public AhcHttpRequestAdapter(Request request) {
        this.request = request;
    }

    @Override
    public void addHeader(String name, String value) {
        request.getHeaders().add(name, value);
    }

    @Override
    public void addHeader(Header header) {
        addHeader(header.getName(), header.getValue());
    }

    @Override
    public void setHeader(String name, String value) {
        request.getHeaders().set(name, value);
    }

    @Override
    public void setHeader(Header header) {
        setHeader(header.getName(), header.getValue());
    }

    @Override
    public SupportedHttpMethod getHttpMethod() {
        return SupportedHttpMethod.valueOf(request.getMethod().toUpperCase());
    }

    @Override
    public URI getUri() {
        try {
            return request.getUri().toJavaNetURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
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
        return request.getCharset();
    }

    @Override
    public List<Part> getParts() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsHeader(String name) {
        return request.getHeaders().contains(name);
    }

    @Override
    public List<? extends Header> getHeaders(String name) {

        return request
                .getHeaders().getAll(name)
                .stream()
                .map(v -> Header.builder().name(name).value(v).build())
                .collect(Collectors.toList());

    }

    @Override
    public List<? extends Header> getAllHeaders() {
        return ConversionUtils.convert(request.getHeaders());
    }
}
