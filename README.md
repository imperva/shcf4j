# About SHCF4J
The Simple HTTP Client Facade for Java (SHCF4J) serves as a simple facade or abstraction for various HTTP client frameworks (e.g. java.net.HttpURLConnection, Apache HttpClient, etc.) allowing the end user to plug in the desired HTTP client framework at deployment time. This library idea originated from the The Simple Logging Facade for Java (SLF4J) progect. More information can be found on the [SLF4J website](http://www.slf4j.org).

# Build Status
[![Build Status](https://www.travis-ci.org/imperva/shcf4j.svg?branch=master)](https://www.travis-ci.org/imperva/shcf4j)
# Motivation
A large number of HTTP client libraries exists in the java community, while using more than one in the same project adds more completixy to the code. In Addition to learning curve that is prerequisite for each library. The SHCF4J provides an abstraction layer for various libraries, that can be desided upon deployment.

# Dependencies
JRE 8

# Use Cases
* As standard for internal libraries that used across different microservice and perform HTTP calls.
* Correct resource management for open HTTP connections. A content can be consumed only by provided callback - no easy way to expose an input stream.

# Code Examples
The HTTP client instances can be created in various ways. For example through any DI frameworks (see the example project) or just by putting a few lines of code:

```Java
HttpClient httpClient = HttpClientBuilderFactory
        .getHttpClientBuilder()
        .build();

```
The HTTP client instance can be customized with any supported configuration parameters (part of the shcf4j-api project). For example:

```Java
HttpClient httpClient = HttpClientBuilderFactory
        .getHttpClientBuilder()
        .setDefaultSocketConfig(
                SocketConfig
                        .builder()
                        .soTimeoutMilliseconds(5 * 1000)
                        .build()
        )
        .build();
```


## What Happens Behind the Scenes?
The factory class uses Java ```java.util.ServiceLoader``` in order to find all ```com.imperva.shcf4j.spi.SHC4JServiceProvider``` instances. Then it uses the first found instance for all following requests.
