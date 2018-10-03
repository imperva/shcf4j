package com.imperva.shcf4j;

import java.util.Locale;

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
     *         <code>null</code> if there is none
     */
    HttpEntity getEntity();


    /**
     * Obtains the locale of this response.
     * The locale is used to determine the reason phrase
     * for the status code.
     *
     * @return the locale of this response, never <code>null</code>
     */
    Locale getLocale();
}
