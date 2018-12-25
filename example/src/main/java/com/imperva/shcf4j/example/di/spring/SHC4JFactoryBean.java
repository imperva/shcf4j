package com.imperva.shcf4j.example.di.spring;

import com.imperva.shcf4j.MutableHttpRequest;
import com.imperva.shcf4j.SyncHttpClientBuilder;
import com.imperva.shcf4j.HttpClientBuilderFactory;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.SyncHttpClient;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.config.SocketConfig;
import com.imperva.shcf4j.conn.ssl.SSLSessionStrategy;
import org.springframework.beans.factory.FactoryBean;

import javax.net.ssl.SSLException;
import java.util.function.Consumer;


/**
 * <b>SHC4JFactoryBean</b>
 *
 * <p>
 * In order to connect between a builder pattern and spring bean creation a {@link FactoryBean}
 * interface must be implemented.
 * </p>
 *
 * @author maxim.kirilov
 */
public class SHC4JFactoryBean implements SyncHttpClientBuilder, FactoryBean<SyncHttpClient> {

    private final SyncHttpClientBuilder builder = HttpClientBuilderFactory.getHttpClientBuilder();


    @Override
    public SyncHttpClientBuilder setSSLSessionStrategy(SSLSessionStrategy strategy) throws SSLException {
        return builder.setSSLSessionStrategy(strategy);
    }

    @Override
    public SyncHttpClientBuilder setDefaultSocketConfig(SocketConfig socketConfig) {
        return builder.setDefaultSocketConfig(socketConfig);
    }

    @Override
    public SyncHttpClientBuilder setDefaultRequestConfig(RequestConfig config) {
        return builder.setDefaultRequestConfig(config);
    }

    @Override
    public SyncHttpClientBuilder setProxy(HttpHost proxy) {
        return builder.setProxy(proxy);
    }

    @Override
    public SyncHttpClientBuilder setDefaultCredentialsProvider(CredentialsProvider cp) {
        return builder.setDefaultCredentialsProvider(cp);
    }

    @Override
    public SyncHttpClientBuilder addRequestInterceptor(Consumer<MutableHttpRequest> interceptor) {
        return builder.addRequestInterceptor(interceptor);
    }

    @Override
    public SyncHttpClient build() {
        return builder.build();
    }


    @Override
    public SyncHttpClient getObject() {
        return builder.build();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public Class<?> getObjectType() {
        return SyncHttpClient.class;
    }
}
