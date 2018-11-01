package com.imperva.shcf4j.httpcomponents.client4.nio;

import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.httpcomponents.client4.HttpClientBaseTest;

/**
 */
abstract class AsyncHttpClientBaseTest extends HttpClientBaseTest {

    abstract AsyncHttpClient getHttpClient();
}
