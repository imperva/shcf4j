package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.auth.AuthScope;
import com.imperva.shcf4j.auth.Credentials;
import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.config.CookieSpecs;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.client.protocol.ClientContext;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.SystemDefaultCredentialsProvider;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class ConversionUtils {


    static org.apache.http.HttpHost convert(com.imperva.shcf4j.HttpHost httpHost) {
        return httpHost != null ?
                new org.apache.http.HttpHost(httpHost.getHostname(), httpHost.getPort(), httpHost.getSchemeName())
                : null;
    }

    static org.apache.http.protocol.HttpContext convert(ClientContext ctx) {

        if (ctx != null) {

            // re-using already existing original context
            HttpClientContext httpContext =
                    ctx.getAttribute(HttpClientContext.class.getCanonicalName(), HttpClientContext.class);

            if (httpContext == null) {
                httpContext = HttpClientContext.create();
                ctx.setAttribute(HttpClientContext.class.getCanonicalName(), httpContext);
            }

            if (ctx.getRequestConfig() != null) {
                httpContext.setRequestConfig(convert(ctx.getRequestConfig()));
            }

            if (ctx.getCredentialsProvider() != null) {
                httpContext.setCredentialsProvider(convert(ctx.getCredentialsProvider()));
            }

            return httpContext;
        }

        return null;
    }

    static org.apache.http.client.config.RequestConfig convert(RequestConfig config) {
        return org.apache.http.client.config.RequestConfig.custom()
                .setExpectContinueEnabled(config.isExpectContinueEnabled())
                .setProxy(convert(config.getProxy()))
                .setLocalAddress(config.getLocalAddress())
                .setCookieSpec(convert(config.getCookieSpec()))
                .setRedirectsEnabled(config.isRedirectsEnabled())
                .setRelativeRedirectsAllowed(config.isRelativeRedirectsAllowed())
                .setCircularRedirectsAllowed(config.isCircularRedirectsAllowed())
                .setMaxRedirects(config.getMaxRedirects())
                .setAuthenticationEnabled(config.isAuthenticationEnabled())
                .setTargetPreferredAuthSchemes(config.getTargetPreferredAuthSchemes())
                .setProxyPreferredAuthSchemes(config.getProxyPreferredAuthSchemes())
                .setConnectionRequestTimeout(config.getConnectionRequestTimeoutMilliseconds())
                .setConnectTimeout((config.getConnectTimeoutMilliseconds()))
                .setSocketTimeout(config.getSocketTimeoutMilliseconds())
                .build();
    }

    static String convert(CookieSpecs cookieSpecs) {
        switch (cookieSpecs) {
            case STANDARD_RFC_6265:
                return org.apache.http.client.config.CookieSpecs.STANDARD;
            case IGNORE_COOKIES:
                return org.apache.http.client.config.CookieSpecs.IGNORE_COOKIES;
            default:
                return org.apache.http.client.config.CookieSpecs.DEFAULT;
        }
    }


    static org.apache.http.HttpRequest convert(HttpRequest request) {
        return HttpComponentsRequestFactory.createHttpComponentsRequest(request);
    }

    static HttpResponse convert(org.apache.http.HttpResponse response) {
        return new HttpResponseImpl(response);
    }


    static org.apache.http.client.CredentialsProvider convert(CredentialsProvider cp) {
        org.apache.http.client.CredentialsProvider credentialsProvider =
                new SystemDefaultCredentialsProvider();

        for (Map.Entry<AuthScope, Credentials> e : cp.getCredentials().entrySet()) {
            AuthScope authScope = e.getKey();
            Credentials credentials = e.getValue();
            credentialsProvider.setCredentials(
                    new org.apache.http.auth.AuthScope(authScope.getHost(), authScope.getPort(), authScope.getRealm(), authScope.getScheme()),
                    new CredentialsAdapter(credentials));
        }

        return credentialsProvider;
    }

    static List<Header> convert(org.apache.http.Header[] headers) {
        return Arrays
                .stream(headers)
                .map(h -> Header.builder().name(h.getName()).value(h.getValue()).build())
                .collect(Collectors.toList());
    }
}