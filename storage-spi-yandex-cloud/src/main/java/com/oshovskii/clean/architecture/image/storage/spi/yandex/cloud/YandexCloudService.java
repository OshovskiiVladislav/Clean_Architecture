package com.oshovskii.clean.architecture.image.storage.spi.yandex.cloud;

import com.oshovskii.clean.architecture.image.storage.models.GetImageRequest;
import com.oshovskii.clean.architecture.image.storage.models.GetImageResponse;
import com.oshovskii.clean.architecture.image.storage.models.SaveFileRequest;
import com.oshovskii.clean.architecture.image.storage.models.SaveFileResponse;
import com.oshovskii.clean.architecture.image.storage.spi.FileServiceSpi;
import com.oshovskii.clean.architecture.image.storage.spi.yandex.cloud.client.YandexCloudClient;
import com.oshovskii.clean.architecture.image.storage.spi.yandex.cloud.client.presentation.CloudObjectWrapper;

import java.util.Optional;
import java.util.UUID;

public class YandexCloudService implements FileServiceSpi {

    private final YandexCloudClient yandexClient;

    public YandexCloudService(YandexCloudClient yandexCloudClient) {
        this.yandexClient = yandexCloudClient;
    }

    @Override
    public Optional<SaveFileResponse> storeImage(SaveFileRequest request) {
        try {
            UUID uuid = yandexClient.saveObjectInCloud(request);
            return Optional.of(new SaveFileResponse(uuid));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<GetImageResponse> getImage(GetImageRequest request) {
        try {
            CloudObjectWrapper objectWrapper = yandexClient.getObjectFromCloud(request.uuid());
            return Optional.of(new GetImageResponse(
                    objectWrapper.inputStream(), objectWrapper.filename(), objectWrapper.contentType()
            ));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
