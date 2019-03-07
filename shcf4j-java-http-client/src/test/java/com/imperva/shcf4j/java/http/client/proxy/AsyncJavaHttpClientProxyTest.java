package com.imperva.shcf4j.java.http.client.proxy;

import com.imperva.shcf4j.AsyncClientBaseTest;
import com.imperva.shcf4j.proxy.ProxyTest;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class AsyncJavaHttpClientProxyTest extends ProxyTest implements AsyncClientBaseTest {

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}
