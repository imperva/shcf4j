package com.imperva.shcf4j.request.body.multipart;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class ByteArrayPart extends PartBase {

    protected final byte[] bytes;

    ByteArrayPart(ByteArrayPartBuilder builder){
        super(builder);
        this.bytes = Arrays.copyOf(builder.bytes, builder.bytes.length);
    }

    @Override
    public String getTransferEncoding() {
        return super.getTransferEncoding() != null ? super.getTransferEncoding() : MIME.ENC_BINARY;
    }
}
