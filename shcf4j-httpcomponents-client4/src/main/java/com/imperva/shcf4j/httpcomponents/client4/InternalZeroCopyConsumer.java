package com.imperva.shcf4j.httpcomponents.client4;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.client.methods.ZeroCopyConsumer;

/**
 * Created by maxim.kirilov on 4/11/17.
 */
class InternalZeroCopyConsumer extends ZeroCopyConsumer<File> {

    public InternalZeroCopyConsumer(File file) throws FileNotFoundException {
        super(file);
    }

    @Override
    protected File process(
            final org.apache.http.HttpResponse response,
            final File file,
            final ContentType contentType) throws Exception {
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new ClientProtocolException("Upload failed: " + response.getStatusLine());
        }
        return file;
    }
}
