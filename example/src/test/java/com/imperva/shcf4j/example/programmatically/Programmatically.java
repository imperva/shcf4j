package com.imperva.shcf4j.example.programmatically;

import com.imperva.shcf4j.HttpClientBuilderFactory;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequestBuilder;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.client.SyncHttpClient;

import java.util.concurrent.CompletableFuture;

public class Programmatically {


    public static void main(String[] args) throws Exception {

        SyncHttpClient syncHttpClient =
                HttpClientBuilderFactory
                        .getHttpClientBuilder()
                        .build();

        syncHttpClient.execute(
                HttpHost.builder().schemeName("https").hostname("imperva.com").port(443).build(),
                HttpRequestBuilder.GET().uri("/").build(),
                response -> {
                    System.out.println(response.getStatusLine());
                    return response.getStatusLine().getStatusCode() == 200;
                });
        syncHttpClient.close();


        AsyncHttpClient asyncHttpClient =
                HttpClientBuilderFactory
                        .getHttpAsyncClientBuilder()
                        .build();

        CompletableFuture<HttpResponse> completableFuture =
                asyncHttpClient.execute(
                        HttpHost.builder().schemeName("https").hostname("imperva.com").port(443).build(),
                        HttpRequestBuilder.GET().uri("/").build()
                );

        completableFuture.thenApply(response -> {
            System.out.println(response.getStatusLine());
            return response.getStatusLine().getStatusCode() == 200;
        }).get();

        asyncHttpClient.close();

    }
}
