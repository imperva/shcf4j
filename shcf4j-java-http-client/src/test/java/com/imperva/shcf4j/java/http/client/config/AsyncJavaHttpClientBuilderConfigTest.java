package com.imperva.shcf4j.java.http.client.config;

import com.imperva.shcf4j.AsyncClientBaseTest;
import com.imperva.shcf4j.NotSupportedException;
import com.imperva.shcf4j.SyncClientBaseTest;
import com.imperva.shcf4j.config.HttpClientBuilderConfigTest;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class AsyncJavaHttpClientBuilderConfigTest extends HttpClientBuilderConfigTest implements AsyncClientBaseTest {

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }


    @Override
    public void globalRequestTimeoutTest() {
        //Currently not supported by JDK11 HTTP client
        throw new NotSupportedException();
    }

    @Override
    public void requestInterceptorTest() {
        //Currently not supported by JDK11 HTTP client
    }

    @Override
    public void maxRedirectsExceededTest() {
        //Currently not supported by JDK11 HTTP client
        throw new NotSupportedException();
    }


    @Override
    public void basicAuthenticationSpecificScopeTest() {
    }


}
