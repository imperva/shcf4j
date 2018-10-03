package com.imperva.shcf4j.concurrent;

public interface FutureCallback<T> {

    /**
     * Called during task cancel
     */
    default void cancelled() {}

    /**
     * Called during task completion.
     *
     * @param result
     */
    default void completed(T result) {}

    /**
     * Called during task failure.
     *
     * @param ex
     */
    default void failed(Exception ex) {}
}
