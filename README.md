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