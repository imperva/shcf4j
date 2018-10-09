package com.imperva.shcf4j.httpcomponents.client4.ssl;

import com.imperva.shcf4j.conn.ssl.X509HostnameVerifier;

import javax.net.ssl.SSLException;

/**
 * <b>StrictHostnameVerifier</b>
 * <p>
 * The Strict HostnameVerifier works the same way as Sun Java 1.4, Sun
 * Java 5, Sun Java 6-rc.  It's also pretty close to IE6.  This
 * implementation appears to be compliant with RFC 2818 for dealing with
 * wildcards.
 * </p>
 * <p>
 * The hostname must match either the first CN, or any of the subject-alts.
 * A wildcard can occur in the CN, and in any of the subject-alts.  The
 * one divergence from IE6 is how we only check the first CN.  IE6 allows
 * a match against any of the CNs present.  We decided to follow in
 * Sun Java 1.4's footsteps and only check the first CN.  (If you need
 * to check all the CN's, feel free to write your own implementation!).
 * </p>
 * A wildcard such as "*.foo.com" matches only subdomains in the same
 * level, for example "a.foo.com".  It does not match deeper subdomains
 * such as "a.b.foo.com".
 *
 * @author Maxim.Kirilov
 */
public class StrictHostnameVerifier implements X509HostnameVerifier {

    private final org.apache.http.conn.ssl.X509HostnameVerifier verifier;

    public StrictHostnameVerifier() {
        this.verifier = new org.apache.http.conn.ssl.StrictHostnameVerifier();
    }

    @Override
    public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
        this.verifier.verify(host, cns, subjectAlts);
    }
}
