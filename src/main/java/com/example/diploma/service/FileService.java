package com.example.diploma.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void saveFile(MultipartFile file, int id);
    void deleteFile(int id);
}
