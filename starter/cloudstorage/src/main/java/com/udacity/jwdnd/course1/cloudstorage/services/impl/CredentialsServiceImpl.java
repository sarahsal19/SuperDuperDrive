package com.udacity.jwdnd.course1.cloudstorage.services.impl;


import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CredentialsServiceImpl implements CredentialsService {

    private final CredentialsMapper credentialsMapper;
    private final UserService userService;
    private final EncryptionService encryptionService;

    @Override
    public void createCredential(Credential credential, Authentication authentication, Model model) {

        String username = authentication.getPrincipal().toString();

        Integer userId = userService.getUser(username).getUserId();
        String encodedKey = encodeKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        credential.setUserId(userId);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

        // update credential if exist
        if (credentialsMapper.getCredential(credential.getCredentialId()) != null) {
            credentialsMapper.updateCredential(credential);
            model.addAttribute("resultSuccess", true);
            log.info("Credential updated successfully with the following values: {} ", credential);
            return;
        }

        if (credentialsMapper.getCredentialByUsername(username) != null) {
            model.addAttribute("resultError", "A credential with this username already exist!");
            return;
        }

        int createdCredential = credentialsMapper.createCredential(credential);

        if (createdCredential > 0) {
            model.addAttribute("resultSuccess", true);
            log.info("Credential created successfully with the following values: {} ", credential);
            return;
        }

        model.addAttribute("resultError", "Something went wrong, please try again!");
        log.error("Fail in creating credential with the following values: {} ", credential);

    }

    @Override
    public List<Credential> getAllCredentials(String username) {
        Integer userId = userService.getUser(username).getUserId();
        return credentialsMapper.getAllCredentials(userId);
    }
    @Override
    public void deleteCredential(Integer credentialId, Model model) {
        credentialsMapper.deleteCredential(credentialId);
        model.addAttribute("resultSuccess",true);
    }

    private String encodeKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        return encodedKey;
    }

}