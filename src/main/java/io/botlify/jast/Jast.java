package io.botlify.jast;

import com.sun.net.httpserver.HttpServer;
import io.botlify.jast.config.RouteConfig;
import io.botlify.jast.enums.HttpMethod;
import io.botlify.jast.interfaces.Middleware;
import io.botlify.jast.interfaces.Route;
import io.botlify.jast.tools.SetupRoutesTool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class Jast {

    /**
     * This map represents all the routes of the application.
     * The key is the path of the route and the value is a map of all the routes with the same path
     * for the specified HTTP method.
     */
    private final List<RouteConfig> routeConfigs;

    /**
     * The list of middlewares to execute before all the routes.
     */
    private final List<Middleware> beforeAll;

    /**
     * The list of middlewares to execute after all the routes.
     */
    private final List<Middleware> afterAll;

    /**
     * The HTTP server of the application.
     */
    private HttpServer server;

    public Jast() {
        this.routeConfigs = new ArrayList<>();
        this.beforeAll = new ArrayList<>();
        this.afterAll = new ArrayList<>();
    }

    /*
     $      Public methods
     */

    // Listen

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

        // Setup routes
        final SetupRoutesTool setupRoutesTool = new SetupRoutesTool(this.server, this.routeConfigs);
        setupRoutesTool.setup();

        assert this.server != null;
        this.server.start();
        if (callback != null)
            callback.accept(null);
    }

    // Global middlewares

    void useBeforeAll(@NotNull final Middleware... middlewares) {
        this.beforeAll.addAll(List.of(middlewares));
    }

    void useAfterAll(@NotNull final Middleware... middlewares) {
        this.afterAll.addAll(List.of(middlewares));
    }

    // GET

    public void get(@NotNull final String path,
                    @NotNull final List<Middleware> middlewares,
                    @NotNull final Route route) {
        insertRoute(HttpMethod.GET, path, middlewares, route);
    }

    public void get(@NotNull final String path,
                    @NotNull final Route route) {
        insertRoute(HttpMethod.GET, path, new ArrayList<>(0), route);
    }

    // POST

    public void post(@NotNull final String path,
                     @NotNull final List<Middleware> middlewares,
                     @NotNull final Route route) {
        insertRoute(HttpMethod.POST, path, middlewares, route);
    }

    public void post(@NotNull final String path,
                     @NotNull final Route route) {
        insertRoute(HttpMethod.POST, path, new ArrayList<>(0), route);
    }


    // PUT

    public void put(@NotNull final String path,
                    @NotNull final List<Middleware> middlewares,
                    @NotNull final Route route) {
        insertRoute(HttpMethod.PUT, path, middlewares, route);
    }

    public void put(@NotNull final String path,
                    @NotNull final Route route) {
        insertRoute(HttpMethod.PUT, path, new ArrayList<>(0), route);
    }

    // DELETE

    public void delete(@NotNull final String path,
                       @NotNull final List<Middleware> middlewares,
                       @NotNull final Route route) {
        insertRoute(HttpMethod.DELETE, path, middlewares, route);
    }

    public void delete(@NotNull final String path,
                       @NotNull final Route route) {
        insertRoute(HttpMethod.DELETE, path, new ArrayList<>(0), route);
    }

    // PATCH

    public void patch(@NotNull final String path,
                      @NotNull final List<Middleware> middlewares,
                      @NotNull final Route route) {
        insertRoute(HttpMethod.PATCH, path, middlewares, route);
    }

    public void patch(@NotNull final String path,
                      @NotNull final Route route) {
        insertRoute(HttpMethod.PATCH, path, new ArrayList<>(0), route);
    }

    // HEAD

    public void head(@NotNull final String path,
                     @NotNull final List<Middleware> middlewares,
                     @NotNull final Route route) {
        insertRoute(HttpMethod.HEAD, path, middlewares, route);
    }

    public void head(@NotNull final String path,
                     @NotNull final Route route) {
        insertRoute(HttpMethod.HEAD, path, new ArrayList<>(0), route);
    }

    // OPTIONS

    public void options(@NotNull final String path,
                        @NotNull final List<Middleware> middlewares,
                        @NotNull final Route route) {
        insertRoute(HttpMethod.OPTIONS, path, middlewares, route);
    }

    public void options(@NotNull final String path,
                        @NotNull final Route route) {
        insertRoute(HttpMethod.OPTIONS, path, new ArrayList<>(0), route);
    }

    // TRACE

    public void trace(@NotNull final String path,
                      @NotNull final List<Middleware> middlewares,
                      @NotNull final Route route) {
        insertRoute(HttpMethod.TRACE, path, middlewares, route);
    }

    public void trace(@NotNull final String path,
                      @NotNull final Route route) {
        insertRoute(HttpMethod.TRACE, path, new ArrayList<>(0), route);
    }

    // CONNECT

    public void connect(@NotNull final String path,
                        @NotNull final List<Middleware> middlewares,
                        @NotNull final Route route) {
        insertRoute(HttpMethod.CONNECT, path, middlewares, route);
    }

    public void connect(@NotNull final String path,
                        @NotNull final Route route) {
        insertRoute(HttpMethod.CONNECT, path, new ArrayList<>(0), route);
    }

    /*
     $      Private methods
     */

    /**
     * Insert a route in the list of routes.
     * @param method The HTTP method of the route.
     * @param path The path of the route.
     * @param route The route to insert.
     */
    void insertRoute(@NotNull final HttpMethod method,
                     @NotNull final String path,
                     @NotNull final Route route) {
        insertRoute(method, path, new ArrayList<>(0), route);
    }

    /**
     * Insert a route in the list of routes.
     * @param method The HTTP method of the route.
     * @param path The path of the route.
     * @param middlewares The middlewares of the route.
     * @param route The route to insert.
     * @throws IllegalArgumentException If the route is already defined with the same method and path.
     */
    private void insertRoute(@NotNull final HttpMethod method,
                             @NotNull final String path,
                             @NotNull final List<Middleware> middlewares,
                             @NotNull final Route route) {
        final RouteConfig routeConfig = new RouteConfig(method, path, middlewares, route);
        if (routeConfigs.contains(routeConfig))
            throw (new IllegalArgumentException("Route (" + method + ") with path \"" + path + "\" was already defined"));
        routeConfigs.add(routeConfig);
    }

}
