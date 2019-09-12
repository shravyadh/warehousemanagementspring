package com.comakeit.wms.constants;





public class Constants {
	public Constants() {
		
	}
	
	public static String url="http://localhost:8181/";
	public double getTotal(int quantity, double price) {
		if (quantity < 10) {
			return price * quantity;
		} else if (quantity >= 10 && quantity <= 100) {
			return price * 0.9 * quantity;
		} else {
			return price * 0.8 * quantity;
		}
	}
}
