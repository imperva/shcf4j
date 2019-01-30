package com.imperva.shcf4j.request.body.multipart;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.entity.ContentType;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class PartBuilder<T extends PartBuilder<T>> {

    protected String name;

    protected ContentType contentType;

    protected String transferEncoding;

    protected String contentId;

    protected String dispositionType;

    protected List<Header> customHeaders = new LinkedList<>();

    public static StringPartBuilder stringPart() {
        return new StringPartBuilder();
    }

    public static ByteArrayPartBuilder byteArrayPart() {
        return new ByteArrayPartBuilder();
    }

    public static InputStreamPartBuilder inputStreamPart() {
        return new InputStreamPartBuilder();
    }

    public static FilePartBuilder filePart() {
        return new FilePartBuilder();
    }

    @SuppressWarnings("unchecked")
    private T asDerivedType() {
        return (T) this;
    }

    public T name(String name) {
        this.name = name;
        return asDerivedType();
    }

    public T contentType(ContentType contentType) {
        this.contentType = contentType;
        return asDerivedType();
    }


    public T transferEncoding(String transferEncoding) {
        this.transferEncoding = transferEncoding;
        return asDerivedType();
    }

    public T contentId(String contentId) {
        this.contentId = contentId;
        return asDerivedType();
    }

    public T dispositionType(String dispositionType) {
        this.dispositionType = dispositionType;
        return asDerivedType();
    }

    public T customHeader(Header header) {
        this.customHeaders.add(header);
        return asDerivedType();
    }

    public T customHeader(String headerName, String headerValue) {
        this.customHeaders.add(Header.builder().name(headerName).value(headerValue).build());
        return asDerivedType();
    }

    public T customHeaders(Collection<Header> customHeaders) {
        this.customHeaders.addAll(customHeaders);
        return asDerivedType();
    }

    public abstract Part build();

}
