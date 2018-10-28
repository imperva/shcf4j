package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.HttpAsyncClientBuilder;
import com.imperva.shcf4j.HttpClientBuilder;
import com.imperva.shcf4j.httpcomponents.client4.impl.client.HttpClients;
import com.imperva.shcf4j.httpcomponents.client4.impl.nio.client.HttpAsyncClients;
import com.imperva.shcf4j.spi.SHC4JServiceProvider;


/**
 * <b>HttpComponentsServiceProvider</b>
 *
 * @author maxim.kirilov
 */
public class HttpComponentsServiceProvider implements SHC4JServiceProvider {

    @Override
    public HttpClientBuilder getHttpClientBuilder() {
        return HttpClients.custom();
    }

    @Override
    public HttpAsyncClientBuilder getHttpAsyncClientBuilder() {
        return HttpAsyncClients.custom();
    }

}
