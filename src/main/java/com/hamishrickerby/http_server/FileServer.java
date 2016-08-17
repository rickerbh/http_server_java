package com.hamishrickerby.http_server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by rickerbh on 17/08/2016.
 */
public class FileServer {
    Path rootPath;

    public FileServer(String rootDirectory) {
        rootPath = Paths.get(rootDirectory);
    }

    public boolean fileExists(String filename) {
        Path filePath = filePath(filename);
        return Files.exists(filePath) && Files.isRegularFile(filePath);
    }

    private Path filePath(String filename) {
        return Paths.get(rootPath.toString(), filename);
    }

    public byte[] get(String filename) {
        byte[] fileContents = new byte[0];
        try {
            if (fileExists(filename)) {
                Path filePath = filePath(filename);
                fileContents = Files.readAllBytes(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return fileContents;
        }
    }
}
