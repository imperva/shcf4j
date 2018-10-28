package com.imperva.shcf4j.conn.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.util.Objects;

/**
 * <b>SimpleSSLSessionStrategy</b>
 *
 * <p>
 *     A Simple implementation for {@link SSLSessionStrategy}
 * </p>
 *
 */
public class SimpleSSLSessionStrategy implements SSLSessionStrategy {


    private SSLContext sslContext;
    private String[] supportedProtocols;
    private String[] supportedCipherSuites;
    private HostnameVerifier hostnameVerifier;
    private KeyManager[] keyManagers = null;
    private TrustManager[] trustManagers = null;
    private boolean trustAllCerts = true;

    private static final TrustManager[] trustAllCertsTrustManager =
            new TrustManager[]{
                    new TrustAllCertsTrustManager() {
                    }};


    public void setKeyManagers(KeyManager[] keyManagers) {
        this.keyManagers = keyManagers;
    }

    public void setTrustManagers(TrustManager[] trustManagers) {
        this.trustManagers = trustManagers;
    }

    public void setSslContext(SSLContext sslContext) {
        this.sslContext = Objects.requireNonNull(sslContext, "sslContext");
    }

    public void setSupportedProtocols(String[] supportedProtocols) {
        this.supportedProtocols = supportedProtocols;
    }

    public void setSupportedCipherSuites(String[] supportedCipherSuites) {
        this.supportedCipherSuites = supportedCipherSuites;
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = Objects.requireNonNull(hostnameVerifier, "hostnameVerifier");
    }

    public void setTrustAllCerts(boolean trustAllCerts) {
        this.trustAllCerts = trustAllCerts;
    }

    @Override
    public SSLContext getSslContext() {
        return sslContext;
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


    public void initSslContext(){
        Objects.requireNonNull(sslContext, "sslContext");
        try {
            sslContext.init(keyManagers, trustAllCerts ? trustAllCertsTrustManager : trustManagers, null);
        } catch (java.security.KeyManagementException keyManagementEx) {
            throw new RuntimeException("Failed to create SSLSocketFactory, due to:", keyManagementEx);
        }
    }

}
