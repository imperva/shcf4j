package com.imperva.shcf4j.proxy;

import com.imperva.shcf4j.HttpClientBaseTest;
import com.imperva.shcf4j.HttpHost;
import com.imperva.shcf4j.HttpRequest;
import com.imperva.shcf4j.HttpRequestBuilder;
import com.imperva.shcf4j.HttpResponse;
import com.imperva.shcf4j.client.config.RequestConfig;
import com.imperva.shcf4j.client.protocol.ClientContext;
import org.eclipse.jetty.proxy.ConnectHandler;
import org.eclipse.jetty.proxy.ProxyServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public abstract class ProxyTest extends HttpClientBaseTest {

    private Server proxyServer;
    private int proxyServerPort;


    @Before
    public void setupProxyServer() throws Exception{
        proxyServer = new Server();
        ServerConnector proxyServerConnector = addHttpConnector(proxyServer);

        HandlerCollection handlers = new HandlerCollection();
        proxyServer.setHandler(handlers);

        // Setup proxy servlet
        ServletContextHandler context = new ServletContextHandler(handlers, "/", ServletContextHandler.SESSIONS);

        ServletHolder proxyServlet = new ServletHolder(ProxyServlet.class);
        context.addServlet(proxyServlet, "/*");

        ConnectHandler proxy = new ConnectHandler();
        handlers.addHandler(proxy);

        proxyServer.start();
        proxyServerPort = proxyServerConnector.getLocalPort();
    }

    @After
    public void stopProxyServer() throws Exception{
        proxyServer.stop();
    }

    private static ServerConnector addHttpConnector(Server server) {
        ServerConnector connector = new ServerConnector(server);
        server.addConnector(connector);
        return connector;
    }


    @Test
    public void clientContextProxyTest() throws Exception {

        String uri = "/my/resource";
        instanceRule.stubFor(get(urlEqualTo(uri))
                .willReturn(
                        aResponse()
                                .withStatus(HttpURLConnection.HTTP_OK)
                )
        );

        HttpRequest request =
                HttpRequestBuilder
                        .GET()
                        .uri(uri)
                        .build();

        ClientContext ctx = ClientContext
                .builder()
                .requestConfig(
                        RequestConfig
                                .builder()
                                .proxy(HttpHost.builder().hostname("localhost").port(proxyServerPort).build())
                                .build())
                .build();



        HttpResponse response = execute(HttpClientBaseTest.HOST, request, ctx);

        Assert.assertEquals("Response code is wrong",
                HttpURLConnection.HTTP_OK, response.getStatusLine().getStatusCode());

    }


}
