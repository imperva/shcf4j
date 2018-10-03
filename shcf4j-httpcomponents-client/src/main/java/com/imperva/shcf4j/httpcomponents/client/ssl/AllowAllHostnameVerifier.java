package com.imperva.shcf4j.httpcomponents.client.ssl;

import com.imperva.shcf4j.conn.ssl.X509HostnameVerifier;

import javax.net.ssl.SSLException;

/**
 * <b>AllowAllHostnameVerifier</b>
 * <p/>
 * <p>
 * The ALLOW_ALL HostnameVerifier essentially turns hostname verification off.
 * This implementation is a no-op, and never throws the SSLException.
 * </p>
 */
public class AllowAllHostnameVerifier implements X509HostnameVerifier {

    @Override
    public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
    }
}
