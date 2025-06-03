package com.PACKAGE.TRADETOWN.ECOMM.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="buyer")
public class Buyer {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long buyerid;
	public long getBuyerid() {
		return buyerid;
	}
	
	public String getBuyername() {
		return buyername;
	}
	public void setBuyername(String buyername) {
		this.buyername = buyername;
	}
	public String getBuyerpassword() {
		return buyerpassword;
	}
	public void setBuyerpassword(String buyerpassword) {
		this.buyerpassword = buyerpassword;
	}
	@Column
	private String buyername;
	@Column
	private String buyerpassword;
	
}
