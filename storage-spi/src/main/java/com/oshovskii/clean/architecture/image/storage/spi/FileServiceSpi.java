package com.oshovskii.clean.architecture.image.storage.spi;

import com.oshovskii.clean.architecture.image.storage.models.GetImageRequest;
import com.oshovskii.clean.architecture.image.storage.models.GetImageResponse;
import com.oshovskii.clean.architecture.image.storage.models.SaveFileRequest;
import com.oshovskii.clean.architecture.image.storage.models.SaveFileResponse;

import java.util.Optional;

public interface FileServiceSpi {

    Optional<SaveFileResponse> storeImage(SaveFileRequest request);

    Optional<GetImageResponse> getImage(GetImageRequest request);

}
