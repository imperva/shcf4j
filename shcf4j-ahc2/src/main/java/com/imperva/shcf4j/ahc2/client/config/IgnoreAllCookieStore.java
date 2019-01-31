package com.imperva.shcf4j.ahc2.client.config;

import io.netty.handler.codec.http.cookie.Cookie;
import org.asynchttpclient.cookie.CookieStore;
import org.asynchttpclient.uri.Uri;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * <b>IgnoreAllCookieStore
 *
 * <p>
 * An implementation of {@link CookieStore} that always return an empty list of cookies for any
 * requested URL.
 * </p>
 *
 * @author maxim.kirilov
 */
public class IgnoreAllCookieStore implements CookieStore {


    private static final CookieStore INSTANCE = new IgnoreAllCookieStore();

    private IgnoreAllCookieStore() {
    }

    public static CookieStore getInstance() {
        return INSTANCE;
    }

    @Override
    public void add(Uri uri, Cookie cookie) {

    }

    @Override
    public List<Cookie> get(Uri uri) {
        return Collections.emptyList();
    }

    @Override
    public List<Cookie> getAll() {
        return Collections.emptyList();
    }

    @Override
    public boolean remove(Predicate<Cookie> predicate) {
        return false;
    }

    @Override
    public boolean clear() {
        return false;
    }
}
