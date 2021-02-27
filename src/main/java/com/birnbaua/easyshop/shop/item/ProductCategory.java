package com.birnbaua.easyshop.shop.item;

public enum ProductCategory {
	//Drinks
	BEER(ProductFamily.DRINK),
	WINE(ProductFamily.DRINK),
	LIQUOR(ProductFamily.DRINK),
	COCKTAIL(ProductFamily.DRINK),
	MIXED_DRINK(ProductFamily.DRINK),
	SOFT_DRINK(ProductFamily.DRINK),
	JUICE(ProductFamily.DRINK),
	WATER(ProductFamily.DRINK),
	
	//food
	FRUIT(ProductFamily.FOOD),
	VEGETABLES(ProductFamily.FOOD),
	MEAT(ProductFamily.FOOD),
	FISH(ProductFamily.FOOD),
	SEAFOOD(ProductFamily.FOOD),
	EGG(ProductFamily.FOOD),
	DAIRY(ProductFamily.FOOD),
	BREAD(ProductFamily.FOOD),
	BAKING_GOODS(ProductFamily.FOOD),
	BREAKFAST(ProductFamily.FOOD),
	DESSERT(ProductFamily.FOOD),
	SNACKS(ProductFamily.FOOD),
	CANDY(ProductFamily.FOOD),
	SPICES(ProductFamily.FOOD),
	
	//other stuff
	HARDWARE(ProductFamily.NON_CONSUMABLE),
	HYGIENE(ProductFamily.NON_CONSUMABLE),
	CLEANING(ProductFamily.NON_CONSUMABLE),
	FUEL(ProductFamily.NON_CONSUMABLE),
	BUILDING_MATERIAL(ProductFamily.NON_CONSUMABLE),
	PAPER(ProductFamily.NON_CONSUMABLE),
	PLASTIC(ProductFamily.NON_CONSUMABLE),
	MAGAZINES(ProductFamily.NON_CONSUMABLE),
	
	//all other stuff
	OTHER(null);
	
	private ProductFamily family;
	public ProductFamily getFamily() {return family;}
	private ProductCategory(ProductFamily family) {this.family = family;}
}
