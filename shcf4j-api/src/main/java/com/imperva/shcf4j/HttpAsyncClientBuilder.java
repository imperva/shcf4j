package com.imperva.shcf4j;

import com.imperva.shcf4j.client.HttpAsyncClient;
import com.imperva.shcf4j.nio.reactor.IOReactorConfig;

import java.util.concurrent.ThreadFactory;

public interface HttpAsyncClientBuilder extends HttpClientCommonBuilder<HttpAsyncClientBuilder> {


    HttpAsyncClientBuilder setThreadFactory(final ThreadFactory threadFactory);


    HttpAsyncClientBuilder setDefaultSocketConfig(IOReactorConfig ioReactorConfig);


    HttpAsyncClient build();

}
