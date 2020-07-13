package com.netposapi.client.repository;

import java.util.List;

import com.netposapi.client.models.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    boolean existsByNameAndPersonId(String name, Integer PersonId);

    boolean existsByIdAndPersonId(Integer id, Integer personId);

    List<Product> findByPersonId(Integer personId);

    List<Product> findByIdAndPersonId(Integer id, Integer personId);
}