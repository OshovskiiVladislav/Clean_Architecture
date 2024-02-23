package com.oshovsky.clean.architecture.image.storage.services;

import com.oshovsky.clean.architecture.image.storage.models.GetImageWrapper;
import com.oshovsky.clean.architecture.image.storage.models.SaveFileWrapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

public interface FileService {

    Optional<SaveFileWrapper> storeImage(MultipartFile file);

    Optional<GetImageWrapper> getImage(UUID uuid);

}
