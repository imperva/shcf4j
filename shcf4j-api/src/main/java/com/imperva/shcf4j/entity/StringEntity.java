package com.imperva.shcf4j.entity;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpEntity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

@Builder
@Value
public class StringEntity implements HttpEntity {

    @NonNull
    private final String entity;

    @Builder.Default
    private final ContentType contentType = ContentType.createTextPlain();


    @Override
    public boolean isRepeatable() {
        return true;
    }

    @Override
    public boolean isChunked() {
        return false;
    }

    @Override
    public long getContentLength() {
        return getEntity().getBytes().length;
    }

    @Override
    public Header getContentType() {
        return contentType != null ?
                Header.builder().name(HttpEntity.CONTENT_TYPE).value(contentType.getMimeType()).build() : null;
    }

    @Override
    public Header getContentEncoding() {
        return contentType != null && contentType.getCharset() != null ?
                Header.builder().name(HttpEntity.CONTENT_ENCODING).value(contentType.getCharset().displayName()).build() :
                null;
    }

    @Override
    public InputStream getContent() throws IOException, IllegalStateException {
        return new ByteArrayInputStream(getEntity().getBytes());
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
        Objects.requireNonNull(out, "out");
        out.write(getEntity().getBytes());
        out.flush();
    }

    @Override
    public boolean isStreaming() {
        return false;
    }
}
