package com.imperva.shcf4j.conn.ssl;

import com.imperva.shcf4j.helpers.Util;
import lombok.Builder;
import lombok.Getter;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.security.NoSuchAlgorithmException;

/**
 * <b>SimpleSSLSessionStrategy</b>
 *
 * <p>
 *     A Simple implementation for {@link SSLSessionStrategy}
 * </p>
 *
 */
@Builder
@Getter
public class DefaultSSLSessionStrategy implements SSLSessionStrategy {

    @Builder.Default
    private final String[] supportedProtocols = {"TLSv1.1"};

    @Builder.Default
    private final String[] supportedCipherSuites = loadDefaultSupportedCipherSuites();

    private final HostnameVerifier hostnameVerifier;

    private final TrustManagerFactory trustManagerFactory;

    private final KeyManagerFactory keyManagerFactory;


    private static String[] loadDefaultSupportedCipherSuites(){
        try{
            return SSLContext.getDefault().getSupportedSSLParameters().getCipherSuites();
        } catch (NoSuchAlgorithmException noSuchAlgorithmException){
            Util.report("Failed to load default SSL protocols & cipher suites", noSuchAlgorithmException);
        }
        return null;
    }

}
