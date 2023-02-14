package fr.waimea.jast;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RouteConfig {

    @Getter @NotNull
    private final String method;

    @Getter @NotNull
    private final List<Middleware> before;

    @Getter @NotNull
    private final List<Middleware> after;

    @Getter @NotNull
    private final String path;

    @Getter @NotNull
    private final Route route;

    public RouteConfig(@NotNull final String httpMethod,
                       @NotNull final List<Middleware> before,
                       @NotNull final String path,
                       @NotNull final Route route,
                       @NotNull final List<Middleware> after) {
        this.method = httpMethod;
        this.before = before;
        this.after = after;
        this.path = path;
        this.route = route;
    }
}
