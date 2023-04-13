package com.example.diploma.controller;

import com.example.diploma.entity.Master;
import com.example.diploma.service.FileService;
import com.example.diploma.service.MasterService;
import com.example.diploma.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/files")
public class FileController {
    @Autowired
    FileService fileService;
    @Autowired
    MasterService masterService;

    @GetMapping("{id}/new")
    public String imageForm(@PathVariable("id") int id, Model model) {
        Master master = masterService.getById(id);
        model.addAttribute("master", master);
        return "new_master_photo";

    }

    @PostMapping("/{id}")
    public String loadImage(@RequestParam("file") MultipartFile file, @PathVariable("id") int id) {
        try {
            fileService.saveFile(file, id);
            return "redirect:/masters/" + id;

        } catch (Exception e) {
            return "error";
        }

    }

    @RequestMapping("/{fileName}")
    public void showPicture(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        File imgFile = new File("C:/Users/37529/Desktop/TestTask/Diploma/src/main/resources/templates/uploads/" + fileName);
        responseFile(response, imgFile);
    }

    private void responseFile(HttpServletResponse response, File imgFile) {
        try (InputStream is = new FileInputStream(imgFile);
             OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[1024]; // пул буферов потока файлов изображений
            while (is.read(buffer) != -1) {
                os.write(buffer);
            }
            os.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        fileService.deleteFile(id);
        return "redirect:/masters";
    }


}
