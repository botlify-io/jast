package io.botlify.jast.config;

import io.botlify.jast.enums.HttpMethod;
import io.botlify.jast.interfaces.Route;
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

    @ToString.Exclude
    @Getter @NotNull
    private final Route route;

    public RouteConfig(@NotNull final HttpMethod httpMethod,
                       @NotNull final String path,
                       @NotNull final Route route) {
        this.method = httpMethod;
        this.path = path;
        this.route = route;
    }

    /*
     $      Public methods
     */

    public @NotNull Map<String, Integer> getRequestParam() {
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
