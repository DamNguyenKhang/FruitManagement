/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author DELL
 */
public class Product {
    private int productID;
    private String productName;
    private String description;
    private BigDecimal price;
    private String imageURL;
    private LocalDateTime importDate;

    public Product(int productID, String productName, String description, BigDecimal price, String imageURL, LocalDateTime importDate) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
        this.importDate = importDate;
    }

    public Product(String productName, String description, BigDecimal price, LocalDateTime importDate) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.importDate = importDate;
    }
    

 
    public Product() {
    }

    public LocalDateTime getImportDate() {
        return importDate;
    }

    public void setImportDate(LocalDateTime importDate) {
        this.importDate = importDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
