package com.netposapi.client.service.impl;

import java.util.List;
import java.util.Optional;

import com.netposapi.client.models.Product;
import com.netposapi.client.models.request.ProductRequest;

public interface ProductServiceImpl {

    public abstract Product save(ProductRequest productRequest);

    public abstract Product put(Product productRequest);

    public abstract Optional<Product> search(Integer id, Integer personId);

    public abstract List<Product> list(Integer personId);

}