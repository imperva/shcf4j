package com.imperva.shcf4j.request.body.multipart;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.entity.ContentType;
import lombok.Getter;

import java.util.List;

@Getter
abstract class PartBase implements Part {

    /**
     * The name of the form field, part of the Content-Disposition header
     */
    private final String name;

    /**
     * Content type information consisting of a MIME type and an optional charset.
     */
    private final ContentType contentType;

    /**
     * The Content-Transfer-Encoding header value.
     */
    private final String transferEncoding;

    /**
     * The Content-Id
     */
    private final String contentId;

    /**
     * The disposition type (part of Content-Disposition)
     */
    private final String dispositionType;

    /**
     * Additional part headers
     */
    private final List<Header> customHeaders;


    protected PartBase(PartBuilder<?> builder) {
        this.name = builder.name;
        this.contentType = builder.contentType;
        this.transferEncoding = builder.transferEncoding;
        this.contentId = builder.contentId;
        this.dispositionType = builder.dispositionType;
        this.customHeaders = builder.customHeaders;
    }

}
