package com.oshovsky.clean.architecture.image.storage.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("image-storage")
public class ApplicationProperties {

    private Local local;

    @Getter
    @Setter
    public static class Local {
        private String directory;
    }

}
