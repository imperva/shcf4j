package com.imperva.shcf4j.httpcomponents.client4.mime;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


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
    public InputStream getContent() throws IOException, IllegalStateException {
        return entity.getContent();
    }

    @Override
    public void writeTo(OutputStream outstream) throws IOException {
        entity.writeTo(outstream);
    }

    @Override
    public boolean isStreaming() {
        return entity.isStreaming();
    }

    @Deprecated
    public void consumeContent() throws IOException {
        entity.consumeContent();
    }

}