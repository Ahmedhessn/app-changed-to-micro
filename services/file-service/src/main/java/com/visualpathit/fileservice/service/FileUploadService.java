package com.visualpathit.fileservice.service;

import com.visualpathit.common.dto.UserDto;
import com.visualpathit.fileservice.config.FileProperties;
import com.visualpathit.fileservice.config.UserServiceProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

@Service
public class FileUploadService {

    private final FileProperties fileProperties;
    private final RestClient userRestClient;

    public FileUploadService(FileProperties fileProperties, UserServiceProperties userServiceProperties) {
        this.fileProperties = fileProperties;
        this.userRestClient = RestClient.builder()
                .baseUrl(userServiceProperties.baseUrl())
                .build();
    }

    public Map<String, String> upload(String name, String userName, MultipartFile file) {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
        }
        try {
            File dir = new File(fileProperties.uploadDir());
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = name + ".png";
            File serverFile = new File(dir, fileName);

            UserDto user = userRestClient.get()
                    .uri("/api/users/username/{username}", userName)
                    .retrieve()
                    .body(UserDto.class);

            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }

            user.setProfileImg(fileName);
            user.setProfileImgPath(serverFile.getAbsolutePath());
            userRestClient.put()
                    .uri("/api/users/{username}", userName)
                    .body(user)
                    .retrieve()
                    .toBodilessEntity();

            try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
                stream.write(file.getBytes());
            }

            return Map.of(
                    "message", "You successfully uploaded file=" + fileName,
                    "path", serverFile.getAbsolutePath()
            );
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Upload failed: " + e.getMessage());
        }
    }
}
