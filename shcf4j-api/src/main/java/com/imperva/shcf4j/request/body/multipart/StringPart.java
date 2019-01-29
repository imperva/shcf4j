package com.imperva.shcf4j.request.body.multipart;

import lombok.Getter;

@Getter
public class StringPart extends PartBase {

    /**
     * Contents of this StringPart.
     */
    private final String value;

    StringPart(StringPartBuilder builder){
        super(builder);
        this.value = builder.value;
    }

    @Override
    public String getTransferEncoding() {
        return super.getTransferEncoding() != null ? super.getTransferEncoding() : MIME.ENC_8BIT;
    }
}
