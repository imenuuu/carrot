package com.example.demo.src.product.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class GetProductDetailRes {
    private String ProductImgUrl;
    private String ProfileImageUrl;
    private String UserName;
    private String Emdong;
    private BigDecimal MannerT;
    private String Title;
    private String CategoryName;
    private String LastTime;
    private String Description;
    private Long LikedCnt;
    private Long ChatCnt;
    private int ViewCount;
    private int Price;
}
