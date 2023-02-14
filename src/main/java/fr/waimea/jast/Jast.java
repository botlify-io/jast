package fr.waimea.jast;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.Request;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Jast {

    @Getter @NotNull
    private final Map<String, Route> routes;

    private List<Middleware> beforeAll, afterAll;

    @Getter @Nullable
    private HttpServer server;

    public Jast() {
        this.routes = new HashMap<>();
        this.beforeAll = new ArrayList<>();
        this.afterAll = new ArrayList<>();
    }

    /*
     $      Middleware
     */

    void useBeforeAll(@NotNull final Middleware... middlewares) {
        this.beforeAll.addAll(List.of(middlewares));
    }

    void useAfterAll(@NotNull final Middleware... middlewares) {
        this.afterAll.addAll(List.of(middlewares));
    }

    /*
     $      Get
     */

    void get(@NotNull final String path,
             @NotNull final Route route) {
        routes.put(path, route);
    }

    void get(@NotNull final List<Middleware> before,
             @NotNull final Route route) {

    }

    void get(@NotNull final String path,
             @NotNull final List<Middleware> before,
             @NotNull final Route route) {

    }

    void get(@NotNull final String path,
             @NotNull final List<Middleware> before,
             @NotNull final Route route,
             @NotNull final List<Middleware> after) {

    }

    /*
     $      Post
     */

    public void listen(@Range(from = 0, to = 65535) final int port,
                       @NotNull final String host) throws IOException {
        listen(port, host, null);
    }

    public void listen(@Range(from = 0, to = 65535) final int port,
                       @NotNull final String host,
                       @Nullable Consumer<Void> callback) throws IOException {
        if (server != null)
            throw (new IllegalStateException("Server is already running"));
        this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
        setupRoutes();
        assert this.server != null;
        this.server.start();
        if (callback != null)
            callback.accept(null);
    }

    /*
     $      Private methods
     */

    void setupRoutes() {
        assert server != null;
        for (Map.Entry<String, Route> entry : routes.entrySet()) {
            server.createContext(entry.getKey(), exchange -> {
                Response response = new Response(exchange);
                if (!callMiddleware(exchange, response, beforeAll))
                    return;
                entry.getValue().handle(exchange, response);
                callMiddleware(exchange, response, afterAll);
            });
        }
    }

    boolean callMiddleware(@NotNull final Request request,
                           @NotNull final Response response,
                           @NotNull final List<Middleware> middlewares) {
        for (Middleware middleware : middlewares) {
            if (!middleware.handle(request, response))
                return false;
        }
        return true;
    }

}
