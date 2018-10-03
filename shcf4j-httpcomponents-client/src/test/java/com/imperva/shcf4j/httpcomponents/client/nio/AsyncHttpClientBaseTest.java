package com.imperva.shcf4j.httpcomponents.client.nio;

import com.imperva.shcf4j.client.HttpAsyncClient;
import com.imperva.shcf4j.httpcomponents.client.HttpClientBaseTest;

/**
 */
abstract class AsyncHttpClientBaseTest extends HttpClientBaseTest {

    abstract HttpAsyncClient getHttpClient();
}
