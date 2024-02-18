package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model, Authentication authentication) throws IOException {
        fileService.uploadFile(file, authentication, model);
        return "result";
    }

    @GetMapping(value = "/view-file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> viewFile(@RequestParam("fileId") Integer fileId) {
        File file = fileService.getFile(fileId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ file.getFileName() +"\"")
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(file.getFileData());
    }

    @GetMapping("/delete-file")
    public String deleteFile(@RequestParam Integer fileId, Model model) {
        fileService.deleteFile(fileId, model);
        return "result";
    }
}

