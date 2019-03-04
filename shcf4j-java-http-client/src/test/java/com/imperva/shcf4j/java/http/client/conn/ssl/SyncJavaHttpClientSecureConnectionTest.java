package com.imperva.shcf4j.java.http.client.conn.ssl;

import com.imperva.shcf4j.SyncClientBaseTest;
import com.imperva.shcf4j.conn.ssl.SecureConnectionTest;
import com.imperva.shcf4j.request.HttpMethodsTest;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class SyncJavaHttpClientSecureConnectionTest extends SecureConnectionTest implements SyncClientBaseTest {

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }



}
