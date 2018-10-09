package com.imperva.shcf4j;


import lombok.Builder;
import lombok.Value;

/**
 * <b>StatusLine</b>
 *
 * The first line of a Response message is the Status-Line, consisting
 * of the protocol version followed by a numeric status code and its
 * associated textual phrase, with each element separated by SP
 * characters. No CR or LF is allowed except in the final CRLF sequence.
 *
 * <pre>
 *     Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
 * </pre>
 *
 * @author maxim.kirilov
 */
@Builder
@Value
public class StatusLine {

    private final ProtocolVersion protocolVersion;
    private final int statusCode;
    private final String reasonPhrase;

}
