package fr.botlify.jast.interfaces;

import fr.botlify.jast.objects.Request;
import fr.botlify.jast.objects.Response;
import org.jetbrains.annotations.NotNull;

public interface Route {

    void handle(@NotNull final Request request, @NotNull final Response response);

}
