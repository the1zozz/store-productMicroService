package com.store.productService.services;

import com.store.productService.model.ProductRequest;
import com.store.productService.model.ProductResponse;

public interface ProductService {
    Long createProduct(ProductRequest productRequest);

    ProductResponse getProductById(Long productId);

    void reduceQuantity(Long productId, long quantity);
}
