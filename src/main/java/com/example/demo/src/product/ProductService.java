package com.example.demo.src.product;

import com.example.demo.src.product.model.PostProductReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service

public class ProductService {
    private final ProductDao productDao;
    private final ProductProvider productProvider;

    @Autowired
    public ProductService(ProductDao productDao, ProductProvider productProvider){
        this.productDao=productDao;
        this.productProvider=productProvider;
    }

    @Transactional
    public PostProductReq createProduct(PostProductReq postProductReq)

}


