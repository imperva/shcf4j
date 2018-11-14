package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.AsyncHttpClientBuilder;
import com.imperva.shcf4j.SyncHttpClientBuilder;
import com.imperva.shcf4j.helpers.Util;
import com.imperva.shcf4j.spi.SHC4JServiceProvider;
import org.apache.http.util.VersionInfo;


/**
 * <b>HttpComponentsServiceProvider</b>
 *
 * @author maxim.kirilov
 */
public class HttpComponentsServiceProvider implements SHC4JServiceProvider {

    // to avoid constant folding by the compiler, this field must *not* be final
    public static String HTTP_COMPONENTS_SUPPORTED_VERSION = "4.5.6"; //   !final

    public HttpComponentsServiceProvider(){
        String httpComponentsRelease =
                VersionInfo.loadVersionInfo("org.apache.http.client", getClass().getClassLoader()).getRelease();

        if ( !HttpComponentsServiceProvider.HTTP_COMPONENTS_SUPPORTED_VERSION.equals(httpComponentsRelease) ){
            Util.report("The runtime version of HTTP COMPONENTS: "
                    + httpComponentsRelease + " not supported, " +
                    "please use version: " + HttpComponentsServiceProvider.HTTP_COMPONENTS_SUPPORTED_VERSION);
        }
    }


    @Override
    public SyncHttpClientBuilder getHttpClientBuilder() {
        return HttpClients.custom();
    }

    @Override
    public AsyncHttpClientBuilder getHttpAsyncClientBuilder() {
        return HttpAsyncClients.custom();
    }

}
