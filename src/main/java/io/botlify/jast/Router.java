package io.botlify.jast;

import io.botlify.jast.config.RouteConfig;
import io.botlify.jast.enums.HttpMethod;
import io.botlify.jast.interfaces.Route;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
public class Router {

    @NotNull @Getter
    private final List<RouteConfig> routesConfig;

    public Router() {
        this.routesConfig = new ArrayList<>();
    }

    /**
     * Define the sub router to use with the path.
     * @param path The path to use.
     * @param router The router to use.
     * @throws IllegalArgumentException If the router is the same as the current router.
     */
    public void use(@NotNull final String path,
                    @NotNull final Router router) {
        if (router == this)
            throw new IllegalArgumentException("You can't use the same router");
        for (final RouteConfig routeConfig : router.getRoutesConfig()) {
            final String newPath = path + routeConfig.getPath();
            final RouteConfig newRouteConfig = new RouteConfig(routeConfig.getMethod(), newPath,
                    routeConfig.getRoute());
            routesConfig.add(newRouteConfig);
        }
    }

    // GET

    /**
     * Insert a route in the router with the path for the GET method.
     * @param path The path to use.
     * @param routes The routes to use.
     */
    public void get(@NotNull final String path,
                    @NotNull final Route... routes) {
        if (routes.length == 0)
            throw new IllegalArgumentException("You must provide at least one route");
        insertRoute(HttpMethod.GET, path, List.of(routes));
    }

    // POST

    /**
     * Insert a route in the router with the path for the POST method.
     * @param path The path to use.
     * @param routes The routes to use.
     */
    public void post(@NotNull final String path,
                     @NotNull final Route... routes) {
        if (routes.length == 0)
            throw new IllegalArgumentException("You must provide at least one route");
        insertRoute(HttpMethod.POST, path, List.of(routes));
    }


    // PUT

    /**
     * Insert a route in the router with the path for the PUT method.
     * @param path The path to use.
     * @param routes The routes to use.
     */
    public void put(@NotNull final String path,
                    @NotNull final Route... routes) {
        if (routes.length == 0)
            throw new IllegalArgumentException("You must provide at least one route");
        insertRoute(HttpMethod.PUT, path, List.of(routes));
    }

    // DELETE

    /**
     * Insert a route in the router with the path for the DELETE method.
     * @param path The path to use.
     * @param routes The routes to use.
     */
    public void delete(@NotNull final String path,
                       @NotNull final Route... routes) {
        if (routes.length == 0)
            throw new IllegalArgumentException("You must provide at least one route");
        insertRoute(HttpMethod.DELETE, path, List.of(routes));
    }

    // PATCH

    /**
     * Insert a route in the router with the path for the PATCH method.
     * @param path The path to use.
     * @param routes The routes to use.
     */
    public void patch(@NotNull final String path,
                      @NotNull final Route... routes) {
        if (routes.length == 0)
            throw new IllegalArgumentException("You must provide at least one route");
        insertRoute(HttpMethod.PATCH, path, List.of(routes));
    }

    // HEAD

    /**
     * Insert a route in the router with the path for the HEAD method.
     * @param path The path to use.
     * @param routes The routes to use.
     */
    public void head(@NotNull final String path,
                     @NotNull final Route... routes) {
        if (routes.length == 0)
            throw new IllegalArgumentException("You must provide at least one route");
        insertRoute(HttpMethod.HEAD, path, List.of(routes));
    }

    // OPTIONS

    /**
     * Insert a route in the router with the path for the OPTIONS method.
     * @param path The path to use.
     * @param routes The routes to use.
     */
    public void options(@NotNull final String path,
                        @NotNull final Route... routes) {
        if (routes.length == 0)
            throw new IllegalArgumentException("You must provide at least one route");
        insertRoute(HttpMethod.OPTIONS, path, List.of(routes));
    }

    // TRACE

    /**
     * Insert a route in the router with the path for the TRACE method.
     * @param path The path to use.
     * @param routes The routes to use.
     */
    public void trace(@NotNull final String path,
                      @NotNull final Route... routes) {
        if (routes.length == 0)
            throw new IllegalArgumentException("You must provide at least one route");
        insertRoute(HttpMethod.TRACE, path, List.of(routes));
    }

    // CONNECT

    /**
     * Insert a route in the router with the path for the CONNECT method.
     * @param path The path to use.
     * @param routes The routes to use.
     */
    public void connect(@NotNull final String path,
                        @NotNull final Route... routes) {
        if (routes.length == 0)
            throw new IllegalArgumentException("You must provide at least one route");
        insertRoute(HttpMethod.CONNECT, path, List.of(routes));
    }

    /*
     $      Private methods
     */

    /**
     * Insert a route in the list of routes.
     * @param method The HTTP method of the route.
     * @param path The path of the route.
     * @param routes The list of {@link Route} to add.
     */
    void insertRoute(@NotNull final HttpMethod method,
                     @NotNull final String path,
                     @NotNull final List<Route> routes) {
        for (final Route route : routes)
            insertRoute(method, path, route);
    }

    /**
     * Insert a route in the list of routes.
     * @param method The HTTP method of the route.
     * @param path The path of the route.
     * @param route The route to insert.
     * @throws IllegalArgumentException If the route is already defined with the same method and path.
     */
    void insertRoute(@NotNull final HttpMethod method,
                     @NotNull final String path,
                     @NotNull final Route route) {
        final RouteConfig routeConfig = new RouteConfig(method, path, route);
        if (routesConfig.contains(routeConfig)) {
            throw (new IllegalArgumentException("Route (" + method + ") with path \""
                    + path + "\" was already defined"));
        }
        routesConfig.add(routeConfig);
    }

}
