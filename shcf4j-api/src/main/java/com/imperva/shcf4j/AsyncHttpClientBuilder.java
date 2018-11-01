package com.imperva.shcf4j;

import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.nio.reactor.IOReactorConfig;

import java.util.concurrent.ThreadFactory;

public interface AsyncHttpClientBuilder extends HttpClientCommonBuilder<AsyncHttpClientBuilder> {


    AsyncHttpClientBuilder setThreadFactory(final ThreadFactory threadFactory);


    AsyncHttpClientBuilder setDefaultSocketConfig(IOReactorConfig ioReactorConfig);


    AsyncHttpClient build();

}
