package io.botlify.jast.config;

import lombok.Builder;
import lombok.Getter;

@Builder
public class JastConfig {

    @Getter @Builder.Default
    private final boolean enableTrailingSlash = true;

    public static JastConfig DEFAULT = JastConfig.builder()
            .build();

}
