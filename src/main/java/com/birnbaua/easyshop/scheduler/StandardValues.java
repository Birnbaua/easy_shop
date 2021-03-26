package com.birnbaua.easyshop.scheduler;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.birnbaua.easyshop.auth.CustomUserDetailsService;
import com.birnbaua.easyshop.auth.User;
import com.birnbaua.easyshop.auth.UserRole;
import com.birnbaua.easyshop.controller.ItemControllerRest;
import com.birnbaua.easyshop.controller.ShopControllerRest;
import com.birnbaua.easyshop.controller.ShopTableControllerRest;
import com.birnbaua.easyshop.service.ItemService;
import com.birnbaua.easyshop.service.ShopService;
import com.birnbaua.easyshop.service.ShopTableService;
import com.birnbaua.easyshop.shop.Shop;
import com.birnbaua.easyshop.shop.ShopTable;
import com.birnbaua.easyshop.shop.item.Item;

@EnableScheduling
@Configuration
public class StandardValues {
	
	@Autowired
	private CustomUserDetailsService us;
	
	@Autowired
	private ShopService ss;
	
	@Autowired
	private ItemService is;
	
	@Autowired
	private ShopTableService sts;
	
	@Scheduled(initialDelay=1000, fixedDelay = 365*24*60*60*1000)
	public void createAdminAcc() {
		if(us.findByUsername("admin") == null) {
			User user = new User();
			user.setUsername("admin");
			user.setPassword("password");
			user.getRoles().add(UserRole.ADMIN);
			us.initSave(user);
		}
	}
	
	@Scheduled(initialDelay = 1000, fixedDelay = 365*24*60*60*1000)
	public void createTestShop() {
		Shop shop = new Shop();
		shop.setName("Test Shop");
		shop.setTitle("Your Shoptitle");
		shop.setOwner(new User("admin"));
		ss.save(shop);
		
		Item item0 = new Item("Bier 0.33");
		Item item1 = new Item("Bier 0.55");
		Item item2 = new Item("Spritzer 0.33");
		Item item3 = new Item("Leberkässemmerl");
		item0.setPrice(2.5);
		item1.setPrice(3.5);
		item2.setPrice(2d);
		item3.setPrice(2d);
		item0.setMaxAmount(6);
		item1.setMaxAmount(3);
		item2.setMaxAmount(5);
		item3.setMaxAmount(4);
		item0.setShop(shop);
		item1.setShop(shop);
		item2.setShop(shop);
		item3.setShop(shop);
		item0.setIsAvaliable(true);
		item1.setIsAvaliable(true);
		item2.setIsAvaliable(true);
		item3.setIsAvaliable(true);
		is.save(item0);
		is.save(item1);
		is.save(item2);
		is.save(item3);
		
		ShopTable table0 = new ShopTable();
		ShopTable table1 = new ShopTable();
		ShopTable table2 = new ShopTable();
		ShopTable table3 = new ShopTable();
		table0.setNr(0);
		table1.setNr(1);
		table2.setNr(2);
		table3.setNr(69);
		table0.setName("Bar");
		table1.setName("Stehtisch 1");
		table2.setName("Stehtisch 2");
		table3.setName("Erotik Tisch");
		table0.setShop(shop);
		table1.setShop(shop);
		table2.setShop(shop);
		table3.setShop(shop);
		sts.save(table0);
		sts.save(table1);
		sts.save(table2);
		sts.save(table3);
		
	}
	
}
