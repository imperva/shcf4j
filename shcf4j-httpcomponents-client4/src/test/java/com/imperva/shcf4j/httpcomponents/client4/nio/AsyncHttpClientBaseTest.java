package com.imperva.shcf4j.httpcomponents.client4.nio;

import com.imperva.shcf4j.client.HttpAsyncClient;
import com.imperva.shcf4j.httpcomponents.client4.HttpClientBaseTest;

/**
 */
abstract class AsyncHttpClientBaseTest extends HttpClientBaseTest {

    abstract HttpAsyncClient getHttpClient();
}
