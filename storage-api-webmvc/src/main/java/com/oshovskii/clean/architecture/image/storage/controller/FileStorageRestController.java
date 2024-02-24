package com.oshovskii.clean.architecture.image.storage.controller;

import com.oshovskii.clean.architecture.image.storage.api.FileServiceApi;
import com.oshovskii.clean.architecture.image.storage.models.GetImageRequest;
import com.oshovskii.clean.architecture.image.storage.models.SaveFileRequest;
import com.oshovskii.clean.architecture.image.storage.models.SaveFileResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/images")
public class FileStorageRestController {

    private final FileServiceApi fileServiceApi;

    public FileStorageRestController(FileServiceApi fileServiceApi) {
        this.fileServiceApi = fileServiceApi;
    }

    @PostMapping
    public ResponseEntity<UUID> storeNewImage(@RequestAttribute MultipartFile image) {
        try {
            return fileServiceApi.storeImage(new SaveFileRequest(
                    image.getInputStream(), image.getOriginalFilename(), image.getSize(), image.getContentType()
                    ))
                    .map(SaveFileResponse::uuid)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Resource> getImageByUuid(@PathVariable("uuid") UUID imageUuid) {
        return fileServiceApi.getImage(new GetImageRequest(imageUuid))
                .map(wrapper -> ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(wrapper.mimeType()))
                        .body((Resource) new InputStreamResource(wrapper.inputStream())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
