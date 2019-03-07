package com.imperva.shcf4j.java.http.client.config;

import com.imperva.shcf4j.SyncClientBaseTest;
import com.imperva.shcf4j.config.RequestConfigTest;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class SyncJavaHttpClientRequestConfigTest extends RequestConfigTest implements SyncClientBaseTest {

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }



    @Override
    public void requestStoreCookieWithClientContextTest() {
        //Currently not supported by JDK11 HTTP client
    }
}
