package com.oshovskii.clean.architecture.image.storage.models;

import java.io.InputStream;

public record SaveFileRequest(InputStream inputStream, String originalFilename, long contentLength, String contentType) {}
