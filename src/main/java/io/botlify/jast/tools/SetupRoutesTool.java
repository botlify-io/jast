package io.botlify.jast.tools;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import io.botlify.jast.config.JastConfig;
import io.botlify.jast.interfaces.Route;
import io.botlify.jast.objects.Request;
import io.botlify.jast.objects.Response;
import io.botlify.jast.config.RouteConfig;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Log4j2
public class SetupRoutesTool {

    @NotNull
    private final HttpServer server;

    @NotNull
    private final List<RouteConfig> routeConfigs;

    @NotNull
    private final JastConfig jastConfig;

    public SetupRoutesTool(@NotNull final HttpServer server,
                           @NotNull final List<RouteConfig> routeConfigs,
                           @NotNull final JastConfig jastConfig) {
        this.server = server;
        this.routeConfigs = routeConfigs;
        this.jastConfig = jastConfig;
    }

    public void setup() {
        // Log all the routes.
        routeConfigs.forEach((routeConfig -> {
            log.debug("Route ({}) \"{}\" has been added.",
                    routeConfig.getMethod(), routeConfig.getPath());
        }));
        server.createContext("/", this::callRoute);
    }

    private void callRoute(@NotNull final HttpExchange exchange) {
        final String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        if (!path.endsWith("/") && jastConfig.isEnableTrailingSlash())
            path = path + "/";

        final String endPath = path;
        // Find the route with the specified path and method.
        final List<RouteConfig> routeConfigList = routeConfigs.stream()
                .filter(routeConfigTmp -> pathEquals(routeConfigTmp.getPath(), endPath))
                .filter(routeConfigTmp -> routeConfigTmp.getMethod().name().equals(method))
                .collect(Collectors.toCollection(ArrayList::new));
        // Check if the route was found.
        final Response response = new Response(exchange);
        if (routeConfigList.isEmpty()) {
            log.trace("Route not found: ({}) \"{}\"", method, path);
            response.sendText(404, "Not found");
            return;
        }
        final RouteConfig routeConfig = routeConfigList.get(0);
        try {
            final Request request = new Request(routeConfig, exchange);
            // Call the middlewares before all the routes.
            callRoutes(request, response, routeConfigList);
        } catch (IOException e) {
            log.warn("Error while receiving the request: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error while calling the route.", e);
            if (response.isSent())
                return;
            response.sendText(500, "Internal server error");
        }

    }

    private void callRoutes(@NotNull final Request request,
                            @NotNull final Response response,
                            @NotNull final List<RouteConfig> routeConfigs) {
        for (final RouteConfig routeConfig : routeConfigs) {
            if (!routeConfig.getRoute().handle(request, response))
                return;
        }
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
