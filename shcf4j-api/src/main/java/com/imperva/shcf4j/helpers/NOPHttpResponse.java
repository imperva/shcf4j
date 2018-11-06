package com.imperva.shcf4j.helpers;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpEntity;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.StatusLine;

import java.util.Collections;
import java.util.List;

/**
 * <b>NOPHttpResponse</b>
 *
 *
 * <p>
 *     An empty {@link HttpResponse} that supposed to be returned from an {@link NOPSyncHttpClient}
 * </p>
 *
 * @author maxim.kirilov
 */
class NOPHttpResponse implements HttpResponse {


    static final HttpResponse INSTANCE = new NOPHttpResponse();


    @Override
    public StatusLine getStatusLine() {
        return StatusLine.builder().build();
    }

    @Override
    public HttpEntity getEntity() {
        return null;
    }


    @Override
    public boolean containsHeader(String name) {
        return false;
    }

    @Override
    public List<? extends Header> getHeaders(String name) {
        return Collections.emptyList();
    }

    @Override
    public List<? extends Header> getAllHeaders() {
        return Collections.emptyList();
    }

}
