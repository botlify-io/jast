package fr.botlify.jast.objects;

import com.sun.net.httpserver.HttpExchange;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

public class Response {

    @NotNull
    private final HttpExchange exchange;

    private int code = 200;

    public Response(@NotNull final HttpExchange request) {
        this.exchange = request;
    }

    public void sendJson(final int code,
                         @NotNull final JSONObject object) {
        setCode(code);
        sendJson(object);
    }

    public void sendJson(@NotNull final JSONObject object) {
        send(object);
    }

    public @NotNull Response setCode(final int code) {
        this.code = code;
        return (this);
    }

    /*
     $      Private methods
     */

    private void send(@NotNull final Object object) {
        try {
            exchange.sendResponseHeaders(200, 0);
            exchange.getResponseBody().write(object.toString().getBytes());
            exchange.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
