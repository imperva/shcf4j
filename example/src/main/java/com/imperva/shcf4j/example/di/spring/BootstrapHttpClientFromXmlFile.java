package com.imperva.shcf4j.example.di.spring;

import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequestBuilder;
import com.imperva.shcf4j.client.SyncHttpClient;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class BootstrapHttpClientFromXmlFile {


    public static void main(String[] args) throws Exception {

        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("com/imperva/shcf4j/example/di/spring/httpClientConfigurationContext.xml");


        SyncHttpClient syncHttpClient = ctx.getBean("exampleSyncHttpClient", SyncHttpClient.class);


        syncHttpClient.execute(
                HttpHost.builder().schemeName("https").hostname("github.com").port(443).build(),
                HttpRequestBuilder.GET().uri("/imperva/shcf4j").build(),
                response -> {
                    System.out.println(response.getStatusLine());
                    return response.getStatusLine().getStatusCode() == 200;
                });


    }
}
