package com.imperva.shcf4j.entity;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;


@Builder
@Value
@EqualsAndHashCode(exclude = "inStream")
public class InputStreamEntity implements HttpEntity {

    private static final int OUTPUT_BUFFER_SIZE = 1024;

    @NonNull
    private final InputStream inStream;
    private final long length = -1;
    private final ContentType contentType;



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
        return this.length;
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
        return this.inStream;
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
        Objects.requireNonNull(out, "outputStream");
        try (final InputStream in = this.inStream) {
            final byte[] buffer = new byte[OUTPUT_BUFFER_SIZE];
            int l;
            if (this.length < 0) {
                // consume until EOF
                while ((l = in.read(buffer)) != -1) {
                    out.write(buffer, 0, l);
                }
            } else {
                // consume no more than length
                long remaining = this.length;
                while (remaining > 0) {
                    l = in.read(buffer, 0, (int)Math.min(OUTPUT_BUFFER_SIZE, remaining));
                    if (l == -1) {
                        break;
                    }
                    out.write(buffer, 0, l);
                    remaining -= l;
                }
            }
        }
    }

    @Override
    public boolean isStreaming() {
        return true;
    }
}
