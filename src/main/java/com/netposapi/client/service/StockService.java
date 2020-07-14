package com.netposapi.client.service;

import java.util.Optional;

import com.netposapi.client.models.Product;
import com.netposapi.client.models.Stock;
import com.netposapi.client.repository.ProductRepository;
import com.netposapi.client.repository.StockRepository;
import com.netposapi.client.service.impl.StockImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StockService implements StockImpl {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StockRepository stockRepository;

    @Override
    public Optional<Product> getProductStock(int personId, int productId) {
        if (productRepository.existsByIdAndPersonId(productId, personId)) {
            Optional<Product> product = productRepository.findByIdAndPersonId(productId, personId);
            product.get().setStock(sumStock(personId, productId));
            productRepository.save(product.get());
            return product;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao existe", null);

    }

    @Override
    public Integer sumStock(int personId, int productId) {
        if (productRepository.existsByIdAndPersonId(productId, personId)) {
            return stockRepository.sumQuantityPerType(personId, productId).intValue();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao existe", null);

    }

    @Override
    public Optional<Product> movementStock(int personId, int productId, int quantity){
    if(productRepository.existsByIdAndPersonId(productId, personId)){
        if (sumStock(personId, productId) + quantity >= 0 && sumStock(personId, productId) + quantity <= 1000 ){
            Stock stock = new Stock();
            stock.setPersonId(personId);
            stock.setProductId(productId);
            stock.setQuantity(quantity);
            stockRepository.save(stock);
            Optional<Product> product = productRepository.findByIdAndPersonId(productId, personId);
            product.get().setStock(sumStock(personId, productId));
            return product;
        }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O estoque nao pode ser negativo ou maior que 1000 | Estoque atual: "
            +sumStock(personId, productId)+" | Quantidade movimentada: "
            +quantity+"| Valor nao aceito: "
            + (sumStock(personId, productId) + quantity), null);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao existe", null);
    }

}