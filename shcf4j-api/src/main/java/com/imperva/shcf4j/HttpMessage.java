package com.imperva.shcf4j;


import java.util.List;

/**
 * <b>HttpMessage</b>
 * <p/>
 * HTTP messages consist of requests from client to server and responses
 * from server to client.
 * <pre>
 *     HTTP-message   = Request | Response     ; HTTP/1.1 messages
 * </pre>
 * <p>
 * HTTP messages use the generic message format of RFC 822 for
 * transferring entities (the payload of the message). Both types
 * of message consist of a start-line, zero or more header fields
 * (also known as "headers"), an empty line (i.e., a line with nothing
 * preceding the CRLF) indicating the end of the header fields,
 * and possibly a message-body.
 * </p>
 * <pre>
 *      generic-message = start-line
 *                        *(message-header CRLF)
 *                        CRLF
 *                        [ message-body ]
 *      start-line      = Request-Line | Status-Line
 * </pre>
 *
 * @author <font color="blue">Maxim Kirilov</font>
 *         Date: April 2014
 */
public interface HttpMessage {


    /**
     * Checks if a certain header is present in this message. Header values are
     * ignored.
     *
     * @param name the header name to check for.
     * @return true if at least one header with this name is present.
     */
    boolean containsHeader(String name);


    /**
     * Returns the first header with a specified name of this message. Header
     * values are ignored. If there is more than one matching header in the
     * message the first element of {@link #getHeaders(String)} is returned.
     * If there is no matching header in the message <code>null</code> is
     * returned.
     *
     * @param name the name of the header to return.
     * @return the first header whose name property equals <code>name</code>
     *   or <code>null</code> if no such header could be found.
     */
    default Header getFirstHeader(String name){
        return getHeaders(name).stream().findFirst().orElse(null);
    }

    /**
     * Returns all the headers with a specified name of this message. Header values
     * are ignored. Headers are ordered in the sequence they will be sent over a
     * connection.
     *
     * @param name the name of the headers to return.
     * @return the headers whose name property equals <code>name</code>.
     */
    List<? extends Header> getHeaders(String name);

    /**
     * Returns all the headers of this message. Headers are ordered in the sequence
     * they will be sent over a connection.
     *
     * @return all the headers of this message
     */
    List<? extends Header> getAllHeaders();

    /**
     * Adds a header to this message. The header will be appended to the end of
     * the list.
     *
     * @param name  the name of the header.
     * @param value the value of the header.
     */
    HttpMessage addHeader(String name, String value);

    /**
     * Overwrites the first header with the same name. The new header will be appended to
     * the end of the list, if no header with the given name can be found.
     *
     * @param name  the name of the header.
     * @param value the value of the header.
     */
    HttpMessage setHeader(String name, String value);

}
