package com.oshovskii.clean.architecture.image.storage.spi.yandex.cloud;

import com.oshovskii.clean.architecture.image.storage.models.GetImageWrapper;
import com.oshovskii.clean.architecture.image.storage.models.SaveFileWrapper;
import com.oshovskii.clean.architecture.image.storage.spi.FileServiceSpi;
import com.oshovskii.clean.architecture.image.storage.spi.yandex.cloud.client.YandexCloudClient;
import com.oshovskii.clean.architecture.image.storage.spi.yandex.cloud.client.presentation.CloudObjectWrapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

public class YandexCloudService implements FileServiceSpi {

    private final YandexCloudClient yandexClient;

    public YandexCloudService(YandexCloudClient yandexCloudClient) {
        this.yandexClient = yandexCloudClient;
    }

    @Override
    public Optional<SaveFileWrapper> storeImage(MultipartFile image) {
        try {
            UUID uuid = yandexClient.saveObjectInCloud(image);
            return Optional.of(new SaveFileWrapper(uuid));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<GetImageWrapper> getImage(UUID uuid) {
        try {
            CloudObjectWrapper objectWrapper = yandexClient.getObjectFromCloud(uuid);
            return Optional.of(new GetImageWrapper(
                    new InputStreamResource(objectWrapper.inputStream()),
                    objectWrapper.filename(),
                    objectWrapper.contentType())
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
