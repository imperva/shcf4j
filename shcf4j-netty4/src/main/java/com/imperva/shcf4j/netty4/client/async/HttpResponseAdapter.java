package com.imperva.shcf4j.netty4.client.async;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpEntity;
import com.imperva.shcf4j.HttpMessage;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.StatusLine;
import io.netty.handler.codec.http.HttpHeaderNames;
import org.asynchttpclient.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class HttpResponseAdapter implements HttpResponse {

    private final Response response;

    public HttpResponseAdapter(Response response) {
        this.response = response;
    }

    @Override
    public StatusLine getStatusLine() {
        return StatusLine
                .builder()
                .statusCode(response.getStatusCode())
                .reasonPhrase(response.getStatusText())
                .build();
    }

    @Override
    public HttpEntity getEntity() {
        return new DefaultHttpEntity();
    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public boolean containsHeader(String name) {
        return response.getHeader(name) != null;
    }

    @Override
    public List<? extends Header> getHeaders(String name) {
        return response
                .getHeaders(name)
                .stream()
                .map(v -> Header.builder().name(name).value(v).build())
                .collect(Collectors.toList());
    }

    @Override
    public List<? extends Header> getAllHeaders() {
        return StreamSupport
                .stream(response.getHeaders().spliterator(), false)
                .map(e -> Header.builder().name(e.getKey()).name(e.getValue()).build())
                .collect(Collectors.toList());
    }

    @Override
    public HttpMessage addHeader(String name, String value) {
        return null;
    }

    @Override
    public HttpMessage setHeader(String name, String value) {
        return null;
    }


    private class DefaultHttpEntity implements HttpEntity {

        @Override
        public boolean isRepeatable() {
            return false;
        }

        @Override
        public boolean isChunked() {
            return false;
        }

        @Override
        public long getContentLength() {
            String contentLength = response.getHeader(HttpHeaderNames.CONTENT_LENGTH);
            return contentLength != null ? Integer.parseInt(contentLength) : -1;
        }

        @Override
        public Header getContentType() {
            return Header
                    .builder()
                    .name(HttpHeaderNames.CONTENT_TYPE.toString())
                    .value(response.getContentType())
                    .build();
        }

        @Override
        public Header getContentEncoding() {
            String contentEncoding = response.getHeader(HttpHeaderNames.CONTENT_ENCODING);
            return contentEncoding != null ?
                    Header.builder().name(HttpHeaderNames.CONTENT_ENCODING.toString()).value(contentEncoding).build() :
                    null;
        }

        @Override
        public InputStream getContent() throws IllegalStateException {
            return response.getResponseBodyAsStream();
        }

        @Override
        public void writeTo(OutputStream outputStream) throws IOException {

        }

        @Override
        public boolean isStreaming() {
            return false;
        }
    }
}
