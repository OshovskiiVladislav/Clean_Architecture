package com.oshovsky.clean.architecture.image.storage.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.oshovsky.clean.architecture.image.storage.configuration.ApplicationProperties;
import com.oshovsky.clean.architecture.image.storage.models.GetImageWrapper;
import com.oshovsky.clean.architecture.image.storage.models.SaveFileWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Profile("cloud")
@RequiredArgsConstructor
public class YandexCloudService implements FileService {

    private static final String ORIGINAL_FILENAME_KEY = "ORIGINAL_FILENAME";

    private final AmazonS3 yandexCloudApi;
    private final ApplicationProperties properties;

    @Override
    public Optional<SaveFileWrapper> storeImage(MultipartFile image) {
        try {
            UUID uuid = UUID.randomUUID();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(image.getContentType());
            metadata.setContentLength(image.getSize());
            metadata.setUserMetadata(Map.of(ORIGINAL_FILENAME_KEY, image.getOriginalFilename()));
            yandexCloudApi.putObject(
                    properties.getYandexCloud().getBucketName(),
                    uuid + "/" + image.getOriginalFilename(),
                    image.getInputStream(), metadata
            );
            return Optional.of(new SaveFileWrapper(uuid));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<GetImageWrapper> getImage(UUID uuid) {
        try {
            ObjectListing inBucket = yandexCloudApi.listObjects(
                    properties.getYandexCloud().getBucketName(),
                    uuid.toString()
            );
            S3Object object = yandexCloudApi.getObject(
                    properties.getYandexCloud().getBucketName(),
                    inBucket.getObjectSummaries().get(0).getKey()
            );
            String contentType = object.getObjectMetadata().getContentType();
            String filename = object.getObjectMetadata().getUserMetaDataOf(ORIGINAL_FILENAME_KEY);
            return Optional.of(new GetImageWrapper(
                    new InputStreamResource(object.getObjectContent()), filename, contentType)
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
