package com.imperva.shcf4j.conn.ssl;

import javax.net.ssl.TrustManagerFactory;

/**
 * <b>InsecureTrustManagerFactory</b>
 *
 * <p>
 * An insecure {@link TrustManagerFactory} that trusts all X.509 certificates
 * without any verification.
 * </p>
 *
 * @author maxim.kirilov
 */
public class InsecureTrustManagerFactory extends TrustManagerFactory {

    public static final TrustManagerFactory INSTANCE = new InsecureTrustManagerFactory();

    private InsecureTrustManagerFactory() {
        super(new InsecureTrustManagerFactorySpi(), null, null);
    }
}
