package com.imperva.shcf4j.auth;

import lombok.Builder;
import lombok.Value;

import java.security.Principal;

/**
 * <b>NTCredentials</b>
 *
 * {@link Credentials} implementation for Microsoft Windows platforms that includes
 * Windows specific attributes such as name of the domain the user belongs to.
 *
 * @author maxim.kirilov
 */
@Builder
@Value
public class NTCredentials implements Credentials, Principal{

    private final String username;

    private final String password;

    @Builder.Default private final String workstation = "";

    private final String domain;


    @Override
    public Principal getUserPrincipal() {
        return this;
    }


    @Override
    public String getName() {
        if (this.domain != null && this.domain.length() > 0) {
            final StringBuilder buffer = new StringBuilder();
            buffer.append(this.domain);
            buffer.append('\\');
            buffer.append(this.username);
            return toString();
        } else {
            return this.username;
        }
    }
}
