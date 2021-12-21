package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PostProductReq {
    private int UploaderId;
    private int CategoryId;
    private String Title;
    private String Description;
    private int Price;
}
