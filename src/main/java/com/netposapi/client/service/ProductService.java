package com.netposapi.client.service;

import java.util.List;

import com.netposapi.client.models.Product;
import com.netposapi.client.models.Stock;
import com.netposapi.client.models.request.ProductRequest;
import com.netposapi.client.repository.ProductRepository;
import com.netposapi.client.repository.StockRepository;
import com.netposapi.client.service.impl.ProductServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService implements ProductServiceImpl {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StockRepository stockRepository;

    @Override
    public Product save(ProductRequest productRequest) {
        if (!productRepository.existsByNameAndPersonId(productRequest.getName(), productRequest.getPersonId())) {
            Product product = new Product();
            product.setName(productRequest.getName());
            product.setPersonId(productRequest.getPersonId());
            product.setPrice(productRequest.getPrice());
            product.setStock(productRequest.getStock());
            productRepository.save(product);
            Stock stock = new Stock();
            stock.setPersonId(product.getPersonId());
            stock.setQuantity(product.getStock());
            stockRepository.save(stock);
            return product;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto ja cadastrado", null);
    }

    @Override
    public Product put(Product productRequest) {
        if (productRepository.existsByIdAndPersonId(productRequest.getId(), productRequest.getPersonId())) {
            Product product = productRepository.getOne(productRequest.getId());
            product.setName(productRequest.getName());
            product.setPersonId(productRequest.getPersonId());
            product.setPrice(productRequest.getPrice());
            productRepository.saveAndFlush(product);
            return product;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao existente", null);

    }

    @Override
    public List<Product> list(Integer personId) {
        return productRepository.findByPersonId(personId);
    }

    @Override
    public List<Product> search(Integer id, Integer personId) {
        return productRepository.findByIdAndPersonId(id, personId);
    }

}