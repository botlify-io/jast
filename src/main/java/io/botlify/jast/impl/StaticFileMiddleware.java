package io.botlify.jast.impl;

import io.botlify.jast.enums.HttpMethod;
import io.botlify.jast.interfaces.Route;
import io.botlify.jast.objects.Request;
import io.botlify.jast.objects.Response;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * This built-in middleware is used to serve static files.
 */
@Log4j2
public class StaticFileMiddleware implements Route {

    @NotNull @Getter
    private final String originPath;

    public StaticFileMiddleware(@NotNull final String originPath) {
        this.originPath = originPath;
    }

    @Override
    public boolean handle(@NotNull final Request request,
                          @NotNull final Response response) {
        if (request.getMethod() != HttpMethod.GET)
            return (true);
        log.trace("StaticFileMiddleware try to found file as " + request.getPath());
        final File file = getFile(request.getPath());
        if (file == null) {
            log.trace("StaticFileMiddleware can't found file as " + request.getPath());
            return (true);
        }
        log.trace("StaticFileMiddleware found file as " + request.getPath());
        response.sendFile(200, file);
        return (false);
    }

    /*
     $      Private methods
     */

    public @Nullable File getFile(@NotNull final String path) {
        final File file = new File(path);
        if (file.exists())
            return (file);
        return (null);
    }

}
