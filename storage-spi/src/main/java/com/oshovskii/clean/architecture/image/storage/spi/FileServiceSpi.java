package com.oshovskii.clean.architecture.image.storage.spi;

import com.oshovskii.clean.architecture.image.storage.models.GetImageWrapper;
import com.oshovskii.clean.architecture.image.storage.models.SaveFileWrapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

public interface FileServiceSpi {

    Optional<SaveFileWrapper> storeImage(MultipartFile file);

    Optional<GetImageWrapper> getImage(UUID uuid);

}

