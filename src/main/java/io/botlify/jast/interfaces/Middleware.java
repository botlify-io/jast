package io.botlify.jast.interfaces;

import io.botlify.jast.objects.Request;
import io.botlify.jast.objects.Response;
import org.jetbrains.annotations.NotNull;

public interface Middleware {

    /**
     * This method is called before the route is called, or after the route is called.
     * If this method return false, the next middleware will not be called.
     * @param request The request object.
     * @param response The response object.
     * @return True if the next middleware should be called, false otherwise.
     */
    boolean handle(@NotNull final Request request, @NotNull final Response response);

}
