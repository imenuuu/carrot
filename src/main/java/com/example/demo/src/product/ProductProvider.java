package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.src.product.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class ProductProvider {
    private final ProductDao productDao;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProductProvider(ProductDao productDao){
        this.productDao=productDao;
    }


    public List<GetProductRes> getProducts() throws BaseException{
        try{
            List<GetProductRes> getProductRes = productDao.getProducts();
            return getProductRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }


    public List<GetProductRes> getProduct(int CategoryId) throws BaseException{
        try {
            List<GetProductRes> getProductRes=productDao.getProduct(CategoryId);
            return getProductRes;
        }
        catch (Exception exception){
            throw new BaseException((DATABASE_ERROR));
        }

    }

    public GetProductDetailRes getProductDetail(int ProductId) throws BaseException{
        try {
            GetProductDetailRes getProductDetailRes=productDao.getProductDetail(ProductId);
            return getProductDetailRes;
        }
        catch (Exception exception){
            throw new BaseException((DATABASE_ERROR));
        }

    }

}
