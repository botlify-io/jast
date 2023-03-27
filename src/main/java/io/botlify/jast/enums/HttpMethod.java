package io.botlify.jast.enums;

import lombok.ToString;
import org.jetbrains.annotations.NotNull;

/**
 * This enum represents the HTTP methods from the RFC 2616.<br />
 * <a href="https://www.rfc-editor.org/rfc/rfc2616">View the RFC.</a>
 */
public enum HttpMethod {

    GET(false),
    POST(true),
    PUT(true),
    DELETE(false),
    HEAD(false),
    OPTIONS(false),
    CONNECT(false),
    TRACE(false),
    PATCH(true);

    private final boolean requestBody;

    HttpMethod(final boolean hasBody) {
        this.requestBody = hasBody;
    }

    public boolean hasRequestBody() {
        return (requestBody);
    }

    public boolean hasResponseBody() {
        return (this != HEAD);
    }

    @Override
    public @NotNull String toString() {
        return (name());
    }
}
