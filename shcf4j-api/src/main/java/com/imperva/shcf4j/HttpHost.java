package com.imperva.shcf4j;

import lombok.*;

import java.net.URI;

@Builder
@Value
public class HttpHost {

    static final String DEFAULT_SCHEME_NAME = "http";
    static final String DEFAULT_HOSTNAME = "localhost";
    static final int DEFAULT_PORT = 80;

    private static final String URI_PATTERN = "%s://%s:%d";

    public URI toURI(){
        return URI.create(String.format(URI_PATTERN, getSchemeName(), getHostname(), getPort()));
    }

    @Builder.Default
    private final String schemeName = DEFAULT_SCHEME_NAME;

    /**
     * hostname (IP or DNS name)
     */
    @Builder.Default
    private final String hostname = DEFAULT_HOSTNAME;

    /**
     * port, or <code>-1</code> if not set
     */
    @Builder.Default
    private final int port = DEFAULT_PORT;

}
