package com.example.diploma.service.impl;

import com.example.diploma.entity.Master;
import com.example.diploma.entity.Photo;
import com.example.diploma.repository.MasterRepository;
import com.example.diploma.repository.PhotoRepository;
import com.example.diploma.service.FileService;
import com.example.diploma.service.MasterService;
import com.example.diploma.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    private final int FILE_NAME_LENGTH = 11;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private MasterService masterService;
    @Autowired
    private MasterRepository masterRepository;
    @Autowired
    private RandomStringGenerator randomStringGenerator;

    public void saveFile(MultipartFile file, int id) {
        try {
            String fileExtension = getFileExtension(file.getOriginalFilename());
            String fileName = randomStringGenerator.generateRandomString(FILE_NAME_LENGTH);

            byte[] fileByte = file.getBytes();
            Path path = Paths.get("C:/Users/37529/Desktop/TestTask/Diploma/src/main/resources/templates/uploads/" + fileName + "." + fileExtension);
            Files.write(path, fileByte);
            Photo photo = new Photo();
            photo.setFileName(fileName + "." + fileExtension);
            Master masterById = masterService.getById(id);
            List<Master> masters = new ArrayList<>();
            masters.add(masterById);
            photo.setMasters(masters);
            photoRepository.save(photo);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileExtension(String fileFullName) {
        if (fileFullName.lastIndexOf(".") != -1 && fileFullName.lastIndexOf(".") != 0) {
            return fileFullName.substring(fileFullName.lastIndexOf(".") + 1);
        } else {
            return "";
        }

    }

    public void deleteFile(int id) {
        photoRepository.deleteById(id);
    }
}
