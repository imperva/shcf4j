package com.imperva.shcf4j.httpcomponents.client4;

import org.apache.http.auth.Credentials;

import java.security.Principal;

class CredentialsAdapter implements Credentials {

    private final com.imperva.shcf4j.auth.Credentials credentials;

    public CredentialsAdapter(com.imperva.shcf4j.auth.Credentials credentials) {
        this.credentials = credentials;
    }

    public Principal getUserPrincipal() {
        return this.credentials.getUserPrincipal();
    }

    @Override
    public String getPassword() {
        return this.credentials.getPassword();
    }
}
