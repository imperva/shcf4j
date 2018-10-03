package com.imperva.shcf4j;

import org.junit.Assert;
import org.junit.Test;

/**
 * <b>HeaderTest</b>
 * <p/>
 * User: Maxim.Kirilov
 * Date: 04/05/2014
 */
public class HeaderTest {


    @Test
    public void legalCreationTest() {
        String name = "header name";
        String value = "header value";
        Header h = Header.builder().name(name).value(value).build();
        Assert.assertEquals("wrong header name", name, h.getName());
        Assert.assertEquals("wrong header value", value, h.getValue());

        h = Header.builder().name(name).build();
        Assert.assertEquals("wrong header name", name, h.getName());
        Assert.assertNull("not empty header value", h.getValue());
    }

    @Test(expected = Exception.class)
    public void illegalCreationTest() {
        Header h = Header.builder().build();
    }

    @Test
    public void equalsTest() {
        String name = "header name";
        String value = "header value";

        Header h1 = Header.builder().name(name).value(value).build();
        Header h2 = Header.builder().name(name).value(value).build();

        Assert.assertEquals("Headers aren't equals", h1, h2);
    }


}
