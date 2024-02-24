package com.oshovskii.clean.architecture.image.storage;

import com.oshovskii.clean.architecture.image.storage.spi.local.filesystem.LocalFileSystemService;
import com.oshovskii.clean.architecture.image.storage.spi.local.filesystem.properties.FileSystemProperties;
import com.oshovskii.clean.architecture.image.storage.usecase.FileOperationsUseCase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebMvcToLocalFileStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebMvcToLocalFileStorageApplication.class, args);
    }

    @Bean
    @ConfigurationProperties("image-storage")
    public FileSystemProperties fileSystemProperties() {
        return new FileSystemProperties();
    }

    @Bean
    public FileOperationsUseCase fileOperationsUseCase(FileSystemProperties properties) {
        return new FileOperationsUseCase(
                new LocalFileSystemService(properties)
        );
    }

}
