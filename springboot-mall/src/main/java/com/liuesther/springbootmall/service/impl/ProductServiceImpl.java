package com.liuesther.springbootmall.service.impl;

import com.liuesther.springbootmall.dao.ProductDao;
import com.liuesther.springbootmall.model.Product;
import com.liuesther.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
