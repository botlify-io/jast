package fr.botlify.jast.enums;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * This enum represents the HTTP methods from the RFC 2616.<br />
 * <a href="https://www.rfc-editor.org/rfc/rfc2616">View the RFC.</a>
 */
@ToString
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

    private final boolean body;

    HttpMethod(final boolean hasBody) {
        this.body = hasBody;
    }

    public boolean hasBody() {
        return (body);
    }
}
