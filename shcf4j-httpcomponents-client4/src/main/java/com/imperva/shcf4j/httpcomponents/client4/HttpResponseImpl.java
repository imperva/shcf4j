package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpEntity;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.ProcessingException;
import com.imperva.shcf4j.ProtocolVersion;
import com.imperva.shcf4j.StatusLine;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * <b>HttpResponseImpl</b>
 * <p/>
 * After receiving and interpreting a request message, a server responds
 * with an HTTP response message.
 * <pre>
 *     Response      = Status-Line
 *                     *(( general-header
 *                      | response-header
 *                      | entity-header ) CRLF)
 *                     CRLF
 *                     [ message-body ]
 * </pre>
 *
 * @author <font color="blue">Maxim Kirilov</font>
 * <p/>
 * Date: April 2014
 */
class HttpResponseImpl implements HttpResponse {

    private final org.apache.http.HttpResponse response;
    private final StatusLine statusLine;
    private final HttpEntity entity;

    HttpResponseImpl(org.apache.http.HttpResponse response) {
        this.response = Objects.requireNonNull(response, "http response");
        this.entity = new SimpleHttpEntity(response.getEntity());
        this.statusLine = StatusLine
                .builder()
                .statusCode(response.getStatusLine().getStatusCode())
                .protocolVersion(
                        ProtocolVersion
                                .builder()
                                .protocol(response.getStatusLine().getProtocolVersion().getProtocol())
                                .major(response.getStatusLine().getProtocolVersion().getMajor())
                                .minor(response.getStatusLine().getProtocolVersion().getMinor())
                                .build())
                .reasonPhrase(response.getStatusLine().getReasonPhrase())
                .build();
    }


    /**
     * Obtains the status line of this response.
     *
     * @return the status line, or <code>null</code> if not yet set
     */
    public StatusLine getStatusLine() {
        return this.statusLine;
    }


    /**
     * Obtains the message entity of this response, if any.
     *
     * @return the response entity, or
     * <code>null</code> if there is none
     */
    public HttpEntity getEntity() {
        return this.entity;
    }


    /**
     * Obtains the locale of this response.
     * The locale is used to determine the reason phrase
     * for the status code.
     *
     * @return the locale of this response, never <code>null</code>
     */
    public Locale getLocale() {
        return this.response.getLocale();
    }


    @Override
    public boolean containsHeader(String name) {
        return response.containsHeader(name);
    }

    @Override
    public List<? extends Header> getHeaders(String name) {
        return ConversionUtils.convert(response.getHeaders(name));
    }

    @Override
    public List<? extends Header> getAllHeaders() {
        return ConversionUtils.convert(response.getAllHeaders());
    }


    private static class SimpleHttpEntity implements HttpEntity {
        private org.apache.http.HttpEntity entity;

        private Header contentType;
        private Header contentEncoding;

        SimpleHttpEntity(org.apache.http.HttpEntity entity) {
            this.entity = entity;

            this.contentType = entity.getContentType() != null ?
                    Header
                            .builder()
                            .name(entity.getContentType().getName())
                            .value(entity.getContentType().getValue())
                            .build() : null;
            this.contentEncoding = entity.getContentEncoding() != null ?
                    Header
                            .builder()
                            .name(entity.getContentEncoding().getName())
                            .value(entity.getContentEncoding().getValue())
                            .build() : null;
        }

        @Override
        public boolean isRepeatable() {
            return entity.isRepeatable();
        }

        @Override
        public boolean isChunked() {
            return entity.isChunked();
        }

        @Override
        public long getContentLength() {
            return entity.getContentLength();
        }

        @Override
        public Header getContentType() {
            return this.contentType;
        }

        @Override
        public Header getContentEncoding() {
            return this.contentEncoding;
        }

        @Override
        public InputStream getContent() {
            try {
                return entity.getContent();
            } catch (Throwable t){
                throw new ProcessingException(t);
            }

        }


        @Override
        public boolean isStreaming() {
            return entity.isStreaming();
        }

        @Override
        public String toString() {
            return entity.toString();
        }
    }
}
