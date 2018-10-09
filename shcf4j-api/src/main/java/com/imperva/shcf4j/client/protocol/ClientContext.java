package com.imperva.shcf4j.client.protocol;

import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.config.RequestConfig;
import lombok.Builder;
import lombok.Value;


/**
 * <b>ClientContext</b>
 *
 * <p>
 * ClientContext represents execution state of an HTTP process. It is a structure
 * that can be used to map an attribute name to an attribute value.
 * </p>
 *
 * @author maxim.kirilov
 *
 */
@Builder(toBuilder = true)
@Value
public final class ClientContext {

    private final RequestConfig requestConfig;
    private final CredentialsProvider credentialsProvider;

}
