package com.example.bookstore.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageService {

    private static final String IMAGE_DIR = "src/main/resources/com/example/bookstore/images/";
    private static final String IMAGE_PATH_PREFIX = "com/example/bookstore/images/";

    public String saveImage(File sourceFile, String targetFileName) throws IOException {
        if (sourceFile == null) {
            throw new IllegalArgumentException("Source file cannot be null");
        }

        Path destPath = Paths.get(IMAGE_DIR + targetFileName);

        // Ensure directory exists
        if (!Files.exists(destPath.getParent())) {
            Files.createDirectories(destPath.getParent());
        }

        Files.copy(sourceFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
        return IMAGE_PATH_PREFIX + targetFileName;
    }

    public void deleteImage(String fileName) {
        if (fileName == null || fileName.isEmpty() || fileName.equalsIgnoreCase("sample.jpeg")) {
            return;
        }

        // Handle full path or just filename
        String actualFileName = extractFileName(fileName);

        Path path = Paths.get(IMAGE_DIR + actualFileName);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.err.println("Failed to delete image: " + e.getMessage());
        }
    }

    public String extractFileName(String path) {
        if (path == null || path.isBlank()) {
            return "";
        }
        int idx = path.lastIndexOf('/');
        if (idx == -1) {
            return path;
        }
        return path.substring(idx + 1);
    }

    public String getExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }
}
