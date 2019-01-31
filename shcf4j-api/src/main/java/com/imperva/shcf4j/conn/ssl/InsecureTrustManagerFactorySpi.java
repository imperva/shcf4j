package com.imperva.shcf4j.conn.ssl;

import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;
import javax.net.ssl.X509ExtendedTrustManager;
import java.security.KeyStore;

/**
 * <b>InsecureTrustManagerFactorySpi</b>
 *
 * @author maxim.kirilov
 */
class InsecureTrustManagerFactorySpi extends TrustManagerFactorySpi {


    private final static X509ExtendedTrustManager TRUST_MANAGER = new TrustAllCertsTrustManager();


    @Override
    protected void engineInit(KeyStore keyStore) {

    }

    @Override
    protected void engineInit(ManagerFactoryParameters managerFactoryParameters) {

    }

    @Override
    protected TrustManager[] engineGetTrustManagers() {
        return new TrustManager[]{TRUST_MANAGER};
    }
}
