package com.imperva.shcf4j.httpcomponents.client4.mime;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpEntity;
import com.imperva.shcf4j.ProcessingException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;


/**
 * <b>MultipartEntity</b>
 *
 * @author maxim.kirilov
 */
class MultipartEntity implements HttpEntity {

    private final org.apache.http.HttpEntity entity;
    private final com.imperva.shcf4j.Header contentType;
    private final com.imperva.shcf4j.Header contentEncoding;

    MultipartEntity(org.apache.http.HttpEntity entity) {
        this.entity = entity;
        this.contentType = Header
                .builder()
                .name(entity.getContentType().getName())
                .value(entity.getContentType().getValue())
                .build();
        this.contentEncoding = Header
                .builder()
                .name(entity.getContentEncoding().getName())
                .value(entity.getContentEncoding().getValue())
                .build();
    }

    @Override
    public boolean isRepeatable() {
        return entity.isRepeatable();
    }

    @Override
    public boolean isChunked() {
        return entity.isChunked();
    }

    @Override
    public long getContentLength() {
        return entity.getContentLength();
    }

    @Override
    public Header getContentType() {
        return this.contentType;
    }

    @Override
    public Header getContentEncoding() {
        return this.contentEncoding;
    }

    @Override
    public InputStream getContent() {
        try {
            return entity.getContent();
        } catch (Throwable t) {
            throw new ProcessingException(t);
        }

    }

    @Override
    public boolean isStreaming() {
        return entity.isStreaming();
    }

}