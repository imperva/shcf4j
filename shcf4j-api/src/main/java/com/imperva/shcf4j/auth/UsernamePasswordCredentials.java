package com.imperva.shcf4j.auth;


import lombok.Builder;
import lombok.Value;

import java.security.Principal;

/**
 * <b>UsernamePasswordCredentials</b>
 * <p/>
 * Simple implementation based on a user name / password
 * pair.
 * <p/>
 * <p/>
 * Created by Maxim.Kirilov on 10/26/2014.
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
