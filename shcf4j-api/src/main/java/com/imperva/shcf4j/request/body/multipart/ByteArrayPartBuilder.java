package com.imperva.shcf4j.request.body.multipart;

import com.imperva.shcf4j.entity.ContentType;

import java.util.Arrays;
import java.util.Objects;

public class ByteArrayPartBuilder extends PartBuilder<ByteArrayPartBuilder> {

    protected byte[] bytes;

    ByteArrayPartBuilder() {
        contentType(ContentType.createApplicationOctetStream());
    }

    public ByteArrayPartBuilder bytes(byte[] bytes){
        Objects.requireNonNull(bytes, "bytes");
        this.bytes = Arrays.copyOf(bytes, bytes.length);
        return this;
    }

    @Override
    public Part build() {
        Objects.requireNonNull(bytes, "bytes");
        return new ByteArrayPart(this);
    }
}
