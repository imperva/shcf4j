package com.imperva.shcf4j.entity;

import lombok.Builder;
import lombok.Value;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * <b>ContentType</b>
 *
 * <p>
 * Content type information consisting of a MIME type and an optional charset.
 * </p>
 *
 * @author maxim.kirilov
 */
@Builder
@Value
public class ContentType {

    private final String mimeType;

    private final Charset charset;


    public static ContentType createApplicationXML() {
        return builder().mimeType("application/xml").charset(ISO_8859_1).build();
    }

    public static ContentType createApplicationJSON() {
        return builder().mimeType("application/json").charset(UTF_8).build();
    }

    public static ContentType createTextPlain() {
        return builder().mimeType("text/plain").charset(ISO_8859_1).build();
    }


    public static ContentType createTextXML() {
        return builder().mimeType("text/xml").charset(ISO_8859_1).build();
    }

    public static ContentType createApplicationFormUrlEncoded(){
        return builder().mimeType("application/x-www-form-urlencoded").charset(ISO_8859_1).build();
    }

    public static ContentType createApplicationOctetStream(){
        return builder().mimeType("application/octet-stream").build();
    }

    public static ContentType probeContentType(Path path){
        try {
            String contentType = Files.probeContentType(path);
            // Default value for files according to RFC7578 is "application/octet-stream".
            return builder().mimeType(contentType != null ? contentType : "application/octet-stream").build();
        } catch (IOException ioException){
            throw new RuntimeException(ioException);
        }
    }

}
