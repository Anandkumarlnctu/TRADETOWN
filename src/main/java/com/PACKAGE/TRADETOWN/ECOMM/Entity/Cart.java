package com.PACKAGE.TRADETOWN.ECOMM.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="cart")
public class Cart {

	  public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Cartitems> getItems() {
		return items;
	}

	public void setItems(List<Cartitems> items) {
		this.items = items;
	}

	public Long getId() {
		return id;
	}

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String username; // or a foreign key to User

	    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	    @JsonManagedReference
	    private List<Cartitems> items = new ArrayList<>();

		
}
