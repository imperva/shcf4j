package com.imperva.shcf4j.httpcomponents.client;

import com.imperva.shcf4j.client.HttpClient;

/**
 * Created with IntelliJ IDEA.
 * User: Maxim.Kirilov
 * Date: 12/23/14
 * Time: 6:30 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class SyncHttpClientBaseTest extends HttpClientBaseTest {

    abstract HttpClient getHttpClient();
}
