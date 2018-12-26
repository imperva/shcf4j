package com.imperva.shcf4j;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * <b>HttpEntity</b>
 *
 * <p>
 * An entity that can be sent or received with an HTTP message.
 * </p>
 *
 * @author maxim.kirilov
 */
public interface HttpEntity {

    /**
     * Tells if the entity is capable of producing its data more than once.
     * A repeatable entity's getContent() and writeTo(OutputStream) methods
     * can be called more than once whereas a non-repeatable entity's can not.
     *
     * @return true if the entity is repeatable, false otherwise.
     */
    boolean isRepeatable();

    /**
     * Tells about chunked encoding for this entity.
     * The primary purpose of this method is to indicate whether
     * chunked encoding should be used when the entity is sent.
     * For entities that are received, it can also indicate whether
     * the entity was received with chunked encoding.
     * <p>
     * The behavior of wrapping entities is implementation dependent,
     * but should respect the primary purpose.
     *
     * @return <code>true</code> if chunked encoding is preferred for this
     * entity, or <code>false</code> if it is not
     */
    boolean isChunked();

    /**
     * Tells the length of the content, if known.
     *
     * @return the number of bytes of the content, or
     * a negative number if unknown. If the content length is known
     * but exceeds {@link Long#MAX_VALUE Long.MAX_VALUE},
     * a negative number is returned.
     */
    long getContentLength();

    /**
     * Obtains the Content-Type header, if known.
     * This is the header that should be used when sending the entity,
     * or the one that was received with the entity. It can include a
     * charset attribute.
     *
     * @return the Content-Type header for this entity, or
     * <code>null</code> if the content type is unknown
     */
    Header getContentType();

    /**
     * Obtains the Content-Encoding header, if known.
     * This is the header that should be used when sending the entity,
     * or the one that was received with the entity.
     * Wrapping entities that modify the content encoding should
     * adjust this header accordingly.
     *
     * @return the Content-Encoding header for this entity, or
     * <code>null</code> if the content encoding is unknown
     */
    Header getContentEncoding();

    /**
     * Returns a content stream of the entity.
     * {@link #isRepeatable Repeatable} entities are expected
     * to create a new instance of {@link InputStream} for each invocation
     * of this method and therefore can be consumed multiple times.
     * Entities that are not {@link #isRepeatable repeatable} are expected
     * to return the same {@link InputStream} instance and therefore
     * may not be consumed more than once.
     * <p>
     * IMPORTANT: Please note all entity implementations must ensure that
     * all allocated resources are properly deallocated after
     * the {@link InputStream#close()} method is invoked.
     *
     * @return content stream of the entity.
     * @throws ProcessingException if the stream could not be created
     * @see #isRepeatable()
     */
    InputStream getContent();


    /**
     * Return the entire response body as a String.
     *
     * @param charset the charset to use when decoding the stream
     * @return the entire response body as a String.
     * @throws ProcessingException if the response body could not be created
     */
    default String getResponseBody(Charset charset) {
        try {
            BufferedInputStream bis = new BufferedInputStream(getContent());
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            int result = bis.read();
            while (result != -1) {
                buf.write((byte) result);
                result = bis.read();
            }
            return buf.toString(charset.displayName());
        } catch (Throwable t) {
            throw new ProcessingException(t);
        }
    }

    /**
     * Tells whether this entity depends on an underlying stream.
     * Streamed entities that read data directly from the socket should
     * return <code>true</code>. Self-contained entities should return
     * <code>false</code>. Wrapping entities should delegate this call
     * to the wrapped entity.
     *
     * @return <code>true</code> if the entity content is streamed,
     * <code>false</code> otherwise
     */
    boolean isStreaming();

}
