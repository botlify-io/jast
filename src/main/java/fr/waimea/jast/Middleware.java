package fr.waimea.jast;

import com.sun.net.httpserver.Request;
import org.jetbrains.annotations.NotNull;

public interface Middleware {

    boolean handle(@NotNull final Request request, @NotNull final Response response);

}
