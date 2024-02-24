package com.oshovskii.clean.architecture.image.storage.spi.yandex.cloud.client;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.oshovskii.clean.architecture.image.storage.models.SaveFileRequest;
import com.oshovskii.clean.architecture.image.storage.spi.yandex.cloud.client.presentation.CloudObjectWrapper;
import com.oshovskii.clean.architecture.image.storage.spi.yandex.cloud.properties.CloudAccountProperties;

import java.util.Map;
import java.util.UUID;

public class YandexCloudClient {

    private static final String ORIGINAL_FILENAME_KEY = "ORIGINAL_FILENAME";

    private final AmazonS3 amazonS3;
    private final String bucketName;

    public YandexCloudClient(CloudAccountProperties properties) {
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(
                                properties.getAccessKey(),
                                properties.getSecretKey())))
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration(
                                properties.getServiceEndpoint(),
                                properties.getSigningRegion()))
                .build();
        this.bucketName = properties.getBucketName();
    }

    public UUID saveObjectInCloud(SaveFileRequest request) {
        UUID uuid = UUID.randomUUID();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(request.contentLength());
        metadata.setContentType(request.contentType());
        metadata.setUserMetadata(Map.of(ORIGINAL_FILENAME_KEY, request.originalFilename()));
        amazonS3.putObject(bucketName, uuid + "/" + request.originalFilename(), request.inputStream(), metadata);
        return uuid;
    }

    public CloudObjectWrapper getObjectFromCloud(UUID uuid) {
        ObjectListing inBucket = amazonS3.listObjects(bucketName, uuid.toString());
        S3Object object = amazonS3.getObject(bucketName, inBucket.getObjectSummaries().get(0).getKey());
        String filename = object.getObjectMetadata().getUserMetaDataOf(ORIGINAL_FILENAME_KEY);
        String contentType = object.getObjectMetadata().getContentType();
        return new CloudObjectWrapper(object.getObjectContent(), filename, contentType);
    }

}
