package com.imperva.shcf4j.client.config;

import lombok.Builder;
import lombok.Value;

import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;

/**
 * <b>ConnectionConfig</b>
 *
 * @author maxim.kirilov
 */

@Builder
@Value
public class ConnectionConfig {

    private final int bufferSize;
    private final int fragmentSizeHint;
    private final Charset charset;
    private final CodingErrorAction malformedInputAction;
    private final CodingErrorAction unmappableInputAction;

}
