package io.botlify.jast;

import com.sun.net.httpserver.HttpServer;
import io.botlify.jast.config.JastConfig;
import io.botlify.jast.enums.HttpMethod;
import io.botlify.jast.interfaces.Route;
import io.botlify.jast.tools.SetupRoutesTool;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.function.Consumer;

/**
 * This class is used to create a HTTP Server and initialize
 * all the route for this server.
 */
@Log4j2
@ToString(callSuper = true)
public final class Jast extends Router {

    private final JastConfig jastConfig;

    /**
     * The HTTP server of the application.
     */
    @ToString.Exclude
    @Nullable
    private HttpServer server;

    /**
     * Initialize a new Jast instance for the HTTP server.
     */
    public Jast() {
        this.jastConfig = JastConfig.DEFAULT;
    }

    public Jast(@NotNull final JastConfig config) {
        this.jastConfig = config;
    }

    /*
     $      Public methods
     */

    // Listen

    public void listen(@Range(from = 0, to = 65535) final int port,
                       @NotNull final String host) throws IOException {
        listen(port, host, null);
    }

    public void listen(@Range(from = 0, to = 65535) final int port,
                       @NotNull final String host,
                       @Nullable Consumer<Void> callback) throws IOException {
        if (server != null)
            throw (new IllegalStateException("Server is already running"));
        this.server = HttpServer.create(new InetSocketAddress(host, port), 0);

        // Setup routes
        final SetupRoutesTool setupRoutesTool = new SetupRoutesTool(this.server, this.getRoutesConfig(),
                this.jastConfig);
        setupRoutesTool.setup();

        assert this.server != null;
        this.server.start();
        log.debug("Jast server is started on {}:{}", host, port);
        if (callback != null)
            callback.accept(null);
    }

    // Close

    public void close() {
        if (server == null)
            throw new IllegalStateException("The Jast server is not started !");
        server.stop(1000);
        server = null;
    }

}
