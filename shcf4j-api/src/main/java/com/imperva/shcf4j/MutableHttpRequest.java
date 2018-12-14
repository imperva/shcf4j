package com.imperva.shcf4j;


/**
 * <b>MutableHttpRequest</b>
 *
 * <p>
 *     Represent mutable request object which is not thread safe, usually used by request interceptors
 * </p>
 *
 * @author maxim.kirilov
 */
public interface MutableHttpRequest extends HttpRequest {


    /**
     * Adds a header to this message. The header will be appended to the end of
     * the list.
     *
     * @param name the name of the header.
     * @param value the value of the header.
     */
    void addHeader(String name, String value);


    /**
     * Adds a header to this message. The header will be appended to the end of
     * the list.
     *
     * @param header the header to append.
     */
    void addHeader(Header header);


    /**
     * Overwrites the first header with the same name. The new header will be appended to
     * the end of the list, if no header with the given name can be found.
     *
     * @param name the name of the header.
     * @param value the value of the header.
     */
    void setHeader(String name, String value);

    /**
     * Overwrites the first header with the same name. The new header will be appended to
     * the end of the list, if no header with the given name can be found.
     *
     * @param header the header to set.
     */
    void setHeader(Header header);
}
