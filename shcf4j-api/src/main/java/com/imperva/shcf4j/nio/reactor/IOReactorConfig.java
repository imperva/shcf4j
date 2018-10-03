package com.imperva.shcf4j.nio.reactor;


import lombok.Builder;
import lombok.Value;

/**
 * <b>IOReactorConfig</b>
 * <p/>
 * I/O reactor configuration parameters.
 *
 * @author <font color="blue">Maxim Kirilov</font>
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
     * Determines time interval in milliseconds at which the I/O reactor wakes up to check for
     * timed out sessions and session requests.
     * <p/>
     * Default: <code>1000</code> milliseconds.
     */
    public long getSelectIntervalMilliseconds() {
        return this.selectIntervalMilliseconds;
    }


    /**
     * Determines grace period in milliseconds the I/O reactors are expected to block waiting
     * for individual worker threads to terminate cleanly.
     * <p/>
     * Default: <code>500</code> milliseconds.
     */
    public long getShutdownGracePeriod() {
        return this.shutdownGracePeriod;
    }

    /**
     * Determines whether or not I/O interest operations are to be queued and executed
     * asynchronously by the I/O reactor thread or to be applied to the underlying
     * {@link java.nio.channels.SelectionKey} immediately.
     * <p/>
     * Default: <code>false</code>
     *
     * @see java.nio.channels.SelectionKey
     * @see java.nio.channels.SelectionKey#interestOps()
     * @see java.nio.channels.SelectionKey#interestOps(int)
     */
    public boolean isInterestOpQueued() {
        return this.interestOpQueued;
    }

    /**
     * Determines the number of I/O dispatch threads to be used by the I/O reactor.
     * <p/>
     * Default: <code>2</code>
     */
    public int getIoThreadCount() {
        return this.ioThreadCount;
    }


    /**
     * Determines the default socket timeout value for non-blocking I/O operations.
     * <p/>
     * Default: <code>0</code> (no timeout)
     *
     * @see java.net.SocketOptions#SO_TIMEOUT
     */
    public int getSoTimeoutMilliseconds() {
        return soTimeoutMilliseconds;
    }


    /**
     * Determines the default value of the {@link java.net.SocketOptions#SO_REUSEADDR} parameter
     * for newly created sockets.
     * <p/>
     * Default: <code>false</code>
     *
     * @see java.net.SocketOptions#SO_REUSEADDR
     */
    public boolean isSoReuseAddress() {
        return soReuseAddress;
    }

    /**
     * Determines the default value of the {@link java.net.SocketOptions#SO_LINGER} parameter
     * for newly created sockets.
     * <p/>
     * Default: <code>-1</code>
     *
     * @see java.net.SocketOptions#SO_LINGER
     */
    public int getSoLingerSeconds() {
        return soLingerSeconds;
    }

    /**
     * Determines the default value of the {@link java.net.SocketOptions#SO_KEEPALIVE} parameter
     * for newly created sockets.
     * <p/>
     * Default: <code>-1</code>
     *
     * @see java.net.SocketOptions#SO_KEEPALIVE
     */
    public boolean isSoKeepalive() {
        return this.soKeepAlive;
    }

    /**
     * Determines the default value of the {@link java.net.SocketOptions#TCP_NODELAY} parameter
     * for newly created sockets.
     * <p/>
     * Default: <code>false</code>
     *
     * @see java.net.SocketOptions#TCP_NODELAY
     */
    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    /**
     * Determines the default connect timeout value for non-blocking connection requests.
     * <p/>
     * Default: <code>0</code> (no timeout)
     */
    public int getConnectTimeoutMilliseconds() {
        return connectTimeoutMilliseconds;
    }


    /**
     * Determines the default value of the {@link java.net.SocketOptions#SO_SNDBUF} parameter
     * for newly created sockets.
     * <p/>
     * Default: <code>0</code> (system default)
     *
     * @see java.net.SocketOptions#SO_SNDBUF
     */
    public int getSndBufSize() {
        return sndBufSize;
    }

    /**
     * Determines the default value of the {@link java.net.SocketOptions#SO_RCVBUF} parameter
     * for newly created sockets.
     * <p/>
     * Default: <code>0</code> (system default)
     *
     * @see java.net.SocketOptions#SO_RCVBUF
     */
    public int getRcvBufSize() {
        return rcvBufSize;
    }

}
