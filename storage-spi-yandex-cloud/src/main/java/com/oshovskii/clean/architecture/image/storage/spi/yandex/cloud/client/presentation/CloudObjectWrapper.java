package com.oshovskii.clean.architecture.image.storage.spi.yandex.cloud.client.presentation;

import com.amazonaws.services.s3.model.S3ObjectInputStream;

public record CloudObjectWrapper(S3ObjectInputStream inputStream, String filename, String contentType) {}
