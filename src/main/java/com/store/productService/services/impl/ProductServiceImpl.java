package com.store.productService.services.impl;

import com.store.productService.entitiies.Product;
import com.store.productService.exception.ProductServiceCustomException;
import com.store.productService.model.ProductRequest;
import com.store.productService.model.ProductResponse;
import com.store.productService.repository.ProductRepository;
import com.store.productService.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Long createProduct(ProductRequest productRequest) {
        log.info("craeting product .. ");
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
        productRepository.save(product);
        log.info("product created");
        return product.getId();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        log.info("getting product by id {}", productId);
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new ProductServiceCustomException("product with id not found", "PRODUCT_NOT_FOUND"));
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);
        return productResponse;


    }

    @Override
    public void reduceQuantity(Long productId, long quantity) {
        log.info("reducing quantity {} for product id {}" , quantity, productId);
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new ProductServiceCustomException("product with id not found", "PRODUCT_NOT_FOUND"));
        if (product.getQuantity() < quantity) {
            throw new ProductServiceCustomException("Stock does not have enough quantity", "Not supported quantity");
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("quantity reduced successfully");
    }
}