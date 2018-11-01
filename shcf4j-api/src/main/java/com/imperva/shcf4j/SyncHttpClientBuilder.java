package com.imperva.shcf4j;

import com.imperva.shcf4j.client.SyncHttpClient;
import com.imperva.shcf4j.config.SocketConfig;

/**
 * <b>SyncHttpClientBuilder</b>
 *
 * @author maxim.kirilov
 */
public interface SyncHttpClientBuilder extends HttpClientCommonBuilder<SyncHttpClientBuilder> {


    SyncHttpClientBuilder setDefaultSocketConfig(SocketConfig socketConfig);

    SyncHttpClient build();

}
