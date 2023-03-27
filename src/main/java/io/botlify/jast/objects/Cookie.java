package io.botlify.jast.objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@ToString
@EqualsAndHashCode
public class Cookie {

    @NotNull @Getter
    private final String name;

    @NotNull @Getter
    private final String value;

    public Cookie(@NotNull final String name,
                  @NotNull final String value) {
        this.name = name;
        this.value = value;
    }

    /*
     $      Public static methods
     */

    public static @NotNull List<Cookie> parseCookieHeader(@NotNull final String cookieHeader) {
        final String[] split = cookieHeader.split(";");
        final List<Cookie> cookies = new ArrayList<>(split.length);
        for (String cookie : split) {
            final Cookie cookieParsed = parse(cookie);
            if (cookieParsed != null)
                cookies.add(cookieParsed);
        }
        return (cookies);
    }

    /**
     * Parse a cookie string into a {@link Cookie} object.
     * @param cookie The cookie string to parse.
     * @return The parsed cookie, or null if the cookie string is invalid.
     */
    public static @Nullable Cookie parse(@NotNull final String cookie) {
        try {
            final String[] split = cookie.split("=");
            if (split.length != 2)
                throw new Exception("Invalid cookie format");
            return (new Cookie(split[0], split[1]));
        } catch (Exception e) {
            log.warn("Failed to parse cookie {}: {}", cookie, e.getMessage());
        }
        return (null);
    }

}
