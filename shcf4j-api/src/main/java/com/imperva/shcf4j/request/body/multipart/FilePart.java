package com.imperva.shcf4j.request.body.multipart;

import lombok.Getter;

import java.nio.file.Path;

@Getter
public class FilePart extends PartBase {

    private final Path filePath;

    FilePart(FilePartBuilder builder){
        super(builder);
        this.filePath = builder.filePath;
    }

    @Override
    public String getTransferEncoding() {
        return super.getTransferEncoding() != null ? super.getTransferEncoding() : MIME.ENC_BINARY;
    }
}
