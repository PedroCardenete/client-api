package com.netposapi.client.repository;

import java.math.BigDecimal;

import com.netposapi.client.models.Stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    @Query(nativeQuery = true, value = "SELECT sum(s.quantity) FROM stock s WHERE s.person_id =:personId AND s.product_id =:productId")
        BigDecimal sumQuantityPerType(@Param("personId") Integer personId,@Param("productId") Integer productId);

}