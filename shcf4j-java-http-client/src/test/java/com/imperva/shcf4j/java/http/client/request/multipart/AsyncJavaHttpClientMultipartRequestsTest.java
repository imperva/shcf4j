package com.imperva.shcf4j.java.http.client.request.multipart;

import com.imperva.shcf4j.AsyncClientBaseTest;
import com.imperva.shcf4j.request.multipart.MultipartRequestsTest;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class AsyncJavaHttpClientMultipartRequestsTest extends MultipartRequestsTest implements AsyncClientBaseTest {

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}
