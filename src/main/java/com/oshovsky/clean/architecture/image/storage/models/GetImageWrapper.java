package com.oshovsky.clean.architecture.image.storage.models;

import org.springframework.core.io.Resource;

public record GetImageWrapper(Resource resource, String filename, String mimeType) {}
