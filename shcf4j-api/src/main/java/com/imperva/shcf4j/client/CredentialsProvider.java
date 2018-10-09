package com.imperva.shcf4j.client;

import com.imperva.shcf4j.auth.AuthScope;
import com.imperva.shcf4j.auth.Credentials;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <b>CredentialsProvider</b>
 * <p>
 * Basic credentials provider that maintains a collection of user
 * credentials.
 * </p>
 *
 * @author maxim.kirilov
 */
public class CredentialsProvider {

    private final Map<AuthScope, Credentials> credMap = new ConcurrentHashMap<>();


    /**
     * @return a default {@code CredentialsProvider}
     */
    public static CredentialsProvider createSystemDefaultCredentialsProvider() {
        return new CredentialsProvider();
    }


    /**
     * Sets the {@link Credentials credentials} for the given authentication
     * scope. Any previous credentials for the given scope will be overwritten.
     *
     * @param authscope   the {@link AuthScope authentication scope}
     * @param credentials the authentication {@link Credentials credentials}
     *                    for the given scope.
     */
    public void setCredentials(final AuthScope authscope, final Credentials credentials) {
        this.credMap.put(authscope, credentials);
    }


    public Map<AuthScope, Credentials> getCredentials(){
        return this.credMap;
    }

}
