package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.NotSupportedException;
import com.imperva.shcf4j.request.body.multipart.ByteArrayPart;
import com.imperva.shcf4j.request.body.multipart.FilePart;
import com.imperva.shcf4j.request.body.multipart.InputStreamPart;
import com.imperva.shcf4j.request.body.multipart.Part;
import com.imperva.shcf4j.request.body.multipart.StringPart;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;


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

        for (Header h : request.getAllHeaders()) {
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
        } else if (!request.getParts().isEmpty()) {
            MultipartEntityBuilder multipartBuilder = MultipartEntityBuilder.create();
            for (Part p : request.getParts()) {
                if (p instanceof StringPart) {
                    StringPart sp = (StringPart) p;
                    if (sp.getContentType() != null) {
                        multipartBuilder.addTextBody(
                                sp.getName(),
                                sp.getValue(),
                                ContentType.create(sp.getContentType().getMimeType(), sp.getContentType().getCharset()));
                    } else { // Part content-type is null
                        multipartBuilder.addTextBody(sp.getName(), sp.getValue());
                    }
                } else if (p instanceof ByteArrayPart) {
                    ByteArrayPart bap = (ByteArrayPart) p;
                    if (bap.getContentType() != null) {
                        multipartBuilder.addBinaryBody(
                                bap.getName(),
                                bap.getBytes(),
                                ContentType.create(bap.getContentType().getMimeType(), bap.getContentType().getCharset()),
                                null);
                    } else { // Part content-type is null
                        multipartBuilder.addBinaryBody(bap.getName(), bap.getBytes());
                    }
                } else if (p instanceof InputStreamPart) {
                    InputStreamPart isp = (InputStreamPart) p;
                    if (isp.getContentType() != null) {
                        multipartBuilder.addBinaryBody(
                                isp.getName(),
                                isp.getInputStream(),
                                ContentType.create(isp.getContentType().getMimeType(), isp.getContentType().getCharset()),
                                null);
                    } else { // Part content-type is null
                        multipartBuilder.addBinaryBody(isp.getName(), isp.getInputStream());
                    }
                } else if (p instanceof FilePart) {
                    FilePart fp = (FilePart) p;
                    if (fp.getContentType() != null) {
                        multipartBuilder.addBinaryBody(
                                fp.getName(),
                                fp.getFilePath().toFile(),
                                ContentType.create(fp.getContentType().getMimeType(), fp.getContentType().getCharset()),
                                null);
                    } else { // Part content-type is null
                        multipartBuilder.addBinaryBody(fp.getName(), fp.getFilePath().toFile());
                    }
                } else {
                    throw new NotSupportedException("An unknown part type received: " + p);
                }
            }
            return multipartBuilder.build();
        }

        return null;
    }
}
