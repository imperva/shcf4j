package com.imperva.shcf4j.test;

import com.imperva.shcf4j.client.AsyncHttpClient;

/**
 */
abstract class AsyncHttpClientBaseTest extends HttpClientBaseTest {

    /**
     *
     * @return a new instance upon evey invocation
     */
    protected abstract AsyncHttpClient getHttpClient();
}
