package com.imperva.shcf4j.request.body.multipart;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.entity.ContentType;

import java.util.List;


/**
 * <b>Part</b>
 *
 * <p>
 *     Represents a part of multipart request
 * </p>
 *
 */
public interface Part {

    /**
     * Return the name of this part.
     *
     * @return The name.
     */
    String getName();

    /**
     * Return content type information consisting of a MIME type and an optional charset.
     *
     * @return
     */
    ContentType getContentType();

    /**
     * Return the transfer encoding of this part (the Content-Transfer-Encoding header value.).
     *
     * @return the transfer encoding, or <code>null</code> to exclude the
     * transfer encoding header
     */
    String getTransferEncoding();

    /**
     * Return the content ID of this part.
     *
     * @return the content ID, or <code>null</code> to exclude the content ID
     * header
     */
    String getContentId();

    /**
     * Gets the disposition-type to be used in Content-Disposition header
     *
     * @return the disposition-type
     */
    String getDispositionType();

    List<Header> getCustomHeaders();
}
