package com.imperva.shcf4j.client;

import com.imperva.shcf4j.HttpResponse;

import java.io.IOException;

public abstract class ResponseHandlerBase<T> {

    /**
     * Processes an {@link HttpResponse} and returns some value
     * corresponding to that response.
     *
     * @param response The response to process
     * @return A value determined by the response
     * @throws IOException in case of a problem or the connection was aborted
     */
    public abstract T handleResponse(HttpResponse response) throws IOException;
}
