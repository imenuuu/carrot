package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int Id;
    private String UserName;
    private String UserId;
    private String PassWord;
    private String Email;
}
