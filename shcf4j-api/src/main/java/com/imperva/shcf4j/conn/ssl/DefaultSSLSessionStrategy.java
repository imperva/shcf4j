package com.imperva.shcf4j.conn.ssl;

import com.imperva.shcf4j.helpers.Util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
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


    private String[] supportedProtocols = {"TLSv1.1"};
    private String[] supportedCipherSuites;

    {
        try{
            supportedCipherSuites = SSLContext.getDefault().getSupportedSSLParameters().getCipherSuites();
        } catch (NoSuchAlgorithmException noSuchAlgorithmException){
            Util.report("Failed to load default SSL protocols & cipher suites", noSuchAlgorithmException);
        }
    }


    private HostnameVerifier hostnameVerifier;
    private TrustManagerFactory trustManagerFactory = null;
    private KeyManagerFactory keyManagerFactory = null;

    public void setSupportedProtocols(String[] supportedProtocols) {
        this.supportedProtocols =  Arrays.copyOf(supportedProtocols, supportedProtocols.length);
    }

    public void setSupportedCipherSuites(String[] supportedCipherSuites) {
        this.supportedCipherSuites = Arrays.copyOf(supportedCipherSuites, supportedCipherSuites.length);
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = Objects.requireNonNull(hostnameVerifier, "hostnameVerifier");
    }

    public void setTrustManagerFactory(TrustManagerFactory trustManagerFactory) {
        this.trustManagerFactory = trustManagerFactory;
    }

    public void setKeyManagerFactory(KeyManagerFactory keyManagerFactory) {
        this.keyManagerFactory = keyManagerFactory;
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
        return Arrays.copyOf(supportedProtocols, supportedProtocols.length);
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return Arrays.copyOf(supportedCipherSuites, supportedCipherSuites.length);
    }

    @Override
    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }



}
