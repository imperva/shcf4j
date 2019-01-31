package com.imperva.shcf4j.client.protocol;

import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.config.RequestConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


/**
 * <b>ClientContext</b>
 *
 * <p>
 * ClientContext represents execution state of an HTTP process. It is a structure
 * that can be used to map an attribute name to an attribute value.
 * </p>
 *
 * @author maxim.kirilov
 */
public final class ClientContext {

    private final Map<String, Object> attributes = new ConcurrentHashMap<>();

    ClientContext(Map<String, Object> attributes) {
        this.attributes.putAll(attributes);
    }

    public RequestConfig getRequestConfig() {
        return getAttribute(RequestConfig.class.getName(), RequestConfig.class);
    }

    public CredentialsProvider getCredentialsProvider() {
        return getAttribute(CredentialsProvider.class.getName(), CredentialsProvider.class);
    }

    public <T> T getAttribute(String attributeName, Class<T> clazz) {
        Object attr = attributes.get(attributeName);
        if (attr != null) {
            return clazz.cast(attr);
        }
        return null;
    }

    public void setAttribute(final String attributeName, final Object obj) {
        Objects.requireNonNull(attributeName, "attributeName");
        Objects.requireNonNull(obj, "obj");
        attributes.put(attributeName, obj);
    }

    public static ClientContext.ClientContextBuilder builder() {
        return new ClientContext.ClientContextBuilder();
    }


    public static class ClientContextBuilder {

        private Map<String, Object> attributes = new HashMap<>();

        ClientContextBuilder() {
        }

        public ClientContext.ClientContextBuilder requestConfig(RequestConfig requestConfig) {
            this.attributes.put(RequestConfig.class.getName(), requestConfig);
            return this;
        }

        public ClientContext.ClientContextBuilder credentialsProvider(CredentialsProvider credentialsProvider) {
            this.attributes.put(CredentialsProvider.class.getName(), credentialsProvider);
            return this;
        }

        public ClientContext build() {
            return new ClientContext(attributes);
        }
    }

}
