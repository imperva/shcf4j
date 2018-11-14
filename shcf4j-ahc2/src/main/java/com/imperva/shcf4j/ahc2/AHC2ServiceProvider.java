package com.imperva.shcf4j.ahc2;

import com.imperva.shcf4j.AsyncHttpClientBuilder;
import com.imperva.shcf4j.SyncHttpClientBuilder;
import com.imperva.shcf4j.ahc2.client.async.AsyncAhcClientBuilder;
import com.imperva.shcf4j.helpers.Util;
import com.imperva.shcf4j.spi.SHC4JServiceProvider;
import org.asynchttpclient.config.AsyncHttpClientConfigDefaults;


/**
 * @author maxim.kirilov
 */
public class AHC2ServiceProvider implements SHC4JServiceProvider {

    // to avoid constant folding by the compiler, this field must *not* be final
    public static String AHC_SUPPORTED_VERSION = "2.6.0"; //   !final

    public AHC2ServiceProvider(){
        if ( !AsyncHttpClientConfigDefaults.AHC_VERSION.equals(AHC2ServiceProvider.AHC_SUPPORTED_VERSION)){
            Util.report("The runtime version of AHC: "
                    + AsyncHttpClientConfigDefaults.AHC_VERSION + " not supported, " +
            "please use version: " + AHC2ServiceProvider.AHC_SUPPORTED_VERSION);
        }
    }

    @Override
    public SyncHttpClientBuilder getHttpClientBuilder() {
        return null;
    }

    @Override
    public AsyncHttpClientBuilder getHttpAsyncClientBuilder() {
        return new AsyncAhcClientBuilder();
    }
}
