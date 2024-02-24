package com.oshovskii.clean.architecture.image.storage.spi.local.filesystem;

import com.oshovskii.clean.architecture.image.storage.models.GetImageWrapper;
import com.oshovskii.clean.architecture.image.storage.models.SaveFileWrapper;
import com.oshovskii.clean.architecture.image.storage.spi.FileServiceSpi;
import com.oshovskii.clean.architecture.image.storage.spi.local.filesystem.properties.FileSystemProperties;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

public class LocalFileSystemService implements FileServiceSpi {

    private final FileSystemProperties properties;

    public LocalFileSystemService(FileSystemProperties properties) {
        this.properties = properties;
    }

    @Override
    public Optional<SaveFileWrapper> storeImage(MultipartFile image) {
        try {
            UUID uuid = UUID.randomUUID();
            Path directory = Files.createDirectories(Path.of(properties.getDirectory(), uuid.toString()));
            Path file = Path.of(directory.toString(), image.getOriginalFilename());
            Files.write(file, image.getBytes());
            return Optional.of(new SaveFileWrapper(uuid));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<GetImageWrapper> getImage(UUID uuid) {
        try {
            Path directory = Path.of(properties.getDirectory(), uuid.toString());
            if (Files.exists(directory)) {
                Path file = directory.toFile().listFiles()[0].toPath();
                String mimeType = Files.probeContentType(file);
                return Optional.of(new GetImageWrapper(
                        new InputStreamResource(Files.newInputStream(file)),
                        file.getFileName().toString(),
                        mimeType)
                );
            }
            return Optional.empty();
        } catch (IOException e) {
            return Optional.empty();
        }
    }

}
