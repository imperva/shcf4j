package com.imperva.shcf4j.java.http.client.request.multipart;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.NotSupportedException;
import com.imperva.shcf4j.ProcessingException;
import com.imperva.shcf4j.entity.ContentType;
import com.imperva.shcf4j.request.body.multipart.ByteArrayPart;
import com.imperva.shcf4j.request.body.multipart.FilePart;
import com.imperva.shcf4j.request.body.multipart.InputStreamPart;
import com.imperva.shcf4j.request.body.multipart.MIME;
import com.imperva.shcf4j.request.body.multipart.Part;
import com.imperva.shcf4j.request.body.multipart.StringPart;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Flow;

class PartsPublisher implements Flow.Publisher<ByteBuffer> {

    private final List<Part> parts = new LinkedList<>();
    private final List<Runnable> onComplete = new LinkedList<>();
    private final String boundary;

    PartsPublisher(List<? extends Part> parts, String boundary) {
        this.parts.addAll(parts);
        this.boundary = boundary;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super ByteBuffer> subscriber) {
        Objects.requireNonNull(subscriber, "subscriber");
        subscriber.onSubscribe(new PartsCreationSubscription(subscriber));
    }


    private class PartsCreationSubscription implements Flow.Subscription {

        private final Flow.Subscriber<? super ByteBuffer> subscriber;

        PartsCreationSubscription(Flow.Subscriber<? super ByteBuffer> subscriber) {
            this.subscriber = subscriber;
        }


        @Override
        public void request(long n) {
            for (Part p : parts) {
                subscriber.onNext(extractPartHeader(p));
                for (ByteBuffer bb : extractPartPayload(p)) {
                    subscriber.onNext(bb);
                }
            }
            subscriber.onNext(createFinalBoundary());
            subscriber.onComplete();

            onComplete();
        }

        @Override
        public void cancel() {
            onComplete();
        }

        private void onComplete() {
            for (Runnable r : onComplete) {
                r.run();
            }
        }


        private ByteBuffer extractPartHeader(Part p) {
            StringBuilder sb = new StringBuilder();
            String dispositionType = p.getDispositionType() != null ? p.getDispositionType() : "form-data";
            sb.append("--").append(boundary).append("\r\n");
            sb.append(MIME.CONTENT_DISPOSITION).append(": ").append(dispositionType).append("; name=\"").append(p.getName()).append("\"").append("\r\n");
            sb.append(MIME.CONTENT_TRANSFER_ENC).append(": ").append(p.getTransferEncoding()).append("\r\n");
            sb.append(convertToStringHttpHeader(p.getContentType())).append("\r\n");
            if (!p.getCustomHeaders().isEmpty()){
                for (Header h : p.getCustomHeaders()){
                    sb.append(h.getName()).append(": ").append(h.getValue()).append("\r\n");
                }
            }
            sb.append("\r\n");
            return ByteBuffer.wrap(sb.toString().getBytes(StandardCharsets.UTF_8));
        }

        private List<ByteBuffer> extractPartPayload(Part p) {
            if (p instanceof StringPart) {
                StringPart sp = (StringPart) p;
                StringBuilder sb = new StringBuilder();
                sb.append(sp.getValue());
                return List.of(ByteBuffer.wrap(sb.toString().getBytes(StandardCharsets.UTF_8)), createPartEnd());
            } else if (p instanceof ByteArrayPart) {
                ByteArrayPart bap = (ByteArrayPart) p;
                return List.of(ByteBuffer.wrap(bap.getBytes()), createPartEnd());
            } else if (p instanceof InputStreamPart) {
                InputStreamPart isp = (InputStreamPart) p;
                try {
                    return List.of(ByteBuffer.wrap(isp.getInputStream().readAllBytes()), createPartEnd());
                } catch (IOException ioException) {
                    throw new ProcessingException(ioException);
                }
            } else if (p instanceof FilePart) {
                FilePart fp = (FilePart) p;
                try {
                    FileChannel fc = FileChannel.open(fp.getFilePath(), StandardOpenOption.READ);
                    onComplete.add(() -> { // close the channel upon completion
                        try {
                            fc.close();
                        } catch (Exception e) {

                        }
                    });
                    MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                    buffer.load();
                    return List.of(buffer, createPartEnd());
                } catch (IOException ioException) {
                    throw new ProcessingException(ioException);
                }
            } else {
                throw new NotSupportedException("An unknown part type received: " + p);
            }
        }


        private ByteBuffer createPartEnd() {
            return ByteBuffer.wrap("\r\n".getBytes(StandardCharsets.UTF_8));
        }

        private ByteBuffer createFinalBoundary() {
            StringBuilder sb = new StringBuilder(20);
            sb.append("--").append(boundary).append("--");
            return ByteBuffer.wrap(sb.toString().getBytes(StandardCharsets.UTF_8));
        }


        private String convertToStringHttpHeader(ContentType contentType) {
            Objects.requireNonNull(contentType, "contentType");
            StringBuilder sb = new StringBuilder(20);
            sb.append("Content-Type: ").append(contentType.getMimeType());
            if (contentType.getCharset() != null) {
                sb.append("; charset=").append(contentType.getCharset());
            }
            return sb.toString();
        }

    }
}
