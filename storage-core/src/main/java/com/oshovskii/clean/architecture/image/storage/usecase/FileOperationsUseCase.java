package com.oshovskii.clean.architecture.image.storage.usecase;

import com.oshovskii.clean.architecture.image.storage.api.FileServiceApi;
import com.oshovskii.clean.architecture.image.storage.models.GetImageRequest;
import com.oshovskii.clean.architecture.image.storage.models.GetImageResponse;
import com.oshovskii.clean.architecture.image.storage.models.SaveFileRequest;
import com.oshovskii.clean.architecture.image.storage.models.SaveFileResponse;
import com.oshovskii.clean.architecture.image.storage.spi.FileServiceSpi;

import java.util.Optional;

public class FileOperationsUseCase implements FileServiceApi {

    private final FileServiceSpi fileServiceSpi;

    public FileOperationsUseCase(FileServiceSpi fileServiceSpi) {
        this.fileServiceSpi = fileServiceSpi;
    }

    @Override
    public Optional<SaveFileResponse> storeImage(SaveFileRequest request) {
        return fileServiceSpi.storeImage(request);
    }

    @Override
    public Optional<GetImageResponse> getImage(GetImageRequest request) {
        return fileServiceSpi.getImage(request);
    }

}
