package fr.waimea.jast;

import com.sun.net.httpserver.HttpExchange;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

public class Response {

    @NotNull
    private final HttpExchange exchange;

    public Response(@NotNull final HttpExchange request) {
        this.exchange = request;
    }

    void sendJson(@NotNull final JSONObject object) throws IOException {
        send(object);
    }

    /*
     $      Private methods
     */

    private void send(@NotNull final Object object) throws IOException {
        exchange.sendResponseHeaders(200, 0);
        exchange.getResponseBody().write(object.toString().getBytes());
        exchange.close();
    }

}
