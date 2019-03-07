package com.imperva.shcf4j.java.http.client;

import com.imperva.shcf4j.AsyncClientBaseTest;
import com.imperva.shcf4j.request.HttpMethodsTest;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class AsyncJavaHttpClientMethodsTest extends HttpMethodsTest implements AsyncClientBaseTest {

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @Override
    public void basicAuthenticationTest() {
        //A specific authenticator cannot be customized per request just for the entire client
    }


}
