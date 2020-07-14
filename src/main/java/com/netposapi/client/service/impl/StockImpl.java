package com.netposapi.client.service.impl;

import java.util.Optional;

import com.netposapi.client.models.Product;

public interface StockImpl {
    public abstract Integer sumStock (int personId, int productId);
    public abstract  Optional<Product> getProductStock (int personId, int productId);
    public abstract Optional<Product> movementStock(int personId, int productId, int quantity);
    
}