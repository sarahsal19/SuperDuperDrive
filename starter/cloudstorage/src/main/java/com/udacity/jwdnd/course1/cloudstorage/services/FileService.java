package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    void uploadFile(MultipartFile uploadedFile, Authentication authentication, Model model) throws IOException;
    List<File> getAllFiles(String username);
    File getFile(Integer fileId);
    void deleteFile(Integer fileId, Model model);
}
