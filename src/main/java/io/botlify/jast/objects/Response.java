package io.botlify.jast.objects;

import com.sun.net.httpserver.HttpExchange;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

public class Response {

    @NotNull
    private final HttpExchange exchange;

    @NotNull
    private ContentType contentType = ContentType.WILDCARD;

    private int code = 200;

    public Response(@NotNull final HttpExchange request) {
        this.exchange = request;
    }

    public void sendText(final int code,
                         @NotNull final String text) {
        setCode(code);
        sendText(text);
    }

    public void sendText(@NotNull final String text) {
        this.contentType = ContentType.TEXT_PLAIN;
        send(text);
    }

    public void sendHtml(final int code,
                         @NotNull final String html) {
        setCode(code);
        sendHtml(html);
    }

    public void sendHtml(@NotNull final String html) {
        this.contentType = ContentType.TEXT_HTML;
        send(html);
    }

    public void sendJson(final int code,
                         @NotNull final JSONObject object) {
        setCode(code);
        sendJson(object);
    }

    public void sendJson(@NotNull final JSONObject object) {
        this.contentType = ContentType.APPLICATION_JSON;
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
