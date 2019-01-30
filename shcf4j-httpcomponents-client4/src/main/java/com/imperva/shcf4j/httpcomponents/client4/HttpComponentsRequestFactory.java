package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.NotSupportedException;
import com.imperva.shcf4j.request.body.multipart.ByteArrayPart;
import com.imperva.shcf4j.request.body.multipart.FilePart;
import com.imperva.shcf4j.request.body.multipart.InputStreamPart;
import com.imperva.shcf4j.request.body.multipart.MIME;
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
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;


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
                FormBodyPartBuilder formBodyPartBuilder =
                        FormBodyPartBuilder
                                .create()
                                .setName(p.getName())
                                .setBody(extractContentBody(p));

                if (p.getDispositionType() != null) {
                    formBodyPartBuilder.addField(MIME.CONTENT_DISPOSITION, p.getDispositionType());
                }
                if (p.getTransferEncoding() != null) {
                    formBodyPartBuilder.addField(MIME.CONTENT_TRANSFER_ENC, p.getTransferEncoding());
                }
                for (Header h : p.getCustomHeaders()) {
                    formBodyPartBuilder.addField(h.getName(), h.getValue());
                }
                multipartBuilder.addPart(formBodyPartBuilder.build());
            }
            return multipartBuilder.build();
        }

        return null;
    }


    private static ContentBody extractContentBody(Part p) {
        ContentType contentType = ContentType.create(p.getContentType().getMimeType(), p.getContentType().getCharset());
        if (p instanceof StringPart) {
            StringPart sp = (StringPart) p;
            return new StringBody(sp.getValue(), contentType);
        } else if (p instanceof ByteArrayPart) {
            ByteArrayPart bap = (ByteArrayPart) p;
            return new ByteArrayBody(bap.getBytes(), contentType, null);
        } else if (p instanceof InputStreamPart) {
            InputStreamPart isp = (InputStreamPart) p;
            return new InputStreamBody(isp.getInputStream(), contentType, null);
        } else if (p instanceof FilePart) {
            FilePart fp = (FilePart) p;
            return new FileBody(fp.getFilePath().toFile(), contentType, null);
        } else {
            throw new NotSupportedException("An unknown part type received: " + p);
        }
    }
}
