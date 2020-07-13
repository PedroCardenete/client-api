package com.netposapi.client.models.request;
import javax.validation.constraints.NotEmpty;

public class ProductRequest {

    @NotEmpty
    private Integer personId;

    @NotEmpty
    private String name;

    @NotEmpty
    private Double price;
    
    @NotEmpty
    private Integer stock;

    public Integer getPersonId() {
        return this.personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return this.stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public ProductRequest(Integer personId, String name, Double price, Integer stock) {
        this.personId = personId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public ProductRequest() {
    }
    
}