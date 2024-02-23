package com.oshovsky.clean.architecture.image.storage.services;

import com.oshovsky.clean.architecture.image.storage.configuration.ApplicationProperties;
import com.oshovsky.clean.architecture.image.storage.models.GetImageWrapper;
import com.oshovsky.clean.architecture.image.storage.models.SaveFileWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ApplicationProperties properties;

    public Optional<SaveFileWrapper> storeImage(MultipartFile image) {
        try {
            UUID uuid = UUID.randomUUID();
            Path directory = Files.createDirectories(Path.of(properties.getLocal().getDirectory(), uuid.toString()));
            Path file = Path.of(directory.toString(), image.getOriginalFilename());
            Files.write(file, image.getBytes());
            return Optional.of(new SaveFileWrapper(uuid));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public Optional<GetImageWrapper> getImage(UUID uuid) {
        try {
            Path directory = Path.of(properties.getLocal().getDirectory(), uuid.toString());
            if (Files.exists(directory)) {
                Path file = directory.toFile().listFiles()[0].toPath();
                String mimeType = Files.probeContentType(file);
                return Optional.of(new GetImageWrapper(new InputStreamResource(Files.newInputStream(file)), file.getFileName().toString(), mimeType));
            }
            return Optional.empty();
        } catch (IOException e) {
            return Optional.empty();
        }
    }

}
