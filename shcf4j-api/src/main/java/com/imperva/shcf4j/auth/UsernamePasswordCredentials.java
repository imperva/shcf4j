package com.imperva.shcf4j.auth;


import lombok.Builder;
import lombok.Value;

import java.security.Principal;

/**
 * <b>UsernamePasswordCredentials</b>
 * <p>
 * Simple implementation based on a user name / password pair.
 *
 * @author maxim.kirilov
 */

@Builder
@Value
public class UsernamePasswordCredentials implements Credentials, Principal {

    private String username;

    private String password;


    @Override
    public Principal getUserPrincipal() {
        return this;
    }

    @Override
    public String getName() {
        return username;
    }
}
