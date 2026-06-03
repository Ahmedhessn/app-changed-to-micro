package com.visualpathit.fileservice.controller;

import com.visualpathit.fileservice.service.FileUploadService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileUploadService fileUploadService;

    public FileController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/upload")
    public Map<String, String> upload(
            @RequestParam("name") String name,
            @RequestParam("userName") String userName,
            @RequestParam("file") MultipartFile file) {
        return fileUploadService.upload(name, userName, file);
    }
}
