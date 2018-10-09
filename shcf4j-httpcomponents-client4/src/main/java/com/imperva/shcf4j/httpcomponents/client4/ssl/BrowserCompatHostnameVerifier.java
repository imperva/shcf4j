package com.imperva.shcf4j.httpcomponents.client4.ssl;

import com.imperva.shcf4j.conn.ssl.X509HostnameVerifier;

import javax.net.ssl.SSLException;

/**
 * <b>BrowserCompatHostnameVerifier</b>
 * <p>
 * The HostnameVerifier that works the same way as Curl and Firefox.
 * </p>
 * <p>
 * The hostname must match either the first CN, or any of the subject-alts.
 * A wildcard can occur in the CN, and in any of the subject-alts.
 * </p>
 * The only difference between BROWSER_COMPATIBLE and STRICT is that a wildcard
 * (such as "*.foo.com") with BROWSER_COMPATIBLE matches all subdomains,
 * including "a.b.foo.com".
 *
 * @author Maxim.Kirilov
 */
public class BrowserCompatHostnameVerifier implements X509HostnameVerifier {

    private final org.apache.http.conn.ssl.X509HostnameVerifier verifier;

    public BrowserCompatHostnameVerifier() {
        this.verifier = new org.apache.http.conn.ssl.BrowserCompatHostnameVerifier();
    }

    @Override
    public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
        verifier.verify(host, cns, subjectAlts);
    }
}