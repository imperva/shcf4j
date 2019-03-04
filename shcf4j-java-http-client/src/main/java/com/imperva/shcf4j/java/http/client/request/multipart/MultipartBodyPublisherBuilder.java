package com.imperva.shcf4j.java.http.client.request.multipart;

import com.imperva.shcf4j.request.body.multipart.Part;

import java.net.http.HttpRequest;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * <b>MultipartBodyPublisherBuilder</b>
 *
 * @author maxim.kirilov
 */
public class MultipartBodyPublisherBuilder {

    /**
     * The pool of ASCII chars to be used for generating a multipart boundary.
     */
    private final static char[] MULTIPART_CHARS =
            "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private final List<Part> parts = new LinkedList<>();


    private final String boundary = generateBoundary();

    private MultipartBodyPublisherBuilder() {
    }

    public MultipartBodyPublisherBuilder parts(Collection<? extends Part> parts) {
        this.parts.addAll(parts);
        return this;
    }

    public MultipartBodyPublisherBuilder part(Part part) {
        this.parts.add(part);
        return this;
    }

    public String getBoundary() {
        return boundary;
    }

    public static MultipartBodyPublisherBuilder create() {
        return new MultipartBodyPublisherBuilder();
    }

    public HttpRequest.BodyPublisher build() {
        checkValidity();
        return HttpRequest.BodyPublishers.fromPublisher(new PartsPublisher(this.parts, this.boundary));
    }


    private void checkValidity() {
        if (this.parts.isEmpty()) {
            throw new IllegalStateException("At least pne part must be added prior to build invocation");
        }
    }

    private String generateBoundary() {
        final StringBuilder buffer = new StringBuilder();
        final Random rand = new Random();
        final int count = rand.nextInt(11) + 30; // a random size from 30 to 40
        for (int i = 0; i < count; i++) {
            buffer.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
        }
        return buffer.toString();
    }
}
