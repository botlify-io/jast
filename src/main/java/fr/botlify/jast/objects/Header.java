package fr.botlify.jast.objects;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents a header in a HTTP request.
 */
public class Header {

    /**
     * The name of the header.
     */
    @NotNull @Getter
    private final String name;

    /**
     * The value of the header.
     */
    @NotNull @Getter
    private final String value;

    /**
     * Create a new header.
     * @param name The name of the header.
     * @param value The value of the header.
     */
    public Header(@NotNull final String name,
                  @NotNull final String value) {
        this.name = name;
        this.value = value;
    }

}
