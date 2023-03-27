package io.botlify.jast.impl;

import io.botlify.jast.interfaces.Route;
import io.botlify.jast.objects.Request;
import io.botlify.jast.objects.Response;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This built-in middleware is used to add CORS headers to the response.
 */
@Builder
public class CorsMiddleware implements Route {

    /**
     * The origin to allow used for the <b>Access-Control-Allow-Origin</b> header.
     */
    @NotNull @Getter @Builder.Default
    private final String origin = "*";

    /**
     * The methods to allow used for the <b>Access-Control-Allow-Methods</b> header.
     */
    @NotNull @Getter @Builder.Default
    private final String methods = "*";

    /**
     * The headers to allow used for the <b>Access-Control-Allow-Headers</b> header.
     */
    @NotNull @Getter @Builder.Default
    private final String headers = "*";

    /**
     * The credentials to allow used for the <b>Access-Control-Allow-Credentials</b> header.
     */
    @Nullable @Getter
    private final String credentials;

    /**
     * The max age to allow used for the <b>Access-Control-Max-Age</b> header.
     */
    @Nullable
    private final String maxAge;

    @Override
    public boolean handle(@NotNull final Request request,
                          @NotNull final Response response) {
        response.addHeader("Access-Control-Allow-Origin", origin);
        response.addHeader("Access-Control-Allow-Methods", methods);
        response.addHeader("Access-Control-Allow-Headers", headers);
        if (credentials != null)
            response.addHeader("Access-Control-Allow-Credentials", credentials);
        if (maxAge != null)
            response.addHeader("Access-Control-Max-Age", maxAge);
        return (true);
    }

}
