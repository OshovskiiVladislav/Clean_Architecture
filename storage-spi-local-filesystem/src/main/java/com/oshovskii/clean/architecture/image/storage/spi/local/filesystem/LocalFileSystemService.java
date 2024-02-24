package com.oshovskii.clean.architecture.image.storage.spi.local.filesystem;

import com.oshovskii.clean.architecture.image.storage.models.GetImageRequest;
import com.oshovskii.clean.architecture.image.storage.models.GetImageResponse;
import com.oshovskii.clean.architecture.image.storage.models.SaveFileRequest;
import com.oshovskii.clean.architecture.image.storage.models.SaveFileResponse;
import com.oshovskii.clean.architecture.image.storage.spi.FileServiceSpi;
import com.oshovskii.clean.architecture.image.storage.spi.local.filesystem.properties.FileSystemProperties;

import java.io.IOException;
import java.io.InputStream;
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
    public Optional<SaveFileResponse> storeImage(SaveFileRequest request) {
        try (InputStream in = request.inputStream()){
            UUID uuid = UUID.randomUUID();
            Path directory = Files.createDirectories(Path.of(properties.getDirectory(), uuid.toString()));
            Path file = Path.of(directory.toString(), request.originalFilename());
            Files.copy(in, file);
            return Optional.of(new SaveFileResponse(uuid));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<GetImageResponse> getImage(GetImageRequest request) {
        try {
            Path directory = Path.of(properties.getDirectory(), request.uuid().toString());
            if (Files.exists(directory)) {
                Path file = directory.toFile().listFiles()[0].toPath();
                String mimeType = Files.probeContentType(file);
                return Optional.of(
                        new GetImageResponse(Files.newInputStream(file), file.getFileName().toString(), mimeType)
                );
            }
            return Optional.empty();
        } catch (IOException e) {
            return Optional.empty();
        }
    }

}
