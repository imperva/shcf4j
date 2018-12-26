package com.imperva.shcf4j;


/**
 * <b>ProcessingException</b>
 *
 * <p>
 *  The exception of this type is thrown during HTTP request or response processing,
 *  to signal a runtime processing failure
 * </p>
 *
 * @author maxim.kirilov
 */
public class ProcessingException extends RuntimeException {

    public ProcessingException(Throwable cause) {
        super(cause);
    }
}
