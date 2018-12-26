package com.imperva.shcf4j.ahc2.client.async;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpEntity;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.ProcessingException;
import com.imperva.shcf4j.StatusLine;
import io.netty.handler.codec.http.HttpHeaderNames;
import org.asynchttpclient.Response;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

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
        return ConversionUtils.convert(response.getHeaders());
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
        public InputStream getContent() {
            try {
                return response.getResponseBodyAsStream();
            } catch (Throwable t){
                throw new ProcessingException(t);
            }
        }

        @Override
        public String getResponseBody(Charset charset) {
            try {
                return response.getResponseBody(charset);
            } catch (Throwable t){
                throw new ProcessingException(t);
            }
        }


        @Override
        public boolean isStreaming() {
            return false;
        }
    }
}
