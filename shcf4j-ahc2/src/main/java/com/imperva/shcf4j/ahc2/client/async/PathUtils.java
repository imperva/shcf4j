package com.imperva.shcf4j.ahc2.client.async;

import java.nio.file.Path;
import java.util.Objects;

class PathUtils {

    /**
     * Extracts file extension, for example: hello.txt => txt
     *
     * @param filePath
     * @return file extension or <code>null</code> in case it doesnt exist
     */
    static String extractFileExtension(Path filePath) {
        Objects.requireNonNull(filePath, "filePath");
        Path fileName = filePath.getFileName();
        int dotLastIndex = fileName.toString().lastIndexOf('.');
        if (dotLastIndex != -1) {
            return fileName.toString().substring(dotLastIndex);
        }
        return null;
    }
}
