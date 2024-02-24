package com.oshovskii.clean.architecture.image.storage;

import com.oshovskii.clean.architecture.image.storage.spi.yandex.cloud.YandexCloudService;
import com.oshovskii.clean.architecture.image.storage.spi.yandex.cloud.client.YandexCloudClient;
import com.oshovskii.clean.architecture.image.storage.spi.yandex.cloud.properties.CloudAccountProperties;
import com.oshovskii.clean.architecture.image.storage.usecase.FileOperationsUseCase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebMvcToYandexCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebMvcToYandexCloudApplication.class, args);
    }

    @Bean
    @ConfigurationProperties("image-storage")
    public CloudAccountProperties cloudAccountProperties() {
        return new CloudAccountProperties();
    }

    @Bean
    public FileOperationsUseCase fileOperationsUseCase(CloudAccountProperties properties) {
        return new FileOperationsUseCase(
                new YandexCloudService(
                        new YandexCloudClient(properties)
                )
        );
    }

}
