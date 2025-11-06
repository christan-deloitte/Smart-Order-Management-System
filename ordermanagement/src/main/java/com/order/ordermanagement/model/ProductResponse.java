package com.order.ordermanagement.model;


public class ProductResponse {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String category;

    public ProductResponse(Long id, String sku, String name, String description, Double price, Integer quantity, String category) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public Long getId() { return id; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Double getPrice() { return price; }
    public Integer getQuantity() { return quantity; }
    public String getCategory() { return category; }
}

