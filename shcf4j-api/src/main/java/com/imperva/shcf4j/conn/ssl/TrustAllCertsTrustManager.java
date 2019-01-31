package com.imperva.shcf4j.conn.ssl;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;
import java.net.Socket;
import java.security.cert.X509Certificate;

class TrustAllCertsTrustManager extends X509ExtendedTrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) {

    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) {

    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)  {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
