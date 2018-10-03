package com.imperva.shcf4j.httpcomponents.client.impl;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpMessage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * <p>
 *     A minimal wrapper for {@code HttpMessage}, which enables to pass it to various
 *     processors before actual request execution (mainly for http headers manipulations).
 * </p>
 *
 */
public class HttpMessageWrapper implements HttpMessage {

    private final org.apache.http.HttpMessage httpMessage;

    public HttpMessageWrapper(org.apache.http.HttpMessage httpMessage) {
        this.httpMessage = httpMessage;
    }

    @Override
    public boolean containsHeader(String name) {
        return httpMessage.containsHeader(name);
    }

    @Override
    public List<? extends Header> getHeaders(String name) {
        return convertHeaders(httpMessage.getHeaders(name));
    }

    @Override
    public List<? extends Header> getAllHeaders() {
        return convertHeaders(httpMessage.getAllHeaders());
    }

    @Override
    public HttpMessage addHeader(String name, String value) {
        httpMessage.addHeader(name, value);
        return this;
    }

    @Override
    public HttpMessage setHeader(String name, String value) {
        httpMessage.setHeader(name, value);
        return this;
    }


    private List<? extends Header> convertHeaders(org.apache.http.Header[] headers){
        return
                Arrays.asList(headers)
                        .stream()
                        .map(h -> Header.builder().name(h.getName()).value(h.getValue()).build())
                        .collect(Collectors.toList());
    }
}
