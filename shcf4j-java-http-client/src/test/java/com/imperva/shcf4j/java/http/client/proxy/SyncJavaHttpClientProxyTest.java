package com.imperva.shcf4j.java.http.client.proxy;

import com.imperva.shcf4j.SyncClientBaseTest;
import com.imperva.shcf4j.proxy.ProxyTest;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class SyncJavaHttpClientProxyTest extends ProxyTest implements SyncClientBaseTest {

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}
