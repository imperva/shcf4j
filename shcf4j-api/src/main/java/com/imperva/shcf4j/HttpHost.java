package com.imperva.shcf4j;

import lombok.*;

@Builder
@Value
public class HttpHost {

    public static final String DEFAULT_SCHEME_NAME = "http";

    @Builder.Default
    private final String schemeName = DEFAULT_SCHEME_NAME;

    /**
     * hostname (IP or DNS name)
     */
    private final String hostname;

    /**
     * port, or <code>-1</code> if not set
     */
    @Builder.Default
    private final int port = -1;

}
