package com.imperva.shcf4j.nio.reactor;


import lombok.Builder;
import lombok.Value;

/**
 * <b>IOReactorConfig</b>
 *
 * <p>
 * I/O reactor configuration parameters.
 * </p>
 *
 * @author maxim.kirilov
 */
@Builder
@Value
public class IOReactorConfig {

    private static final int AVAIL_PROCS = Runtime.getRuntime().availableProcessors();

    @Builder.Default
    private final long selectIntervalMilliseconds = 1000;

    @Builder.Default
    private final long shutdownGracePeriod = 500;

    @Builder.Default
    private final boolean interestOpQueued = false;

    @Builder.Default
    private final int ioThreadCount = AVAIL_PROCS;

    @Builder.Default
    private final int soTimeoutMilliseconds = 0;

    @Builder.Default
    private final boolean soReuseAddress = false;

    @Builder.Default
    private final int soLingerSeconds = -1;

    @Builder.Default
    private final boolean soKeepAlive = false;

    @Builder.Default
    private final boolean tcpNoDelay = true;

    @Builder.Default
    private final int connectTimeoutMilliseconds = 0;

    @Builder.Default
    private final int sndBufSize = 0;

    @Builder.Default
    private final int rcvBufSize = 0;


    /**
     * <p>
     * Determines time interval in milliseconds at which the I/O reactor wakes up to check for
     * timed out sessions and session requests.
     * </p>
     * <p>
     * Default: <code>1000</code> milliseconds.
     *
     * @return the time interval in milliseconds at which the I/O reactor wakes up to check for timed out sessions and session requests
     */
    public long getSelectIntervalMilliseconds() {
        return this.selectIntervalMilliseconds;
    }


    /**
     * <p>
     * Determines grace period in milliseconds the I/O reactors are expected to block waiting
     * for individual worker threads to terminate cleanly.
     * </p>
     * <p>
     * Default: <code>500</code> milliseconds.
     *
     * @return the grace period in milliseconds the I/O reactors are expected to block waiting for individual worker threads to terminate cleanly.
     */
    public long getShutdownGracePeriod() {
        return this.shutdownGracePeriod;
    }

    /**
     * <p>
     * Determines whether or not I/O interest operations are to be queued and executed
     * asynchronously by the I/O reactor thread or to be applied to the underlying
     * {@link java.nio.channels.SelectionKey} immediately.
     * </p>
     * <p>
     * Default: <code>false</code>
     *
     * @return whether or not I/O interest operations are to be queued and executed asynchronously by the I/O reactor thread or to be applied to the underlying {@link java.nio.channels.SelectionKey} immediately.
     * @see java.nio.channels.SelectionKey
     * @see java.nio.channels.SelectionKey#interestOps()
     * @see java.nio.channels.SelectionKey#interestOps(int)
     */
    public boolean isInterestOpQueued() {
        return this.interestOpQueued;
    }

    /**
     * <p>
     * Determines the number of I/O dispatch threads to be used by the I/O reactor.
     * </p>
     * <p>
     * Default: <code>2</code>
     *
     * @return the number of I/O dispatch threads to be used by the I/O reactor.
     */
    public int getIoThreadCount() {
        return this.ioThreadCount;
    }


    /**
     * <p>
     * Determines the default socket timeout value for non-blocking I/O operations.
     * </p>
     * <p>
     * Default: <code>0</code> (no timeout)
     *
     * @return the default socket timeout value for non-blocking I/O operations.
     * @see java.net.SocketOptions#SO_TIMEOUT
     */
    public int getSoTimeoutMilliseconds() {
        return soTimeoutMilliseconds;
    }


    /**
     * <p>
     * Determines the default value of the {@link java.net.SocketOptions#SO_REUSEADDR} parameter
     * for newly created sockets.
     * </p>
     * Default: <code>false</code>
     *
     * @return the default value of the {@link java.net.SocketOptions#SO_REUSEADDR} parameter
     * @see java.net.SocketOptions#SO_REUSEADDR
     */
    public boolean isSoReuseAddress() {
        return soReuseAddress;
    }

    /**
     * <p>
     * Determines the default value of the {@link java.net.SocketOptions#SO_LINGER} parameter
     * for newly created sockets.
     * </p>
     * <p>
     * Default: <code>-1</code>
     *
     * @return the default value of the {@link java.net.SocketOptions#SO_LINGER} parameter for newly created sockets.
     * @see java.net.SocketOptions#SO_LINGER
     */
    public int getSoLingerSeconds() {
        return soLingerSeconds;
    }

    /**
     * <p>
     * Determines the default value of the {@link java.net.SocketOptions#SO_KEEPALIVE} parameter
     * for newly created sockets.
     * </p>
     * <p>
     * Default: <code>-1</code>
     *
     * @return the default value of the {@link java.net.SocketOptions#SO_KEEPALIVE} parameter for newly created sockets.
     * @see java.net.SocketOptions#SO_KEEPALIVE
     */
    public boolean isSoKeepalive() {
        return this.soKeepAlive;
    }

    /**
     * <p>
     * Determines the default value of the {@link java.net.SocketOptions#TCP_NODELAY} parameter
     * for newly created sockets.
     * </p>
     * <p>
     * Default: <code>false</code>
     *
     * @return the default value of the {@link java.net.SocketOptions#TCP_NODELAY} parameter for newly created sockets.
     * @see java.net.SocketOptions#TCP_NODELAY
     */
    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    /**
     * <p>
     * Determines the default connect timeout value for non-blocking connection requests.
     * </p>
     * <p>
     * Default: <code>0</code> (no timeout)
     *
     * @return the default connect timeout value for non-blocking connection requests.
     */
    public int getConnectTimeoutMilliseconds() {
        return connectTimeoutMilliseconds;
    }


    /**
     * <p>
     * Determines the default value of the {@link java.net.SocketOptions#SO_SNDBUF} parameter
     * for newly created sockets.
     * </p>
     * <p>
     * Default: <code>0</code> (system default)
     *
     * @return the default value of the {@link java.net.SocketOptions#SO_SNDBUF} parameter for newly created sockets.
     * @see java.net.SocketOptions#SO_SNDBUF
     */
    public int getSndBufSize() {
        return sndBufSize;
    }

    /**
     * <p>
     * Determines the default value of the {@link java.net.SocketOptions#SO_RCVBUF} parameter
     * for newly created sockets.
     * </p>
     * <p>
     * Default: <code>0</code> (system default)
     *
     * @return the default value of the {@link java.net.SocketOptions#SO_RCVBUF} parameter for newly created sockets.
     * @see java.net.SocketOptions#SO_RCVBUF
     */
    public int getRcvBufSize() {
        return rcvBufSize;
    }

}
