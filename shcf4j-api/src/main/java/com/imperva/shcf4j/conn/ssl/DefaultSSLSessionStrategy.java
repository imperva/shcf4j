package com.imperva.shcf4j.conn.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.util.Objects;

/**
 * <b>SimpleSSLSessionStrategy</b>
 *
 * <p>
 *     A Simple implementation for {@link SSLSessionStrategy}
 * </p>
 *
 */
public class DefaultSSLSessionStrategy implements SSLSessionStrategy {


    private String[] supportedProtocols;
    private String[] supportedCipherSuites;
    private HostnameVerifier hostnameVerifier;
    private TrustManagerFactory trustManagerFactory = null;
    private KeyManagerFactory keyManagerFactory = null;

    public void setSupportedProtocols(String[] supportedProtocols) {
        this.supportedProtocols = supportedProtocols;
    }

    public void setSupportedCipherSuites(String[] supportedCipherSuites) {
        this.supportedCipherSuites = supportedCipherSuites;
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = Objects.requireNonNull(hostnameVerifier, "hostnameVerifier");
    }


    @Override
    public TrustManagerFactory getTrustManagerFactory() {
        return this.trustManagerFactory;
    }

    @Override
    public KeyManagerFactory getKeyManagerFactory() {
        return this.keyManagerFactory;
    }

    @Override
    public String[] getSupportedProtocols() {
        return supportedProtocols;
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return supportedCipherSuites;
    }

    @Override
    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }



}
