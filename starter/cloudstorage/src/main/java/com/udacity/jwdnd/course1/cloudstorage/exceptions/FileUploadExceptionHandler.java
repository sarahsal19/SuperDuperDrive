package com.udacity.jwdnd.course1.cloudstorage.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


@ControllerAdvice
public class FileUploadExceptionHandler {
    @ExceptionHandler //todo: response Status
    public String handleMaxSizeException(MaxUploadSizeExceededException ex, Model model) {
        model.addAttribute("resultError", "File size exceeded 5MB limit");
        return "result";
    }
}

