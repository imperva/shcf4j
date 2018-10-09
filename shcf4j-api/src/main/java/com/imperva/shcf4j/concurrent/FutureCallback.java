package com.imperva.shcf4j.concurrent;

public interface FutureCallback<T> {

    /**
     * Called during task cancel
     */
    default void cancelled() {}

    /**
     * Called during task completion.
     *
     * @param result the outcome of the execution
     */
    default void completed(T result) {}

    /**
     * Called during task failure.
     *
     * @param ex the raised exception that lead to execution failure
     */
    default void failed(Exception ex) {}
}
