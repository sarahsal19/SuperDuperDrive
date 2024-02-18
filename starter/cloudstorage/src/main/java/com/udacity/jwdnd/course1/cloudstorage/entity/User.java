package com.udacity.jwdnd.course1.cloudstorage.entity;


import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer userId;
    private String username;
    private String salt;
    private String password;
    private String firstName;
    private String lastName;
}
