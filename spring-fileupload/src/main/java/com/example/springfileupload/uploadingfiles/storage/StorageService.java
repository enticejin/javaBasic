package com.example.springfileupload.uploadingfiles.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    void init();

    void store(MultipartFile file);

    Stream<Path> loadAll() throws IOException;

    Path load(String fileName);

    Resource loadAsResource(String fileName);

    void deleteAll();
}
