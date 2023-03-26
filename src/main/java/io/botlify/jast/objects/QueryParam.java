package io.botlify.jast.objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@EqualsAndHashCode
@ToString
public class QueryParam {

    @NotNull @Getter
    public final String key;

    @NotNull @Getter
    public final String value;

    public QueryParam(@NotNull final String key,
                      @NotNull final String value) {
        this.key = key;
        this.value = value;
    }

}
