package com.imperva.shcf4j.java.http.client;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpEntity;
import com.imperva.shcf4j.HttpHeaderNames;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.ProcessingException;
import com.imperva.shcf4j.ProtocolVersion;
import com.imperva.shcf4j.StatusLine;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

class HttpResponseImpl implements HttpResponse {

    private final java.net.http.HttpResponse<InputStream> response;

    private final HttpEntity httpEntity;

    HttpResponseImpl(java.net.http.HttpResponse<InputStream> response) {
        this.response = response;
        this.httpEntity = new HttpEntityImpl();
    }


    @Override
    public StatusLine getStatusLine() {
        ProtocolVersion pv;
        switch (response.version()) {
            case HTTP_1_1:
                pv = ProtocolVersion.builder().protocol("HTTP").major(1).minor(1).build();
                break;
            case HTTP_2:
                pv = ProtocolVersion.builder().protocol("HTTP").major(2).minor(0).build();
                break;
            default:
                throw new RuntimeException("Not supported HTTP protocol: " + response.version());
        }

        return StatusLine
                .builder()
                .statusCode(response.statusCode())
                .protocolVersion(pv)
                .build();
    }

    @Override
    public HttpEntity getEntity() {
        return this.httpEntity;
    }

    @Override
    public boolean containsHeader(String name) {
        return response.headers().firstValue(name).isPresent();
    }

    @Override
    public List<? extends Header> getHeaders(String name) {
        return response
                .headers()
                .allValues(name)
                .stream()
                .map(v -> Header.builder().name(name).value(v).build())
                .collect(Collectors.toList());
    }

    @Override
    public List<? extends Header> getAllHeaders() {

        return response
                .headers()
                .map()
                .keySet()
                .stream()
                .map(this::getHeaders)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    class HttpEntityImpl implements HttpEntity {


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
            return response
                    .headers()
                    .firstValue(HttpHeaderNames.CONTENT_LENGTH)
                    .map(Integer::parseInt)
                    .orElse(-1);
        }

        @Override
        public Header getContentType() {
            return response
                    .headers()
                    .firstValue(HttpHeaderNames.CONTENT_TYPE)
                    .map(v -> Header.builder().name(HttpHeaderNames.CONTENT_TYPE).value(v).build())
                    .orElse(null);
        }

        @Override
        public Header getContentEncoding() {
            return response
                    .headers()
                    .firstValue(HttpHeaderNames.CONTENT_ENCODING)
                    .map(v -> Header.builder().name(HttpHeaderNames.CONTENT_ENCODING).value(v).build())
                    .orElse(null);
        }

        @Override
        public InputStream getContent() {
            try {
                return response.body();
            } catch (Throwable t) {
                throw new ProcessingException(t);
            }
        }


        @Override
        public boolean isStreaming() {
            return false;
        }
    }
}
