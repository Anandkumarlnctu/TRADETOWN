package com.PACKAGE.TRADETOWN.ECOMM.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PACKAGE.TRADETOWN.ECOMM.Entity.Buyer;
import com.PACKAGE.TRADETOWN.ECOMM.Repository.BuyerRepository;

@Service
public class BuyerService {

	@Autowired BuyerRepository buyrepo;
	@Autowired MailService ms;
	public Buyer login(String buyername, String buyerpassword) {
		Buyer user=buyrepo.findByBuyernameAndBuyerpassword(buyername, buyerpassword);
		if(user!=null)
		{
			return user;
		}
		return null;
	}
	public void register(Buyer buyer) {
		buyrepo.save(buyer);
		ms.sendWelcomeEmail(buyer.getBuyername());
		
	}

}
