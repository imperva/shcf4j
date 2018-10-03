package com.imperva.shcf4j.httpcomponents.client.impl;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class HttpEntityAdapter implements HttpEntity {

    private final com.imperva.shcf4j.HttpEntity entity;

    public HttpEntityAdapter(com.imperva.shcf4j.HttpEntity entity) {
        this.entity = entity;
    }

    public boolean isRepeatable() {
        return this.entity != null && this.entity.isRepeatable();
    }

    @Override
    public boolean isChunked() {
        return this.entity != null && this.entity.isChunked();
    }

    @Override
    public long getContentLength() {
        return this.entity != null ? this.entity.getContentLength(): 0;
    }

    @Override
    public Header getContentType() {
        com.imperva.shcf4j.Header header = this.entity != null ? this.entity.getContentType() : null;
        return header != null ? new BasicHeader(header.getName(), header.getValue()) : null;
    }

    @Override
    public Header getContentEncoding() {
        com.imperva.shcf4j.Header header = this.entity != null ? this.entity.getContentEncoding() : null;
        return header != null ? new BasicHeader(header.getName(), header.getValue()) : null;
    }

    @Override
    public InputStream getContent() throws IOException, IllegalStateException {
        return this.entity.getContent();
    }

    @Override
    public void writeTo(OutputStream outstream) throws IOException {
        if (this.entity != null) {
            this.entity.writeTo(outstream);
        }
    }

    @Override
    public boolean isStreaming() {
        return this.entity.isStreaming();
    }

    @Override
    public void consumeContent() throws IOException {

    }
}
