package io.botlify.jast.tools;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import io.botlify.jast.objects.Request;
import io.botlify.jast.objects.Response;
import io.botlify.jast.config.RouteConfig;
import io.botlify.jast.interfaces.Middleware;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SetupRoutesTool {

    @NotNull
    private final HttpServer server;

    @NotNull
    private final List<RouteConfig> routeConfigs;

    public SetupRoutesTool(@NotNull final HttpServer server,
                           @NotNull final List<RouteConfig> routeConfigs) {
        this.server = server;
        this.routeConfigs = routeConfigs;
    }

    public void setup() {
        server.createContext("/", this::callRoute);
    }

    private void callRoute(@NotNull final HttpExchange exchange) {
        final String method = exchange.getRequestMethod();
        final String path = exchange.getRequestURI().getPath();
        // Find the route with the specified path and method.
        final RouteConfig routeConfig = routeConfigs.stream()
                .filter(routeConfig1 -> pathEquals(routeConfig1.getPath(), path))
                .filter(routeConfig1 -> routeConfig1.getMethod().name().equals(method))
                .findFirst()
                .orElse(null);
        // Check if the route was found.
        if (routeConfig == null)
            return;
        final Response response = new Response(exchange);
        try {
            final Request request = new Request(routeConfig, exchange);
            // Call the middlewares before all the routes.
            if (!callMiddleware(request, response, routeConfig.getMiddlewares()))
                return;
            // Call the route.
            routeConfig.getRoute().handle(request, new Response(exchange));
        } catch (Exception e) {
            // TODO: Handle the exception.
        }

    }

    private boolean callMiddleware(@NotNull final Request request,
                                   @NotNull final Response response,
                                   @NotNull final List<Middleware> middlewares) {
        for (final Middleware middleware : middlewares) {
            if (!middleware.handle(request, response))
                return (false);
        }
        return (true);
    }

    /**
     * This method will remove all request parameters from the specified path
     * to compare it with the route path. This method check if the route path
     * and the request path are equals (without the request parameters).
     * @param routePath The route path.
     * @param requestPath The request path.
     * @return True if the route path and the request path are equals, false otherwise.
     */
    public boolean pathEquals(@NotNull final String routePath,
                              @NotNull final String requestPath) {
        final String[] routePathParts = routePath.split("/");
        final String[] requestPathParts = requestPath.split("/");
        // Check if the route path and the request path have the same number of parts.
        if (routePathParts.length != requestPathParts.length)
            return (false);
        // Check if the route path and the request path have the same parts.
        for (int i = 0; i < routePathParts.length; i++) {
            if (routePathParts[i].startsWith(":"))
                continue;
            if (!routePathParts[i].equals(requestPathParts[i]))
                return (false);
        }
        return (true);
    }

}
