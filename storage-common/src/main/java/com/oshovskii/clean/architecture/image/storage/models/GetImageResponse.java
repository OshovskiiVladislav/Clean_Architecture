package com.oshovskii.clean.architecture.image.storage.models;

import java.io.InputStream;

public record GetImageResponse(InputStream inputStream, String filename, String mimeType) {}
