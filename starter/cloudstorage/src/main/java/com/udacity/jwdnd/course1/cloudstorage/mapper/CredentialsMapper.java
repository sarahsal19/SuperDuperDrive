package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {
    @Insert("INSERT INTO CREDENTIALS (url, username, password, key, userId) VALUES (#{url}, #{username}, #{password}, #{key}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int createCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userId =#{userId}")
    List<Credential> getAllCredentials(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId =#{credentialId}")
    Credential getCredential(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE username = #{username}")
    Credential getCredentialByUsername(String username);

    @Update("UPDATE CREDENTIALS SET url =#{url}, username =#{username}, password =#{password}, key =#{key} WHERE credentialId =#{credentialId}")
    int updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    void deleteCredential(Integer credentialId);
}

