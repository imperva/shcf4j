package com.imperva.shcf4j.config;

import lombok.Builder;
import lombok.Value;

/**
 * <b>SocketConfig</b>
 *
 *
 * @author maxim.kirilov
 */
@Builder
@Value
public final class SocketConfig {

    /**
     * Determines the default socket timeout value for non-blocking I/O operations.
     *
     * Default: <code>0</code> (no timeout)
     *
     * @see java.net.SocketOptions#SO_TIMEOUT
     */
    @Builder.Default
    private final int soTimeoutMilliseconds = 0;

    /**
     * Determines the default value of the {@link java.net.SocketOptions#SO_REUSEADDR} parameter
     * for newly created sockets.
     *
     * Default: <code>false</code>
     *
     * @see java.net.SocketOptions#SO_REUSEADDR
     */
    @Builder.Default
    private final boolean soReuseAddress = false;

    /**
     * Determines the default value of the {@link java.net.SocketOptions#SO_LINGER} parameter
     * for newly created sockets.
     *
     * Default: <code>-1</code>
     *
     * @see java.net.SocketOptions#SO_LINGER
     */
    @Builder.Default
    private int soLingerSeconds = -1;

    /**
     * Determines the default value of the {@link java.net.SocketOptions#SO_KEEPALIVE} parameter
     * for newly created sockets.
     *
     * Default: <code>-1</code>
     *
     * @see java.net.SocketOptions#SO_KEEPALIVE
     */
    private final boolean soKeepAlive;

    /**
     * Determines the default value of the {@link java.net.SocketOptions#TCP_NODELAY} parameter
     * for newly created sockets.
     *
     * Default: <code>false</code>
     *
     * @see java.net.SocketOptions#TCP_NODELAY
     */
    @Builder.Default
    private final boolean tcpNoDelay = false;

}
