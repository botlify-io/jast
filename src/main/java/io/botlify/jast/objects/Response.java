package io.botlify.jast.objects;

import com.sun.net.httpserver.HttpExchange;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
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

    private final List<Header> responseHeaders = new ArrayList<>();

    /*
     $      Constructors.
     */

    public Response(@NotNull final HttpExchange request) {
        this.exchange = request;
    }

    /*
     $      Public methods.
     */

    // Header.

    public void addHeader(@NotNull final String key,
                          @NotNull final String value) {
        responseHeaders.add(new Header(key, value));
    }

    public void addHeader(@NotNull final Header header) {
        responseHeaders.add(header);
    }

    public void removeHeaders(@NotNull final String name) {
        responseHeaders.removeIf(header -> header.getName().equals(name));
    }

    public void removeHeaders(@NotNull final Header header) {
        responseHeaders.remove(header);
    }

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

    // Send file.

    public void sendFile(final int code,
                         @NotNull final File file) {
        setCode(code);
        exchange.getResponseHeaders().add("Content-Type", contentType.toString());
        try {
            exchange.sendResponseHeaders(code, file.length());
            exchange.getResponseBody().write(file.toString().getBytes());
            exchange.close();
        } catch (IOException e) {
            log.error("Error while sending response: {}", e.getMessage());
        }
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
            // Add all headers.
            for (final Header header : responseHeaders)
                exchange.getResponseHeaders().add(header.getName(), header.getValue());
            exchange.sendResponseHeaders(200, 0);
            exchange.getResponseBody().write(object.toString().getBytes());
            exchange.close();
        } catch (IOException e) {
            log.error("Error while sending response: {}", e.getMessage());
        }
    }

}
