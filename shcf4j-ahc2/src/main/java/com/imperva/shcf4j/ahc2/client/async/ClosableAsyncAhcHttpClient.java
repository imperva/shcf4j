package com.imperva.shcf4j.ahc2.client.async;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.client.AsyncHttpClient;
import com.imperva.shcf4j.client.CredentialsProvider;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.client.protocol.ClientContext;
import org.asynchttpclient.Realm;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Response;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


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


        String fullUri = target.getSchemeName() + "://" + target.getHostname() + ":" + target.getPort() + request.getUri().toASCIIString();
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
        }
    }


    private static void copyHeaders(HttpRequest request, RequestBuilder builder) {
        for (Header h : request.getAllHeaders()) {
            builder.addHeader(h.getName(), h.getValue());
        }
    }

    private static void handleClientContext(ClientContext ctx, RequestBuilder builder){
        if (ctx != null) {
            handleRequestConfig(ctx.getRequestConfig(), builder);
            handleCredentialsProvider(ctx.getCredentialsProvider(), builder);

        }
    }

    private static void handleRequestConfig(RequestConfig rc, RequestBuilder builder){
        if (rc != null) {
            builder.setReadTimeout(rc.getConnectTimeoutMilliseconds())
                    .setRequestTimeout(rc.getSocketTimeoutMilliseconds());
        }
    }

    private static void handleCredentialsProvider(CredentialsProvider cp, RequestBuilder builder){
        if (cp != null){
            List<Realm> lRealms =
                    cp.getCredentials()
                            .entrySet()
                            .stream()
                            .map(e -> {
                                Realm.Builder realm = new Realm.Builder(
                                        e.getValue().getUserPrincipal().getName(),
                                        e.getValue().getPassword());
                                if (e.getKey().getScheme() != null) {
                                    switch (e.getKey().getScheme().toUpperCase()) {
                                        case "BASIC":
                                            realm.setScheme(Realm.AuthScheme.BASIC);
                                            break;
                                        case "DIGEST":
                                            realm.setScheme(Realm.AuthScheme.DIGEST);
                                            break;
                                        case "NTLM":
                                            realm.setScheme(Realm.AuthScheme.NTLM);
//                                        realm.setNtlmDomain()
                                            break;
                                        case "SPNEGO":
                                            realm.setScheme(Realm.AuthScheme.SPNEGO);
                                            break;
                                        case "KERBEROS":
                                            realm.setScheme(Realm.AuthScheme.KERBEROS);
                                            break;
                                    }
                                } else { // Default scheme
                                    realm.setScheme(Realm.AuthScheme.BASIC);
                                }
                                return realm.build();
                            }).collect(Collectors.toList());
            builder.setRealm(lRealms.get(0));
        }
    }

}
