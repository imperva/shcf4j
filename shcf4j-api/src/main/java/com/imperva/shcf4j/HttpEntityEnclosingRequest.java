package com.imperva.shcf4j;

import java.net.URI;

public class HttpEntityEnclosingRequest extends HttpRequest {

    private HttpEntity entity;


    HttpEntityEnclosingRequest(URI uri, SupportedHttpMethod method) {
        super(uri, method);
    }


    public void setEntity(HttpEntity entity) {
        this.entity = entity;
    }

    public HttpEntity getEntity() {
        return entity;
    }
}
