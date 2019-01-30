package com.imperva.shcf4j.entity;

import lombok.Builder;
import lombok.Value;

import java.nio.charset.Charset;

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

}
