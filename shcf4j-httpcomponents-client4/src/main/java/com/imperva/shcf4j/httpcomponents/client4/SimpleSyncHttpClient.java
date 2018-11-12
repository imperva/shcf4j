package com.imperva.shcf4j.httpcomponents.client4;

import org.apache.http.impl.client.CloseableHttpClient;

/**
 * <b>CloseableHttpClient</b>
 *
 * @author <font color="blue">Maxim Kirilov</font>
 *         Date: April 2014
 */
class SimpleSyncHttpClient extends ClosableSyncHttpClientBase {


    SimpleSyncHttpClient(CloseableHttpClient realClient) {
        super(realClient);
    }

}
