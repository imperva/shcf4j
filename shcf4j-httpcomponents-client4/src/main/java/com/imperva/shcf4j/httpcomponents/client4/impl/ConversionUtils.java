package com.imperva.shcf4j.httpcomponents.client4.impl;

import com.imperva.shcf4j.HttpEntity;
import com.imperva.shcf4j.HttpEntityEnclosingRequest;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.auth.AuthScope;
import com.imperva.shcf4j.auth.Credentials;
import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.client.protocol.ClientContext;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.SystemDefaultCredentialsProvider;

import java.util.Map;

public class ConversionUtils {


    public static org.apache.http.HttpHost convert(com.imperva.shcf4j.HttpHost httpHost){
        return httpHost != null ?
                new org.apache.http.HttpHost(httpHost.getHostname(), httpHost.getPort(), httpHost.getSchemeName())
                : null;
    }

    public static org.apache.http.protocol.HttpContext convert(ClientContext ctx) {

        if (ctx != null) {
            HttpClientContext httpContext = HttpClientContext.create();

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

    public static org.apache.http.client.config.RequestConfig convert(RequestConfig config){
        return org.apache.http.client.config.RequestConfig.custom()
                .setExpectContinueEnabled(config.isExpectContinueEnabled())
                .setProxy(convert(config.getProxy()))
                .setLocalAddress(config.getLocalAddress())
                .setStaleConnectionCheckEnabled(config.isStaleConnectionCheckEnabled())
                .setCookieSpec(config.getCookieSpec())
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

    public static org.apache.http.HttpRequest convert(HttpRequest request){

        org.apache.http.HttpRequest httpRequest;

        switch (request.getMethod()){
            case GET:
                httpRequest = new org.apache.http.client.methods.HttpGet(request.getUri());
                break;
            case POST:
                HttpPost postRequest = new HttpPost(request.getUri());
                if (request instanceof HttpEntityEnclosingRequest){
                    HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
                    postRequest.setEntity(new HttpEntityAdapter(entity));
                }
                httpRequest = postRequest;
                break;
            case PUT:
                HttpPut putRequest = new HttpPut(request.getUri());
                if (request instanceof HttpEntityEnclosingRequest){
                    HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
                    putRequest.setEntity(new HttpEntityAdapter(entity));
                }
                httpRequest = putRequest;
                break;
            case DELETE:
                httpRequest = new org.apache.http.client.methods.HttpDelete(request.getUri());
                break;
            default:
                throw new RuntimeException("Not supported HTTP method: " + request.getMethod());
        }

        request
                .getAllHeaders()
                .stream()
                .peek(h -> httpRequest.addHeader(h.getName(), h.getValue()))
                .count();


        return httpRequest;
    }

    public static HttpResponse convert(org.apache.http.HttpResponse response){
        return new HttpResponseImpl(response);
    }


    public static org.apache.http.client.CredentialsProvider convert(CredentialsProvider cp){
        org.apache.http.client.CredentialsProvider credentialsProvider =
                new SystemDefaultCredentialsProvider();

        for(Map.Entry<AuthScope, Credentials> e : cp.getCredentials().entrySet()){
            AuthScope authScope = e.getKey();
            Credentials credentials = e.getValue();
            credentialsProvider.setCredentials(
                    new org.apache.http.auth.AuthScope(authScope.getHost(), authScope.getPort(), authScope.getRealm(), authScope.getScheme()),
                    new CredentialsAdapter(credentials));
        }

        return credentialsProvider;
    }
}