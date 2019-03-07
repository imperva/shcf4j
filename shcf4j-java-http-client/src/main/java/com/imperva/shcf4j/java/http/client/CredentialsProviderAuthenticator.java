package com.imperva.shcf4j.java.http.client;

import com.imperva.shcf4j.auth.AuthScope;
import com.imperva.shcf4j.auth.Credentials;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

class CredentialsProviderAuthenticator extends Authenticator {

    private static PasswordAuthentication DUMMY_PASSWORD_AUTH =
            new PasswordAuthentication("", "".toCharArray());

    private final AuthScope authScope;
    private final Credentials credentials;

    private CredentialsProviderAuthenticator(AuthScope authScope, Credentials credentials) {
        this.authScope = authScope;
        this.credentials = credentials;
    }


    public static Authenticator createAuthenticator(AuthScope authScope, Credentials credentials) {
        return new CredentialsProviderAuthenticator(authScope, credentials);
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        if ( (authScope.getScheme() == null ||  authScope.getScheme().equalsIgnoreCase(getRequestingScheme()))
                && ((authScope.getHost() == null || authScope.getHost().equalsIgnoreCase(getRequestingHost())))
                && ((authScope.getRealm() == null || authScope.getRealm().equalsIgnoreCase(getRequestingPrompt())))
                && ((authScope.getPort() < 0) || authScope.getPort() == getRequestingPort())) {

            return new PasswordAuthentication(credentials.getUserPrincipal().getName(), credentials.getPassword().toCharArray());
        }

        return null;
    }

}
