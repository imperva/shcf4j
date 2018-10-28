package com.imperva.shcf4j.example.di.spring;

import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.client.HttpClient;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class BootstrapHttpClientFromXmlFile {


    public static void main(String[] args) throws Exception {

        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("com/imperva/shcf4j/example/di/spring/httpClientConfigurationContext.xml");


        HttpClient httpClient = ctx.getBean("exampleSyncHttpClient", HttpClient.class);


        httpClient.execute(
                HttpHost.builder().schemeName("https").hostname("github.com").port(443).build(),
                HttpRequest.createGetRequest("/imperva/shcf4j"),
                response -> {
                    System.out.println(response.getStatusLine());
                    return response.getStatusLine().getStatusCode() == 200;
                });


    }
}
