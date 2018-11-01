package com.imperva.shcf4j.httpcomponents.client4.impl.client;

import org.apache.http.impl.client.CloseableHttpClient;

/**
 * <b>CloseableHttpClient</b>
 *
 * @author <font color="blue">Maxim Kirilov</font>
 *         Date: April 2014
 */
class SimpleSyncHttpClient extends SyncHttpClientBase {


    SimpleSyncHttpClient(CloseableHttpClient realClient) {
        super(realClient);
    }

}
