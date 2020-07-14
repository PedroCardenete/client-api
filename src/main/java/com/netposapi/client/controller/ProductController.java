package com.netposapi.client.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.netposapi.client.models.Person;
import com.netposapi.client.models.Product;
import com.netposapi.client.models.request.ProductRequest;
import com.netposapi.client.models.request.ResponseCustomized;
import com.netposapi.client.service.PersonService;
import com.netposapi.client.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

  @Autowired
  PersonService personService;

  @Autowired
  ProductService productService;

  @ApiOperation(value = "Retorna um produto especifico do Person logado")
  @GetMapping(value = "/{id}")
  public ResponseEntity<Object> find(@PathVariable int id) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Optional<Person> person = personService.getPersonEmail(authentication.getName());
    Optional<Product> product = productService.search(person.get().getId(), id);
    return product.isPresent() ? ResponseEntity.ok().body(ResponseCustomized.response("Success", product))
        : ResponseEntity.badRequest().body("Não existe nenhum produto cadastrado");
  }

  @ApiOperation(value = "Registrar Produto")
  @PostMapping(value = "/register")
  public ResponseEntity<Object> register(@Valid  @RequestBody ProductRequest productRequest) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Optional<Person> person = personService.getPersonEmail(authentication.getName());
    productRequest.setPersonId(person.get().getId());
    Product product = productService.save(productRequest);
    return ResponseEntity.ok().body(ResponseCustomized.response("Success", product));
  }

  @ApiOperation(value = "Atualizar Produto")
  @PutMapping(value = "/put")
  public ResponseEntity<Object> put(@Valid @RequestBody Product productRequest) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Optional<Person> person = personService.getPersonEmail(authentication.getName());
    productRequest.setPersonId(person.get().getId());
    Product product = productService.put(productRequest);
    return ResponseEntity.ok().body(ResponseCustomized.response("Success", product));
  }

  @ApiOperation(value = "Listar Produto")
  @GetMapping(value = "/list")
  public ResponseEntity<Object> list() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Optional<Person> person = personService.getPersonEmail(authentication.getName());
    List<Product> product = productService.list(person.get().getId());
    return product.size() > 0 ? ResponseEntity.ok().body(ResponseCustomized.response("Success", product))
        : ResponseEntity.badRequest().body("Nao existe nenhum produto cadastrado");
  }

}