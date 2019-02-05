package com.imperva.shcf4j.example.di.spring;

import com.imperva.shcf4j.HttpClientBuilderFactory;
import com.imperva.shcf4j.client.SyncHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfiguration {

    @Bean
    public SyncHttpClient asyncHttpClient(){
        return HttpClientBuilderFactory.getHttpClientBuilder().build();
    }

}
