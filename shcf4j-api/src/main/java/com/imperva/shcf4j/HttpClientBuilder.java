package com.imperva.shcf4j;

import com.imperva.shcf4j.client.HttpClient;
import com.imperva.shcf4j.config.SocketConfig;

/**
 * <b>HttpClientBuilder</b>
 *
 * @author maxim.kirilov
 */
public interface HttpClientBuilder extends HttpClientCommonBuilder<HttpClientBuilder> {


    HttpClientBuilder setDefaultSocketConfig(SocketConfig socketConfig);

    HttpClient build();

}
