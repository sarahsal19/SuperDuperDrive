package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.List;

public interface CredentialsService {
    void createCredential(Credential credential, Authentication authentication, Model model);
    List<Credential> getAllCredentials(String username);
    void deleteCredential(Integer credentialId, Model model);
}
