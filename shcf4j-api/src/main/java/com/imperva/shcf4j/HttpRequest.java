package com.imperva.shcf4j;

import com.imperva.shcf4j.request.body.multipart.Part;

import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;

public interface HttpRequest extends HttpMessage {

    enum SupportedHttpMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

    SupportedHttpMethod getHttpMethod();

    URI getUri();

    Path getFilePath();

    byte[] getByteData();

    String getStringData();

    ByteBuffer getByteBufferData();

    InputStream getInputStreamData();

    Charset getCharset();

    List<Part> getParts();

}
