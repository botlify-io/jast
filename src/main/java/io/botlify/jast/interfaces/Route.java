package io.botlify.jast.interfaces;

import io.botlify.jast.objects.Request;
import io.botlify.jast.objects.Response;
import org.jetbrains.annotations.NotNull;

public interface Route {

    void handle(@NotNull final Request request, @NotNull final Response response);

}
