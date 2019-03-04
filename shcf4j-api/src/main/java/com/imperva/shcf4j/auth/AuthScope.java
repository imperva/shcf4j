package com.imperva.shcf4j.auth;


import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class AuthScope {

    private final String host;
    @Builder.Default
    private final int port = -1;
    private final String realm;
    private final String scheme;

    public static AuthScope createAnyAuthScope() {
        return builder().build();
    }

}
