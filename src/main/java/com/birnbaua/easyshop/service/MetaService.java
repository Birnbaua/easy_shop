package com.birnbaua.easyshop.service;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.birnbaua.easyshop.shop.Shop;
import com.birnbaua.easyshop.shop.ShopTable;
import com.birnbaua.easyshop.shop.item.Item;
import com.birnbaua.easyshop.shop.order.Order;

public class MetaService {
	
	private static ShopService SHOP_SERVICE;
	private static ShopTableService SHOP_TABLE_SERVICE;
	private static ItemService ITEM_SERVICE;
	private static OrderService ORDER_SERVICE;
	
	private static Map<Class<?>,JpaService<?,?>> SERVICES = new TreeMap<>();
	
	@Autowired
	public static void setSHOP_SERVICE(ShopService shopService) {SHOP_SERVICE = shopService;SERVICES.put(Shop.class, shopService);}
	public static ShopService getSHOP_SERVICE() {return SHOP_SERVICE;}
	
	@Autowired
	public static void setSHOP_TABLE_SERVICE(ShopTableService shopTableService) {SHOP_TABLE_SERVICE = shopTableService;SERVICES.put(ShopTable.class, shopTableService);}
	public static ShopTableService getSHOP_TABLE_SERVICE() {return SHOP_TABLE_SERVICE;}
	
	@Autowired
	public static void setITEM_SERVICE(ItemService itemService) {ITEM_SERVICE = itemService;SERVICES.put(Item.class, itemService);}
	public static ItemService getITEM_SERVICE() {return ITEM_SERVICE;}
	
	@Autowired
	public static void setORDER_SERVICE(OrderService orderService) {ORDER_SERVICE = orderService;SERVICES.put(Order.class, orderService);}
	public static OrderService getORDER_SERVICE() {return ORDER_SERVICE;}
	
	
	@SuppressWarnings("unchecked")
	public static <T, K> JpaService<T,K> get(Class<T> c0,Class<K> c1) {
		return (JpaService<T, K>) SERVICES.get(c0);
	}
}
