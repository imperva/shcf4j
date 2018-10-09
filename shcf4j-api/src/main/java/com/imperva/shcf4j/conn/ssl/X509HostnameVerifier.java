package com.imperva.shcf4j.conn.ssl;

import javax.net.ssl.SSLException;

/**
 * <b>X509HostnameVerifier</b>
 * <p>
 * Interface for checking if a hostname matches the names stored inside the
 * server's X.509 certificate.
 * </p>
 *
 * @author maxim.kirilov
 */
@FunctionalInterface
public interface X509HostnameVerifier {


    /**
     * Checks to see if the supplied hostname matches any of the supplied CNs
     * or "DNS" Subject-Alts.  Most implementations only look at the first CN,
     * and ignore any additional CNs.  Most implementations do look at all of
     * the "DNS" Subject-Alts. The CNs or Subject-Alts may contain wildcards
     * according to RFC 2818.
     *
     * @param cns         CN fields, in order, as extracted from the X.509
     *                    certificate.
     * @param subjectAlts Subject-Alt fields of type 2 ("DNS"), as extracted
     *                    from the X.509 certificate.
     * @param host        The hostname to verify.
     * @throws SSLException if the verification process fails.
     */
    void verify(String host, String[] cns, String[] subjectAlts) throws SSLException;
}
