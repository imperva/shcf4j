package com.imperva.shcf4j.httpcomponents.client4;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * <b>URIBuilder</b>
 * <p/>
 * <p>
 * Builder for {@link java.net.URI} instances.
 * </p>
 *
 * @author <font color="blue">Maxim Kirilov</font>
 *         <p/>
 *         Date: April 2014
 */
public final class URIBuilder {

    /**
     * Adaptee
     */
    private final org.apache.http.client.utils.URIBuilder uriBuilder;


    /**
     * Constructs an empty instance.
     */
    private URIBuilder() {
        uriBuilder = new org.apache.http.client.utils.URIBuilder();
    }


    /**
     * Construct an instance from the string which must be a valid URI.
     *
     * @param uri a valid URI in string form
     * @throws java.net.URISyntaxException if the input is not a valid URI
     */
    private URIBuilder(String uri) throws URISyntaxException {
        uriBuilder = new org.apache.http.client.utils.URIBuilder(uri);
    }


    /**
     * Construct an instance from the provided URI.
     *
     * @param uri
     */
    private URIBuilder(URI uri) {
        uriBuilder = new org.apache.http.client.utils.URIBuilder(uri);
    }

    /**
     * Constructs an empty instance.
     */
    public static URIBuilder emptyInstance() {
        return new URIBuilder();
    }


    /**
     * Construct an instance from the string which must be a valid URI.
     *
     * @param uri a valid URI in string form
     * @throws java.net.URISyntaxException if the input is not a valid URI
     */
    public static URIBuilder valueOf(String uri) throws URISyntaxException {
        return new URIBuilder(uri);
    }

    /**
     * Construct an instance from the provided URI.
     *
     * @param uri
     */
    public static URIBuilder valueOf(final URI uri) {
        return new URIBuilder(uri);
    }

    /**
     * Builds a {@link URI} instance.
     */
    public URI build() throws URISyntaxException {
        return this.uriBuilder.build();
    }


    /**
     * Sets URI scheme.
     */
    public URIBuilder setScheme(final String scheme) {
        this.uriBuilder.setScheme(scheme);
        return this;
    }

    /**
     * Sets URI user info. The value is expected to be un escaped and may contain non ASCII
     * characters.
     */
    public URIBuilder setUserInfo(final String userInfo) {
        this.uriBuilder.setUserInfo(userInfo);
        return this;
    }

    /**
     * Sets URI user info as a combination of username and password. These values are expected to
     * be un escaped and may contain non ASCII characters.
     */
    public URIBuilder setUserInfo(final String username, final String password) {
        this.uriBuilder.setUserInfo(username, password);
        return this;
    }

    /**
     * Sets URI host.
     */
    public URIBuilder setHost(final String host) {
        this.uriBuilder.setHost(host);
        return this;
    }

    /**
     * Sets URI port.
     */
    public URIBuilder setPort(final int port) {
        this.uriBuilder.setPort(port);
        return this;
    }

    /**
     * Sets URI path. The value is expected to be un escaped and may contain non ASCII characters.
     */
    public URIBuilder setPath(final String path) {
        this.uriBuilder.setPath(path);
        return this;
    }

    /**
     * Removes URI query.
     */
    public URIBuilder removeQuery() {
        this.uriBuilder.removeQuery();
        return this;
    }


    /**
     * Sets URI query parameters. The parameter name / values are expected to be un escaped
     * and may contain non ASCII characters.
     * <p/>
     * Please note query parameters and custom query component are mutually exclusive. This method
     * will remove custom query if present.
     */
    public URIBuilder setParameters(Map<String, String> params) {

        List<NameValuePair> lNameValuePair = new LinkedList<NameValuePair>();
        for (Map.Entry<String, String> p : params.entrySet()) {
            lNameValuePair.add(new BasicNameValuePair(p.getKey(), p.getValue()));
        }
        this.uriBuilder.setParameters(lNameValuePair);
        return this;
    }

    /**
     * Adds URI query parameters. The parameter name / values are expected to be un escaped
     * and may contain non ASCII characters.
     * <p/>
     * Please note query parameters and custom query component are mutually exclusive. This method
     * will remove custom query if present.
     */
    public URIBuilder addParameters(Map<String, String> params) {
        List<NameValuePair> lNameValuePair = new LinkedList<NameValuePair>();
        for (Map.Entry<String, String> p : params.entrySet()) {
            lNameValuePair.add(new BasicNameValuePair(p.getKey(), p.getValue()));
        }
        this.uriBuilder.addParameters(lNameValuePair);
        return this;
    }


    /**
     * Adds parameter to URI query. The parameter name and value are expected to be un escaped
     * and may contain non ASCII characters.
     * <p/>
     * Please note query parameters and custom query component are mutually exclusive. This method
     * will remove custom query if present.
     */
    public URIBuilder addParameter(final String param, final String value) {
        this.uriBuilder.addParameter(param, value);
        return this;
    }

    /**
     * Sets parameter of URI query overriding existing value if set. The parameter name and value
     * are expected to be un escaped and may contain non ASCII characters.
     * <p/>
     * Please note query parameters and custom query component are mutually exclusive. This method
     * will remove custom query if present.
     */
    public URIBuilder setParameter(final String param, final String value) {
        this.uriBuilder.setParameter(param, value);
        return this;
    }

    /**
     * Clears URI query parameters.
     */
    public URIBuilder clearParameters() {
        this.uriBuilder.clearParameters();
        return this;
    }

    /**
     * Sets custom URI query. The value is expected to be un escaped and may contain non ASCII
     * characters.
     * <p/>
     * Please note query parameters and custom query component are mutually exclusive. This method
     * will remove query parameters if present.
     */
    public URIBuilder setCustomQuery(final String query) {
        this.uriBuilder.setCustomQuery(query);
        return this;
    }

    /**
     * Sets URI fragment. The value is expected to be un escaped and may contain non ASCII
     * characters.
     */
    public URIBuilder setFragment(final String fragment) {
        this.uriBuilder.setFragment(fragment);
        return this;
    }


    public boolean isAbsolute() {
        return this.uriBuilder.isAbsolute();
    }


    public boolean isOpaque() {
        return this.uriBuilder.isAbsolute();
    }

    public String getScheme() {
        return this.uriBuilder.getScheme();
    }

    public String getUserInfo() {
        return this.uriBuilder.getUserInfo();
    }

    public String getHost() {
        return this.uriBuilder.getHost();
    }

    public int getPort() {
        return this.uriBuilder.getPort();
    }

    public String getPath() {
        return this.uriBuilder.getPath();
    }

    public Map<String, String> getQueryParams() {

        List<NameValuePair> lNameValuePair = this.uriBuilder.getQueryParams();
        if (lNameValuePair == null || lNameValuePair.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, String> lAnswer = new HashMap<String, String>();
        for (NameValuePair nvp : lNameValuePair) {
            lAnswer.put(nvp.getName(), nvp.getValue());
        }
        return lAnswer;
    }

    public String getFragment() {
        return this.uriBuilder.getFragment();
    }

    @Override
    public String toString() {
        return this.uriBuilder.toString();
    }

}
