package com.imperva.shcf4j;

import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;

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

}
