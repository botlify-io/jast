package io.botlify.jast.interfaces;

import io.botlify.jast.objects.Request;
import io.botlify.jast.objects.Response;
import org.jetbrains.annotations.NotNull;

/**
 * This interface represents a route.
 * Define your route by implementing this interface.
 */
public interface Route {

    /**
     * This method should be redefined by your route.
     * @param request The request received from the client.
     * @param response The response object used to send a response to the client.
     * @return True if the request has been handled, false if the request go to the next route.
     */
    boolean handle(@NotNull final Request request, @NotNull final Response response);

}
