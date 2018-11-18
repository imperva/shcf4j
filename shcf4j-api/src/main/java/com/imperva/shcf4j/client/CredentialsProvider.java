package com.imperva.shcf4j.client;

import com.imperva.shcf4j.auth.AuthScope;
import com.imperva.shcf4j.auth.Credentials;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Map;

/**
 * <b>CredentialsProvider</b>
 * <p>
 * Basic credentials provider that maintains a collection of user
 * credentials.
 * </p>
 *
 * @author maxim.kirilov
 */
@Builder
@Value
public class CredentialsProvider {

    @Singular
    private final Map<AuthScope, Credentials> credentials;


}
