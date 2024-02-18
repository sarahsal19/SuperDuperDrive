package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.udacity.jwdnd.course1.cloudstorage.constants.constants.MAX_FILE_SIZE;

@Service
@AllArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    private final UserService userService;
    private final FileMapper fileMapper;

    public void uploadFile(MultipartFile uploadedFile, Authentication authentication, Model model) throws IOException {
        String username = authentication.getPrincipal().toString();

        if (uploadedFile.isEmpty()) {
            model.addAttribute("fileError", "You are trying to upload an empty file, please try again!");
            return;
        }

        if (uploadedFile.getSize() > MAX_FILE_SIZE) {
            throw new MaxUploadSizeExceededException(uploadedFile.getSize());
        }

        File file = new File();
        file.setFileName(uploadedFile.getOriginalFilename());
        file.setContentType(uploadedFile.getContentType());
        file.setFileSize(Long.toString(uploadedFile.getSize()));
        file.setFileData(uploadedFile.getBytes());
        file.setUserId(userService.getUser(username).getUserId());

        if(fileMapper.getFileByNameAndUserId(file.getFileName(), file.getUserId()) != null){
            model.addAttribute("resultError", "The file is already uploaded!");
            return;
        }

        int addedFile = fileMapper.addFile(file);
        if( addedFile > 0) {
            model.addAttribute("resultSuccess", true);
            log.info("File uploaded successfully with the following values: {} ", uploadedFile);
            return;
        }
        model.addAttribute("resultError", "Something went wrong, please try again!");
        log.error("Fail in uploading file with the following values: {} ", uploadedFile);

    }

    public List<File> getAllFiles(String username) {
        Integer userId = userService.getUser(username).getUserId();
        return fileMapper.getAllFiles(userId);
    }

    public File getFile(Integer fileId) {
        return fileMapper.getFileById(fileId);
    }

    public void deleteFile(Integer fileId, Model model) {
        fileMapper.deleteFile(fileId);
        model.addAttribute("resultSuccess",true);
    }
}

