package com.imperva.shcf4j.conn.routing;

import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpMessage;

/**
 * Encapsulates logic to compute a {@link HttpHost} to a target host.
 * Implementations may for example be based on parameters, or on the
 * standard Java system properties.
 *
 * Implementations of this interface must be thread-safe. Access to shared
 * data must be synchronized as methods of this interface may be executed
 * from multiple threads.
 *
 */
public interface HttpRoutePlanner {


    /**
     * Determines the route for a request.
     *
     * @param target    the target host for the request.
     *                  Implementations may accept <code>null</code>
     *                  if they can still determine a route, for example
     *                  to a default target or by inspecting the request.
     * @param httpMessage   the request to execute
     *
     * @return  the route that the request should take
     *
     */
    HttpHost determineRoute(HttpHost target, HttpMessage httpMessage);
}
