package com.product.productmanagement.Model;

public class ProductResponse {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private Double price;
    private Double discount;
    private Integer quantity;
    private String category;

    public ProductResponse(Long id, String sku, String name, String description, Double price, Double discount, Integer quantity, String category) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
        this.category = category;
    }

    public Long getId() { return id; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Double getPrice() { return price; }
    public Double getDiscount() { return discount; }
    public Integer getQuantity() { return quantity; }
    public String getCategory() { return category; }
}
