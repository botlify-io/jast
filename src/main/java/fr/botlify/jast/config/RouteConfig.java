package fr.botlify.jast.config;

import fr.botlify.jast.enums.HttpMethod;
import fr.botlify.jast.interfaces.Middleware;
import fr.botlify.jast.interfaces.Route;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class RouteConfig {

    @EqualsAndHashCode.Include
    @Getter @NotNull
    private final HttpMethod method;

    @EqualsAndHashCode.Include
    @Getter @NotNull
    private final String path;

    @Getter @NotNull
    private final List<Middleware> middlewares;

    @Getter @NotNull
    private final Route route;

    public RouteConfig(@NotNull final HttpMethod httpMethod,
                       @NotNull final String path,
                       @NotNull final List<Middleware> middlewares,
                       @NotNull final Route route) {
        this.method = httpMethod;
        this.middlewares = middlewares;
        this.path = path;
        this.route = route;
    }

    public RouteConfig(@NotNull final HttpMethod httpMethod,
                       @NotNull final String path,
                       @NotNull final Route route) {
        this.method = httpMethod;
        this.middlewares = new ArrayList<>(0);
        this.path = path;
        this.route = route;
    }

    /*
     $      Public methods
     */

    public @NotNull final Map<String, Integer> getRequestParam() {
        final Map<String, Integer> params = new HashMap<>();
        final String[] split = path.split("/");
        for (int i = 0; i < split.length; i++) {
            final String s = split[i];
            if (s.startsWith(":"))
                params.put(s.substring(1), i);
        }
        return (params);
    }

}
