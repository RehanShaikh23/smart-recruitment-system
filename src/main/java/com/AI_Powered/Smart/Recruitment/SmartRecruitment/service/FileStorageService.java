package com.AI_Powered.Smart.Recruitment.SmartRecruitment.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path root = Paths.get("uploads");

    public FileStorageService() throws IOException {
        Files.createDirectories(root);
    }

    public String store(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), root.resolve(filename));
        return filename;
    }

    public byte[] load(String filename) throws IOException {

        filename = filename.replace("\\", "/");


        if (filename.startsWith("uploads/")) {
            filename = filename.substring(8);
        }

        filename = filename.replaceFirst("^uploads/", "");

        Path path = root.resolve(filename).normalize();
        System.out.println("DEBUG: Trying to load file -> " + path.toAbsolutePath());

        if (!Files.exists(path)) {
            throw new FileNotFoundException("File not found: " + filename);
        }
        return Files.readAllBytes(path);
    }


    public void delete(String filePath) throws IOException {
        filePath = filePath.replace("\\", "/");
        Path path = root.resolve(filePath).normalize();
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    public String getFileName(String filePath) {
        filePath = filePath.replace("\\", "/");
        return Paths.get(filePath).getFileName().toString();
    }

    public boolean exists(String filePath) {
        filePath = filePath.replace("\\", "/");
        return Files.exists(root.resolve(filePath).normalize());
    }
}
