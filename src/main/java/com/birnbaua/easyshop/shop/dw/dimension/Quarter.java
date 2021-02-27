package com.birnbaua.easyshop.shop.dw.dimension;

public enum Quarter {
	Q1,Q2,Q3,Q4;
	
	public static Quarter of(Byte month) {
		switch(month) {
		case 1:
		case 2:
		case 3:
			return Q1;
		case 4:
		case 5:
		case 6:
			return Q2;
		case 7:
		case 8:
		case 9:
			return Q3;
		case 10:
		case 11:
		case 12:
			return Q4;
		default:
			return null;
		}
	}
}
