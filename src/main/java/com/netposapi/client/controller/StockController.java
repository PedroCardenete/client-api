package com.netposapi.client.controller;

import java.util.Optional;

import javax.validation.constraints.Max;

import com.netposapi.client.models.Person;
import com.netposapi.client.models.Product;
import com.netposapi.client.models.request.ResponseCustomized;
import com.netposapi.client.service.PersonService;
import com.netposapi.client.service.StockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    PersonService personService;

    @Autowired
    StockService stockService;

    
    @ApiOperation(value = "Consultar quantidade de produto no estoque")
    @GetMapping(value = "/{productId}")
    public ResponseEntity<Object> find(@PathVariable int productId) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      Optional<Person> person = personService.getPersonEmail(authentication.getName());
      Optional<Product> productStock = stockService.getProductStock(person.get().getId(),productId);
      return productStock.isPresent() ? ResponseEntity.ok().body(ResponseCustomized.response("Success", productStock))
          : ResponseEntity.badRequest().body("Problemas ao gerar consultar estoque");
    }
  
        
    @ApiOperation(value = "Movimentar o estoque valor positvo ou negativo")
    @PostMapping(value = "/movement/{id}/quantity/{quantity}")
    public ResponseEntity<Object> find( @PathVariable ("id") Integer productId, @PathVariable ("quantity")  @Max(1000)Integer quantity) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      Optional<Person> person = personService.getPersonEmail(authentication.getName());
      Optional<Product> productStock = stockService.movementStock(person.get().getId(), productId, quantity);
      return productStock.isPresent() ? ResponseEntity.ok().body(ResponseCustomized.response("Success", productStock))
          : ResponseEntity.badRequest().body("Problemas ao gerar consultar estoque");
    }
  
}