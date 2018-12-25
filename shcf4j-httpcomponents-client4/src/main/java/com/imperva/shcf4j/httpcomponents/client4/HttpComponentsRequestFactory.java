package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpRequest;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;

class HttpComponentsRequestFactory {


    static org.apache.http.HttpRequest createHttpComponentsRequest(HttpRequest request) {

        org.apache.http.HttpRequest httpRequest;

        switch (request.getHttpMethod()) {
            case GET:
                httpRequest = new HttpGet(request.getUri());
                break;
            case POST:
                HttpPost postRequest = new HttpPost(request.getUri());
                postRequest.setEntity(getHttpEntity(request));
                httpRequest = postRequest;
                break;
            case PUT:
                HttpPut putRequest = new HttpPut(request.getUri());
                putRequest.setEntity(getHttpEntity(request));
                httpRequest = putRequest;
                break;
            case DELETE:
                httpRequest = new HttpDelete(request.getUri());
                break;
            default:
                throw new RuntimeException("Not supported HTTP method: " + request.getHttpMethod());
        }

        for(Header h : request.getAllHeaders()){
            httpRequest.addHeader(h.getName(), h.getValue());
        }

        return httpRequest;
    }


    private static HttpEntity getHttpEntity(HttpRequest request) {
        if (request.getFilePath() != null) {
            return new FileEntity(request.getFilePath().toFile());
        } else if (request.getInputStreamData() != null) {
            return new InputStreamEntity(request.getInputStreamData());
        } else if (request.getByteData() != null) {
            return new ByteArrayEntity(request.getByteData());
        } else if (request.getStringData() != null) {
            return new StringEntity(request.getStringData(), request.getCharset());
        }

        return null;
    }
}
