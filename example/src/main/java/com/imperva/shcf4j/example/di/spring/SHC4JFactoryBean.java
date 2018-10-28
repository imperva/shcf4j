package com.imperva.shcf4j.example.di.spring;

import com.imperva.shcf4j.HttpClientBuilder;
import com.imperva.shcf4j.HttpClientBuilderFactory;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.HttpClient;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.config.SocketConfig;
import com.imperva.shcf4j.conn.ssl.SSLSessionStrategy;
import org.springframework.beans.factory.FactoryBean;


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
public class SHC4JFactoryBean implements HttpClientBuilder, FactoryBean<HttpClient> {

    private final HttpClientBuilder builder = HttpClientBuilderFactory.getHttpClientBuilder();


    @Override
    public HttpClientBuilder setSSLSessionStrategy(SSLSessionStrategy strategy) {
        return builder.setSSLSessionStrategy(strategy);
    }

    @Override
    public HttpClientBuilder setDefaultSocketConfig(SocketConfig socketConfig) {
        return builder.setDefaultSocketConfig(socketConfig);
    }

    @Override
    public HttpClientBuilder setDefaultRequestConfig(RequestConfig config) {
        return builder.setDefaultRequestConfig(config);
    }

    @Override
    public HttpClientBuilder setProxy(HttpHost proxy) {
        return builder.setProxy(proxy);
    }

    @Override
    public HttpClientBuilder setDefaultCredentialsProvider(CredentialsProvider cp) {
        return builder.setDefaultCredentialsProvider(cp);
    }

    @Override
    public HttpClient build() {
        return builder.build();
    }


    @Override
    public HttpClient getObject() {
        return builder.build();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public Class<?> getObjectType() {
        return HttpClient.class;
    }
}
