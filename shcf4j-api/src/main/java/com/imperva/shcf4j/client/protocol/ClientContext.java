package com.imperva.shcf4j.client.protocol;

import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.config.RequestConfig;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

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
 *
 */
@Builder(toBuilder = true)
@Value
public final class ClientContext {

    private final RequestConfig requestConfig;
    private final CredentialsProvider credentialsProvider;
    @Getter(AccessLevel.NONE)
    private final Map<String, Object> attributes = new ConcurrentHashMap<>();



    public <T> T getAttribute(String attributeName, Class<T> clazz){
        Object attr = attributes.get(attributeName);
        if (attr !=null ){
            return clazz.cast(attr);
        }
        return null;
    }

    public void setAttribute(final String attributeName, final Object obj) {
        Objects.requireNonNull(attributeName, "attributeName");
        Objects.requireNonNull(obj, "obj");
        attributes.put(attributeName, obj);
    }

}
