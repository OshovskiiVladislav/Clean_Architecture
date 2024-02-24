package com.oshovskii.clean.architecture.image.storage.usecase;

import com.oshovskii.clean.architecture.image.storage.api.FileServiceApi;
import com.oshovskii.clean.architecture.image.storage.spi.FileServiceSpi;
import com.oshovskii.clean.architecture.image.storage.models.GetImageWrapper;
import com.oshovskii.clean.architecture.image.storage.models.SaveFileWrapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

public class FileOperationsUseCase implements FileServiceApi {

    private final FileServiceSpi fileServiceSpi;

    public FileOperationsUseCase(FileServiceSpi fileServiceSpi) {
        this.fileServiceSpi = fileServiceSpi;
    }

    @Override
    public Optional<SaveFileWrapper> storeImage(MultipartFile file) {
        return fileServiceSpi.storeImage(file);
    }

    @Override
    public Optional<GetImageWrapper> getImage(UUID uuid) {
        return fileServiceSpi.getImage(uuid);
    }

}
