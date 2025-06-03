package com.PACKAGE.TRADETOWN.ECOMM.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;

@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    private String productName;

    private double productPrice;

    private byte[] productImage;

    // Many products can belong to one seller
    @ManyToOne
    @JoinColumn(name = "sellerid", nullable = false)
    private Seller seller;

	public Long getId() {
		return id;
	}

	

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public byte[] getProductImage() {
		return productImage;
	}

	public void setProductImage(byte[] productImage) {
		this.productImage = productImage;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

    // Getters and Setters
}
