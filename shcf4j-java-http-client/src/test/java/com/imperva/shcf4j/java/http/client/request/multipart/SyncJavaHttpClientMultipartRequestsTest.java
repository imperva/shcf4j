package com.imperva.shcf4j.java.http.client.request.multipart;

import com.imperva.shcf4j.SyncClientBaseTest;
import com.imperva.shcf4j.request.multipart.MultipartRequestsTest;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class SyncJavaHttpClientMultipartRequestsTest extends MultipartRequestsTest implements SyncClientBaseTest {

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}
