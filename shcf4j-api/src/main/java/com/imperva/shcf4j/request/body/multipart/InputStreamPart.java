package com.imperva.shcf4j.request.body.multipart;

import lombok.Getter;

import java.io.InputStream;

@Getter
public class InputStreamPart extends PartBase {

    private final InputStream inputStream;
    private final long contentLength;

    InputStreamPart(InputStreamPartBuilder builder) {
        super(builder);
        this.inputStream = builder.inputStream;
        this.contentLength = builder.contentLength;
    }

    @Override
    public String getTransferEncoding() {
        return MIME.ENC_BINARY;
    }
}
