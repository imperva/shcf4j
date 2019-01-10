# SHCF4J [![Build Status](https://www.travis-ci.org/imperva/shcf4j.svg?branch=master)](https://www.travis-ci.org/imperva/shcf4j) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.imperva.shcf4j/shcf4j-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.imperva.shcf4j/shcf4j-api/) [![Coverage Status](https://coveralls.io/repos/github/imperva/shcf4j/badge.svg?branch=feature%2Fcoveralls-integration)](https://coveralls.io/github/imperva/shcf4j?branch=feature%2Fcoveralls-integration)
The Simple HTTP Client Facade for Java (SHCF4J) serves as a simple facade or abstraction for various HTTP client frameworks (e.g. java.net.HttpURLConnection, Apache HttpClient, etc.) allowing the end user to plug in the desired HTTP client framework at deployment time. This library idea originated from the The Simple Logging Facade for Java [(SLF4J)](http://www.slf4j.org) progect. While SLF4J provides ready to use Logger instances, SHCF4J provides HTTP client builders that used for actual client creation and abstracts under the hood the entire creation and adoption to underlying implementation.

# Motivation
A large number of HTTP client libraries exists in the java community, while using more than one in the same project adds more completixy to the code. In Addition to learning curve that is prerequisite for each library. The SHCF4J provides an abstraction layer for various libraries, that can be desided upon deployment.

In addition, SHCF4J enables cleaner code by declaring all possible exceptions as runtime ones, so its up to the developer decidions whether to handle them.

# Dependencies
* JRE 8

# Use Cases
* As standard for internal libraries that used across different microservice and perform HTTP calls.
* Correct resource management for open HTTP connections. A content can be consumed only by provided callback - no easy way to expose an input stream.
* Unified configuration for outgoing HTTPS requests like: the used protocol (TLSv1, TLSv1.1, etc.) & the used ciphers list. Can be very useful in applications that must have varios security compliancy.

# Supported Providers
* Apache HTTP Client 4.3+
* Asynchronous HTTP Client 2.6+ (AHC2)

# Code Examples
The HTTP client instances can be created in various ways. For example through any DI frameworks (see the example project) or just by putting a few lines of code.

### Synchronous Client

```Java
SyncHttpClient syncHttpClient = HttpClientBuilderFactory
        .getHttpClientBuilder()
        .build();

```

The HTTP client instance can be customized with any supported configuration parameters (part of the shcf4j-api project). For example:

```Java
SyncHttpClient syncHttpClient = HttpClientBuilderFactory
        .getHttpClientBuilder()
        .setDefaultSocketConfig(
                SocketConfig
                        .builder()
                        .soTimeoutMilliseconds(5 * 1000)
                        .build())
        .build();
```

Request Execution Example:

```Java
HttpHost host = HttpHost
                .builder()
                .schemeName("https")
                .hostname("imperva.com")
                .port(443)
                .build();

HttpRequest request = HttpRequestBuilder
        .GET(URI.create("/hello/world"))
        .header("Accept", "application/json")
        .build();

String response = httpClient.execute(host, request, r -> r.getEntity().getResponseBody(StandardCharsets.UTF_8));
```

### Asynchronous Client

```Java
AsyncHttpClient asyncHttpClient = HttpClientBuilderFactory
        .getHttpAsyncClientBuilder()
        .setDefaultSocketConfig(
                IOReactorConfig
                        .builder()
                        .connectTimeoutMilliseconds(1000)
                        .build())
        .setDefaultRequestConfig(
                RequestConfig
                        .builder()
                        .socketTimeoutMilliseconds(1000)
                        .build())
        .build();
```

Any request execution will immediately return a ``` java.util.concurrent.CompletableFuture<HttpResponse>``` object that can be manipulated in both synchronous & asynchronous ways.

```Java
CompletableFuture<HttpResponse> cf = asyncRequester.execute(host, request);
```


## What Happens Behind the Scenes?
The factory class uses Java ```java.util.ServiceLoader``` in order to find all ```com.imperva.shcf4j.spi.SHC4JServiceProvider``` instances. Then it uses the first found instance for all following requests.