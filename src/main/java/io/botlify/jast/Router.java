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

    public void get(@NotNull final String path,
                    @NotNull final Route... routes) {
        if (routes.length == 0)
            throw new IllegalArgumentException("You must provide at least one route");
        insertRoute(HttpMethod.GET, path, List.of(routes));
    }

    // POST

    public void post(@NotNull final String path,
                     @NotNull final Route... routes) {
        if (routes.length == 0)
            throw new IllegalArgumentException("You must provide at least one route");
        insertRoute(HttpMethod.POST, path, List.of(routes));
    }


    // PUT

    public void put(@NotNull final String path,
                    @NotNull final Route... routes) {
        if (routes.length == 0)
            throw new IllegalArgumentException("You must provide at least one route");
        insertRoute(HttpMethod.PUT, path, List.of(routes));
    }

    // DELETE

    public void delete(@NotNull final String path,
                       @NotNull final Route... routes) {
        if (routes.length == 0)
            throw new IllegalArgumentException("You must provide at least one route");
        insertRoute(HttpMethod.DELETE, path, List.of(routes));
    }

    // PATCH

    public void patch(@NotNull final String path,
                      @NotNull final Route... routes) {
        if (routes.length == 0)
            throw new IllegalArgumentException("You must provide at least one route");
        insertRoute(HttpMethod.PATCH, path, List.of(routes));
    }

    // HEAD

    public void head(@NotNull final String path,
                     @NotNull final Route... routes) {
        if (routes.length == 0)
            throw new IllegalArgumentException("You must provide at least one route");
        insertRoute(HttpMethod.HEAD, path, List.of(routes));
    }

    // OPTIONS

    public void options(@NotNull final String path,
                        @NotNull final Route... routes) {
        if (routes.length == 0)
            throw new IllegalArgumentException("You must provide at least one route");
        insertRoute(HttpMethod.OPTIONS, path, List.of(routes));
    }

    // TRACE

    public void trace(@NotNull final String path,
                      @NotNull final Route... routes) {
        if (routes.length == 0)
            throw new IllegalArgumentException("You must provide at least one route");
        insertRoute(HttpMethod.TRACE, path, List.of(routes));
    }

    // CONNECT

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
        if (routesConfig.contains(routeConfig))
            throw (new IllegalArgumentException("Route (" + method + ") with path \"" + path + "\" was already defined"));
        routesConfig.add(routeConfig);
    }

}
