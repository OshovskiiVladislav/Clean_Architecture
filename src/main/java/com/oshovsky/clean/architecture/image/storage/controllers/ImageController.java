package com.oshovsky.clean.architecture.image.storage.controllers;

import com.oshovsky.clean.architecture.image.storage.models.SaveFileWrapper;
import com.oshovsky.clean.architecture.image.storage.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {

    private final FileService imageService;

    @PostMapping
    public ResponseEntity<UUID> storeNewImage(@RequestAttribute MultipartFile image) {
        try {
            return imageService.storeImage(image)
                    .map(SaveFileWrapper::uuid)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Resource> getImageByUuid(@PathVariable("uuid") UUID imageUuid) {
        return imageService.getImage(imageUuid)
                .map(wrapper -> ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(wrapper.mimeType()))
                        .body(wrapper.resource()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
