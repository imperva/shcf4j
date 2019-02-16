package com.imperva.shcf4j.java.http.client;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.NotSupportedException;
import com.imperva.shcf4j.ProcessingException;
import com.imperva.shcf4j.client.protocol.ClientContext;

import java.io.FileNotFoundException;
import java.time.Duration;

import static java.net.http.HttpRequest.newBuilder;

class JavaHttpRequestFactory {

    static java.net.http.HttpRequest createJavaHttpRequest(HttpHost target, HttpRequest request, ClientContext ctx){
        java.net.http.HttpRequest.Builder requestBuilder = newBuilder(target.toURI().resolve(request.getUri()));

        switch (request.getHttpMethod()){
            case GET:
                requestBuilder.GET();
                break;
            case POST:
                requestBuilder.POST(getBodyPublisher(request));
                break;
            case PUT:
                requestBuilder.PUT(getBodyPublisher(request));
                break;
            case DELETE:
                requestBuilder.DELETE();
                break;
            default:
                throw new RuntimeException("Not supported HTTP method: " + request.getHttpMethod());

        }

        for (Header h : request.getAllHeaders()) {
            requestBuilder.header(h.getName(), h.getValue());
        }

        if (ctx != null && ctx.getRequestConfig() != null){
            long requestTimeoutMillis = ctx.getRequestConfig().getSocketTimeoutMilliseconds();
            if ( requestTimeoutMillis > 0) {
                requestBuilder.timeout(Duration.ofMillis(requestTimeoutMillis));
            }
        }
        return requestBuilder.build();
    }


    private static  java.net.http.HttpRequest.BodyPublisher getBodyPublisher(HttpRequest request){

        if (request.getByteData() != null) {
            return java.net.http.HttpRequest.BodyPublishers.ofByteArray(request.getByteData());
        } else if (request.getStringData() != null) {
            return java.net.http.HttpRequest.BodyPublishers.ofString(request.getStringData());
        } else if (request.getFilePath() != null) {
            try {
                return java.net.http.HttpRequest.BodyPublishers.ofFile(request.getFilePath());
            } catch (FileNotFoundException fileNotFoundException){
                throw new ProcessingException(fileNotFoundException);
            }
        } else if (request.getInputStreamData() != null) {
            return java.net.http.HttpRequest.BodyPublishers.ofInputStream(() -> request.getInputStreamData());
        } else if (!request.getParts().isEmpty()) {
            throw new NotSupportedException("Currently not supported by Java HTTP Client");
        } else {
            throw new ProcessingException("No body provided!");
        }
    }
}
