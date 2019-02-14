package com.imperva.shcf4j.client.config;


import com.imperva.shcf4j.HttpHost;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.net.InetAddress;
import java.util.Collection;

/**
 * <b>RequestConfig</b>
 *
 * <p>
 * Holds configuration parameters for HTTP request.
 * </p>
 *
 * @author maxim.kirilov
 */
@Builder(toBuilder = true)
@Value
public final class RequestConfig {

    /**
     * Determines whether the 'Expect: 100-Continue' handshake is enabled for entity enclosing methods.
     */
    @Builder.Default
    private final boolean expectContinueEnabled = false;

    /**
     *  An HTTP proxy to be used for request execution.
     */
    private final HttpHost proxy;

    /**
     * A local address to be used for request execution.
     */
    private final InetAddress localAddress;

    /**
     * The name of the cookie specification to be used for HTTP state management.
     */
    @Builder.Default
    private final CookieSpecs cookieSpec = CookieSpecs.STANDARD_RFC_6265;

    /**
     * Determines whether redirects should be handled automatically.
     */
    @Builder.Default
    private final boolean redirectsEnabled = true;

    /**
     * Determines whether relative redirects should be rejected.
     */
    private final boolean relativeRedirectsAllowed;

    /**
     * Determines whether circular redirects (redirects to the same location) should be allowed.
     */
    private final boolean circularRedirectsAllowed;

    /**
     * The maximum number of redirects to be followed.
     */
    @Builder.Default
    private final int maxRedirects = 5;

    /**
     * Determines whether authentication should be handled automatically.
     */
    private final boolean authenticationEnabled;

    /**
     * Determines the order of preference for supported authentication schemes when authenticating with the target host.
     */
    @Singular("targetPreferredAuthScheme")
    private final Collection<String> targetPreferredAuthSchemes;

    /**
     * Determines the order of preference for supported authentication schemes when authenticating with the proxy host.
     */
    @Singular("proxyPreferredAuthScheme")
    private final Collection<String> proxyPreferredAuthSchemes;

    /**
     * The timeout in milliseconds used when requesting a connection from the connection manager.
     */
    @Builder.Default
    private final int connectionRequestTimeoutMilliseconds = -1;

    /**
     * Determines the timeout in milliseconds until a connection is established.
     */
    @Builder.Default
    private final int connectTimeoutMilliseconds = -1;

    /**
     * Defines the socket timeout (SO_TIMEOUT) in milliseconds, which is the timeout for waiting for data or, put differently, a maximum period inactivity between two consecutive data packets).
     * A negative timeout value is interpreted as an infinite timeout. If this parameter is not set, read operations will not time out (infinite timeout).
     * aka: Read timeout
     */
    @Builder.Default
    private final int socketTimeoutMilliseconds = -1;

}