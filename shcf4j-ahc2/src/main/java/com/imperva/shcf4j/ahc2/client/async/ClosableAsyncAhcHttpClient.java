package com.imperva.shcf4j.ahc2.client.async;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.NotSupportedException;
import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.client.protocol.ClientContext;
import com.imperva.shcf4j.request.body.multipart.ByteArrayPart;
import com.imperva.shcf4j.request.body.multipart.FilePart;
import com.imperva.shcf4j.request.body.multipart.InputStreamPart;
import com.imperva.shcf4j.request.body.multipart.Part;
import com.imperva.shcf4j.request.body.multipart.StringPart;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Response;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;


/**
 * <b>AsyncNettyHttpClient</b>
 *
 * <p>
 * An Async HTTP client implementation {@link AsyncHttpClient} that backed by {@link org.asynchttpclient.AsyncHttpClient}
 * </p>
 *
 * @author maxim.kirilov
 */
class ClosableAsyncAhcHttpClient implements AsyncHttpClient {

    private final org.asynchttpclient.AsyncHttpClient asyncHttpClient;


    ClosableAsyncAhcHttpClient(org.asynchttpclient.AsyncHttpClient asyncHttpClient) {
        Objects.requireNonNull(asyncHttpClient, "asyncHttpClient");
        this.asyncHttpClient = asyncHttpClient;
    }


    @Override
    public CompletableFuture<HttpResponse> execute(HttpHost target, HttpRequest request) {
        return execute(target, request, null);
    }

    @Override
    public CompletableFuture<HttpResponse> execute(HttpHost target, HttpRequest request, ClientContext ctx) {


        String fullUri = target.toURI() + request.getUri().toASCIIString();
        RequestBuilder builder = new RequestBuilder(request.getHttpMethod().name());
        builder.setUri(org.asynchttpclient.uri.Uri.create(fullUri));
        handleClientContext(ctx, builder);
        addBody(request, builder);
        copyHeaders(request, builder);

        return asyncHttpClient
                .executeRequest(builder.build())
                .toCompletableFuture()
                .thenApply(ClosableAsyncAhcHttpClient::convert);
    }

    @Override
    public void close() throws IOException {
        asyncHttpClient.close();
    }


    private static HttpResponse convert(Response response) {
        return new HttpResponseAdapter(response);
    }


    private static void addBody(HttpRequest request, RequestBuilder builder) {
        if (request.getByteData() != null) {
            builder.setBody(request.getByteData());
        } else if (request.getStringData() != null) {
            builder.setBody(request.getStringData());
        } else if (request.getFilePath() != null) {
            builder.setBody(request.getFilePath().toFile());
        } else if (request.getInputStreamData() != null) {
            builder.setBody(request.getInputStreamData());
        } else if (!request.getParts().isEmpty()) {
            handleMultiPart(request.getParts(), builder);
        }
    }


    private static void copyHeaders(HttpRequest request, RequestBuilder builder) {
        for (Header h : request.getAllHeaders()) {
            builder.addHeader(h.getName(), h.getValue());
        }
    }

    private static void handleClientContext(ClientContext ctx, RequestBuilder builder) {
        if (ctx != null) {
            handleRequestConfig(ctx.getRequestConfig(), builder);
            handleCredentialsProvider(ctx.getCredentialsProvider(), builder);

        }
    }

    private static void handleRequestConfig(RequestConfig rc, RequestBuilder builder) {
        if (rc != null) {
            builder.setReadTimeout(rc.getConnectTimeoutMilliseconds())
                    .setRequestTimeout(rc.getSocketTimeoutMilliseconds());
        }
    }

    private static void handleCredentialsProvider(CredentialsProvider cp, RequestBuilder builder) {
        if (cp != null) {
            builder.setRealm(ConversionUtils.convert(cp).get(0));
        }
    }

    private static void handleMultiPart(List<Part> parts, RequestBuilder builder) {
        for (Part p : parts) {
            org.asynchttpclient.request.body.multipart.PartBase partBase;

            if (p instanceof StringPart) {
                StringPart sp = (StringPart) p;
                partBase = new org.asynchttpclient.request.body.multipart.StringPart(
                        sp.getName(),
                        sp.getValue(),
                        sp.getContentType() != null ? sp.getContentType().getMimeType() : null,
                        sp.getContentType() != null ? sp.getContentType().getCharset() : null,
                        sp.getContentId(),
                        sp.getTransferEncoding());
            } else if (p instanceof ByteArrayPart) {
                ByteArrayPart bap = (ByteArrayPart) p;
                partBase = new org.asynchttpclient.request.body.multipart.ByteArrayPart(
                        bap.getName(),
                        bap.getBytes(),
                        bap.getContentType() != null ? bap.getContentType().getMimeType() : null,
                        bap.getContentType() != null ? bap.getContentType().getCharset() : null,
                        null,
                        null,
                        bap.getTransferEncoding());
            } else if (p instanceof InputStreamPart) {
                InputStreamPart isp = (InputStreamPart) p;
                partBase = new org.asynchttpclient.request.body.multipart.InputStreamPart(
                        isp.getName(),
                        isp.getInputStream(),
                        null,
                        isp.getContentLength(),
                        isp.getContentType() != null ? isp.getContentType().getMimeType() : null,
                        isp.getContentType() != null ? isp.getContentType().getCharset() : null,
                        isp.getContentId(),
                        isp.getTransferEncoding());
            } else if (p instanceof FilePart) {
                FilePart fp = (FilePart) p;
                partBase = new org.asynchttpclient.request.body.multipart.FilePart(
                        fp.getName(),
                        fp.getFilePath().toFile(),
                        fp.getContentType() != null ? fp.getContentType().getMimeType() : null,
                        fp.getContentType() != null ? fp.getContentType().getCharset() : null,
                        PathUtils.extractFileExtension(fp.getFilePath()),
                        null,
                        fp.getTransferEncoding());
            } else {
                throw new NotSupportedException("An unknown part type received: " + p);
            }

            partBase.setDispositionType(p.getDispositionType());

            for (Header h : p.getCustomHeaders()) {
                partBase.addCustomHeader(h.getName(), h.getValue());
            }
            builder.addBodyPart(partBase);
        }
    }
}