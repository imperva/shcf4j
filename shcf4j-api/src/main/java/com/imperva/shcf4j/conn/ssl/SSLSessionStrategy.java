package com.imperva.shcf4j.conn.ssl;


import javax.net.ssl.*;

/**
 * <b>SSLSessionStrategy</b>
 *
 * <p>
 *  TLS transport level security strategy.
 * </p>
 *
 * @author Maxim.Kirilov
 */
public interface SSLSessionStrategy {


    SSLContext getSslContext();

    String[] getSupportedProtocols();

    String[] getSupportedCipherSuites();

    X509HostnameVerifier getHostnameVerifier();

}
