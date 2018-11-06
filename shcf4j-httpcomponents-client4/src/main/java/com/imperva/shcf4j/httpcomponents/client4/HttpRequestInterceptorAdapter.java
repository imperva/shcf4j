package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.HttpMessage;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

import java.util.Objects;
import java.util.function.Consumer;


/**
 * <b>HttpRequestInterceptorAdapter</b>
 *
 *
 *
 *
 * @author maxim.kirilov
 */
class HttpRequestInterceptorAdapter implements HttpRequestInterceptor {

    private final Consumer<HttpMessage> requestInterceptor;

    HttpRequestInterceptorAdapter(Consumer<HttpMessage> requestInterceptor) {
        Objects.requireNonNull(requestInterceptor, "interceptor");
        this.requestInterceptor = requestInterceptor;
    }

    @Override
    public void process(HttpRequest request, HttpContext context) {
        requestInterceptor.accept(new HttpMessageWrapper(request));
    }

}
