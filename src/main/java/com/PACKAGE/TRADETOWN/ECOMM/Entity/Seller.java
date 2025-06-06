package com.PACKAGE.TRADETOWN.ECOMM.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="seller")
public class Seller {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long sellerid;
	
	@Column
	private String storename;
	@Column
	private String Storedesc;
	@Column
	private String sellername;
	@Column
	private String sellerpassword;
	
	  
	public String getSellername() {
		return sellername;
	}

	public void setSellername(String sellername) {
		this.sellername = sellername;
	}

	public String getSellerpassword() {
		return sellerpassword;
	}

	public void setSellerpassword(String sellerpassword) {
		this.sellerpassword = sellerpassword;
	}
	
	@Column
    private byte[] storeimage;
	@Column
	private String imagetype;
	public long getSellerid() {
		return sellerid;
	}
	
	public String getStorename() {
		return storename;
	}
	public void setStorename(String storename) {
		this.storename = storename;
	}
	public String getStoredesc() {
		return Storedesc;
	}
	public void setStoredesc(String storedesc) {
		Storedesc = storedesc;
	}
	public byte[] getStoreimage() {
		return storeimage;
	}
	public void setStoreimage(byte[] storeimage) {
		this.storeimage = storeimage;
	}
	public String getImagetype() {
		return imagetype;
	}
	public void setImagetype(String imagetype) {
		this.imagetype = imagetype;
	}
	
}

