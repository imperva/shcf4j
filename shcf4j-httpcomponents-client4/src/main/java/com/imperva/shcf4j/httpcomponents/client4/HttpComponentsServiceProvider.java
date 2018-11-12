package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.AsyncHttpClientBuilder;
import com.imperva.shcf4j.SyncHttpClientBuilder;
import com.imperva.shcf4j.spi.SHC4JServiceProvider;


/**
 * <b>HttpComponentsServiceProvider</b>
 *
 * @author maxim.kirilov
 */
public class HttpComponentsServiceProvider implements SHC4JServiceProvider {

    @Override
    public SyncHttpClientBuilder getHttpClientBuilder() {
        return HttpClients.custom();
    }

    @Override
    public AsyncHttpClientBuilder getHttpAsyncClientBuilder() {
        return HttpAsyncClients.custom();
    }

}
