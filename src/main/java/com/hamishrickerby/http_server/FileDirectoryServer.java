package com.hamishrickerby.http_server;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rickerbh on 16/08/2016.
 */
public class FileDirectoryServer {
    Path rootPath;

    public FileDirectoryServer(String rootDirectory) {
        rootPath = Paths.get(rootDirectory);
    }

    public boolean directoryExists(String filename) {
        Path filePath = Paths.get(rootPath.toString(), filename);
        return Files.exists(filePath) && Files.isDirectory(filePath);
    }

    public List<String> get(String location) {
        Path path = Paths.get(rootPath.toString(), location);
        List<String> listing = new ArrayList<String>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path file : stream) {
                listing.add(file.getFileName().toString());
            }
        } catch (IOException e) {
            // Silently eat these
        }
        return listing;
    }
}
