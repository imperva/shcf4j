package com.imperva.shcf4j.request.body.multipart;

import java.nio.file.Path;
import java.util.Objects;

public class FilePartBuilder extends PartBuilder<FilePartBuilder> {

    protected Path filePath;

    public FilePartBuilder filePath(Path filePath){
        this.filePath = filePath;
        return this;
    }

    @Override
    public Part build() {
        Objects.requireNonNull(filePath, "filePath");
        return new FilePart(this);
    }
}
