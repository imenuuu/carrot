package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor


public class GetProductRes {
    private String ProductImgUrl;
    private String Title;
    private String Emdong;
    private String Lasttime;
    private Integer Price;
    private Long likedcnt;
    private Long chatcnt;


}
