package com.imperva.shcf4j.java.http.client.conn.ssl;

import com.imperva.shcf4j.AsyncClientBaseTest;
import com.imperva.shcf4j.conn.ssl.SecureConnectionTest;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class AsyncJavaHttpClientSecureConnectionTest extends SecureConnectionTest implements AsyncClientBaseTest {

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }


}
