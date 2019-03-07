package com.imperva.shcf4j.java.http.client;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * <b>IgnoreAllCookieStore</b>
 *
 * <p>
 *     A {@link CookieStore} implementation which ignores all cookies
 * </p>
 *
 * @author maxim.kirilov
 */
public class IgnoreAllCookieStore implements CookieStore {


    static CookieStore INSTANCE = new IgnoreAllCookieStore();

    private IgnoreAllCookieStore() {

    }

    @Override
    public void add(URI uri, HttpCookie cookie) {

    }

    @Override
    public List<HttpCookie> get(URI uri) {
        return Collections.emptyList();
    }

    @Override
    public List<HttpCookie> getCookies() {
        return Collections.emptyList();
    }

    @Override
    public List<URI> getURIs() {
        return Collections.emptyList();
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        return false;
    }

    @Override
    public boolean removeAll() {
        return true;
    }
}
