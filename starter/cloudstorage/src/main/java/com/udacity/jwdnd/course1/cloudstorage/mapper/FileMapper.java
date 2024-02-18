package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, fileData, userId) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{fileData}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFile(File file);

    @Select("SELECT * FROM FILES WHERE fileName=#{fileName} AND userId=#{userId}")
    File getFileByNameAndUserId(String fileName, Integer userId);

    @Select("SELECT * FROM FILES WHERE userId=#{userId}")
    List<File> getAllFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId=#{fileId}")
    File getFileById(Integer fileId);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId}")
    void deleteFile(Integer fileId);
}
