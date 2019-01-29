package com.imperva.shcf4j.request.body.multipart;

import com.imperva.shcf4j.entity.ContentType;

import java.util.Objects;

/**
 * <b>StringPartBuilder</b>
 *
 * @author maxim.kirilov
 */
public class StringPartBuilder extends PartBuilder<StringPartBuilder> {

    protected String value;

    StringPartBuilder() {
        contentType(ContentType.createTextPlain()); // Default, in order to preserve behavior between different providers (which can use different defaults)
    }

    @Override
    public Part build() {
        Objects.requireNonNull(value, "value");
        return new StringPart(this);
    }


    public StringPartBuilder value(String value) {
        this.value = value;
        return this;
    }
}
