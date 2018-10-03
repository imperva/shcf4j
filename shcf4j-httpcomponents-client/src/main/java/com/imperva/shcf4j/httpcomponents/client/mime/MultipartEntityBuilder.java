package com.imperva.shcf4j.httpcomponents.client.mime;

import com.imperva.shcf4j.HttpEntity;

import java.io.File;


/**
 * <b>MultipartEntityBuilder</b>
 *
 * @author maxim.kirilov
 *
 */
public class MultipartEntityBuilder {

    private final org.apache.http.entity.mime.MultipartEntityBuilder internalBuilder;


    private MultipartEntityBuilder(org.apache.http.entity.mime.MultipartEntityBuilder internalBuilder) {
        this.internalBuilder = internalBuilder;
    }


    public static MultipartEntityBuilder builder(){
        return new MultipartEntityBuilder(org.apache.http.entity.mime.MultipartEntityBuilder.create());
    }


    public MultipartEntityBuilder setLaxMode() {
        this.internalBuilder.setLaxMode();
        return this;
    }

    public MultipartEntityBuilder addBinaryBody(final String name, final File file) {
        this.internalBuilder.addBinaryBody(name, file);
        return this;
    }

    public HttpEntity build(){
        return new MultipartEntity(this.internalBuilder.build());
    }



}