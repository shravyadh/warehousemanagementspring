package com.example.demo.Repository;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.bean.Customer;
import com.example.demo.bean.Item;
import com.example.demo.bean.ItemOrder;
import com.example.demo.bean.Purchase;

public interface AdminRepositoryInterface {

	List<Item> getItems();
	Customer addCustomer(Customer customer);
	List<Customer> getCustomers(int customer_code);
	void deleteCustomer(String customer_name);
	void deleteItem(String item_name);
	Purchase placeOrder(Purchase purchase);
	List<Purchase> viewPurchases(LocalDate date);
	List<ItemOrder> viewOrders();
}
