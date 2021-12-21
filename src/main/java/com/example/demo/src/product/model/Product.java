package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor


public class Product {
    private String ProductImgUrl;
    private String Title;
    private String Emdong;
    private String lasttimee;
    private Integer Price;
    private String UserName;
    private Long likedcnt;
    private Long chatcnt;


}
