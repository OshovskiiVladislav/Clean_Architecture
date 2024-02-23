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
    private YandexCloud yandexCloud;

    @Getter
    @Setter
    public static class Local {
        private String directory;
    }

    @Getter
    @Setter
    public static class YandexCloud {
        private String serviceEndpoint;
        private String signingRegion;
        private String accessKey;
        private String secretKey;
        private String bucketName;
    }

}

