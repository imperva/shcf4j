package com.imperva.shcf4j.entity;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpEntity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;


@Builder
@Value
public class FileEntity implements HttpEntity {

    @NonNull
    private final Path path;

    private final ContentType contentType;


    @Override
    public boolean isRepeatable() {
       return true;
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
        return Files.newInputStream(getPath());
    }


    @Override
    public boolean isChunked() {
        return false;
    }

    @Override
    public long getContentLength() {
        try {
            return Files.size(getPath());
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        Objects.requireNonNull(outputStream, "outputStream");
        Files.copy(getPath(), outputStream);
    }

    @Override
    public boolean isStreaming() {
        return false;
    }
}
