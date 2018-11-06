package com.imperva.shcf4j;

public interface HttpResponse extends HttpMessage {

    /**
     * Obtains the status line of this response.
     *
     * @return the status line, or <code>null</code> if not yet set
     */
    StatusLine getStatusLine();


    /**
     * Obtains the message entity of this response, if any.
     *
     * @return the response entity, or
     * <code>null</code> if there is none
     */
    HttpEntity getEntity();


}
