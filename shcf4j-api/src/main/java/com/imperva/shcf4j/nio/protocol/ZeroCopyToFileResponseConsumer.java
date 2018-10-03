package com.imperva.shcf4j.nio.protocol;

import java.nio.file.Path;


/**
 * <b>ZeroCopyConsumer</b>
 *
 * <p>
 *  A consumer that streams content entity enclosed in an HTTP response directly into a file
 *  without an intermediate in-memory buffer.
 * </p>
 *
 * This consumer can be useful for file downloads.
 *
 * @author maxim.kirilov
 *
 */
public interface ZeroCopyToFileResponseConsumer {

    Path getPath();

}
