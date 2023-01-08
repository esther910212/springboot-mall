package com.liuesther.springbootmall.service.impl;

import com.liuesther.springbootmall.dao.ProductDao;
import com.liuesther.springbootmall.dto.ProductQuertParams;
import com.liuesther.springbootmall.dto.ProductRequest;
import com.liuesther.springbootmall.model.Product;
import com.liuesther.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Integer countProduct(ProductQuertParams productQuertParams) {
        return productDao.countProduct(productQuertParams);
    }

    @Override
    public List<Product> getProducts(ProductQuertParams productQuertParams) {//(ProductCategory category, String search)
        return productDao.getProducts(productQuertParams);//(category,search)
    }

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId,productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }
}
