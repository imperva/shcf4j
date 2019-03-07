package com.imperva.shcf4j.java.http.client;

import com.imperva.shcf4j.SyncClientBaseTest;
import com.imperva.shcf4j.request.HttpMethodsTest;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class SyncJavaHttpClientMethodsTest extends HttpMethodsTest implements SyncClientBaseTest {

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @Override
    public void basicAuthenticationTest()  {
        //A specific authenticator cannot be customized per request just for the entire client
    }


}
