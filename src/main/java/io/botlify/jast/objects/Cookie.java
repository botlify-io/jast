package io.botlify.jast.objects;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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

    @Deprecated
    public static @NotNull Cookie parse(@NotNull final String cookie) {
        final String[] split = cookie.split("=");
        return new Cookie(split[0], split[1]);
    }

    @Deprecated
    public static @NotNull List<Cookie> parse(@NotNull final List<String> cookies) {
        final List<Cookie> cookieList = new ArrayList<>(cookies.size());
        for (final String cookie : cookies)
            cookieList.add(parse(cookie));
        return (cookieList);
    }

}
