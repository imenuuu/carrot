package com.example.demo.src.product;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.product.model.*;

import java.util.List;


@RestController
@RequestMapping("/app/products")
public class ProductController {

    @Autowired
    private final ProductProvider productProvider;

    public ProductController(ProductProvider productProvider) {
        this.productProvider = productProvider;
    }
    /**
     * 상품조회 API
     * [GET]/products/:Title
     * 상품번호 및 등 조회API
     */


    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetProductRes>> getProducts(){
        try{
            List<GetProductRes> getProductRes =productProvider.getProducts();
            return new BaseResponse<>(getProductRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    //products/:CategoryId
    @ResponseBody
    @GetMapping("/{CategoryId}")
    public BaseResponse<List<GetProductRes>> getProduct(@PathVariable("CategoryId") int CategoryId){
        //카테고리별로조회
        try{
            List<GetProductRes> getProductRes =productProvider.getProduct(CategoryId);
            return new BaseResponse<>(getProductRes);
        }
        catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }
    //products/Detail/:ProductId
    @ResponseBody
    @GetMapping("/Detail/{ProductId}")
    public BaseResponse<GetProductDetailRes> getProductDetail(@PathVariable("ProductId") int ProductId){
        //상품상세페이지
        try{
            GetProductDetailRes getProductDetailRes =productProvider.getProductDetail(ProductId);
            return new BaseResponse<>(getProductDetailRes);
        }
        catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @Transactional



    }
