package io.botlify.jast.objects;

import com.sun.net.httpserver.HttpExchange;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Response {

    /**
     * The exchange of the request.
     */
    @NotNull
    private final HttpExchange exchange;

    /**
     * This boolean is used to know if the response has been sent.
     */
    @Getter
    private boolean isSent = false;

    /**
     * The content type of the response.
     */
    @NotNull
    private ContentType contentType = ContentType.WILDCARD;

    /**
     * The code of the response.
     */
    private int code = 200;

    /*
     $      Constructors.
     */

    public Response(@NotNull final HttpExchange request) {
        this.exchange = request;
    }

    /*
     $      Public methods.
     */

    // Send text.

    public void sendText(final int code,
                         @NotNull final String text) {
        setCode(code);
        sendText(text);
    }

    public void sendText(@NotNull final String text) {
        this.contentType = ContentType.TEXT_PLAIN;
        send(text);
    }

    // Send HTML.

    public void sendHtml(final int code,
                         @NotNull final String html) {
        setCode(code);
        sendHtml(html);
    }

    public void sendHtml(@NotNull final String html) {
        this.contentType = ContentType.TEXT_HTML;
        send(html);
    }

    // Send JSON.

    public void sendJson(final int code,
                         @NotNull final Object object) {
        setCode(code);
        sendJson(object);
    }

    public void sendJson(@NotNull final Object object) {
        this.contentType = ContentType.APPLICATION_JSON;
        send(object);
    }

    // Send XML.

    public void sendXml(final int code,
                        @NotNull final Object object) {
        setCode(code);
        sendXml(object);
    }

    public void sendXml(@NotNull final Object object) {
        this.contentType = ContentType.APPLICATION_XML;
        send(object);
    }

    /*
     $      Set value of the response.
     */

    public @NotNull Response setCode(final int code) {
        this.code = code;
        return (this);
    }

    public @NotNull Response setContentType(@NotNull final ContentType contentType) {
        this.contentType = contentType;
        return (this);
    }

    // Send.

    public void send(@NotNull final Object object) {
        if (isSent)
            throw (new IllegalStateException("Response already sent"));
        isSent = true;
        try {
            exchange.sendResponseHeaders(200, 0);
            exchange.getResponseBody().write(object.toString().getBytes());
            exchange.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
