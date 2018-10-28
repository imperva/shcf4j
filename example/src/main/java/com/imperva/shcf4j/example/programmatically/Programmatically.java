package com.imperva.shcf4j.example.programmatically;

import com.imperva.shcf4j.HttpClientBuilderFactory;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.client.HttpClient;

public class Programmatically {


    public static void main(String[] args) throws Exception {

        HttpClient httpClient = HttpClientBuilderFactory
                .getHttpClientBuilder()
                .build();

           httpClient.execute(
                        HttpHost.builder().schemeName("https").hostname("github.com").port(443).build(),
                        HttpRequest.createGetRequest("/imperva/shcf4j"),
                        response -> {
                            System.out.println(response.getStatusLine());
                            return response.getStatusLine().getStatusCode() == 200;
                        });

    }
}
