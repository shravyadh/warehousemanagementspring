package com.comakeit.wms.restcontroller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.comakeit.wms.bean.Customer;
import com.comakeit.wms.bean.Item;
import com.comakeit.wms.bean.ItemOrder;
import com.comakeit.wms.bean.Purchase;
import com.comakeit.wms.service.AdminService;

@RestController
public class AdminRest {

	@Autowired
	AdminService adminService;
	
	@GetMapping("/viewItems")
	public List<Item> getItems()
	{
		 return adminService.getItems();
	}
	
	@PostMapping("/addCustomer")
	public Customer addCustomer(@RequestBody Customer customer)
	{
		return adminService.addCustomer(customer);
	}
	
	@GetMapping("/viewCustomers/{customer_code}")
	public List<Customer> getCustomers(@PathVariable("customer_code") int customer_code)
	{
		 return adminService.getCustomers(customer_code);
	}
	
	@DeleteMapping("/deleteCustomer/{customer_name}")
	public void deleteCustomer(@PathVariable("customer_name") String customer_name)
	{
		adminService.deleteCustomer(customer_name);
	}
	
	@DeleteMapping("/deleteItem/{item_name}")
	public void deleteItem(@PathVariable("item_name") String item_name)
	{
		adminService.deleteItem(item_name);
	}
	
	@PostMapping("/purchase")
	public Purchase placeOrder(@RequestBody Purchase purchase)
	{
		return adminService.placeOrder(purchase);
	}
	
	@GetMapping("/viewPurchases/{date}")
	public List<Purchase> viewPurchases(@PathVariable("date") String date)
	{
		 return adminService.viewPurchases(LocalDate.parse(date));
	}
	
	@GetMapping("/viewOrders")
	public List<ItemOrder> viewOrders()
	{
		 return adminService.viewOrders();
	}
	
	@GetMapping("/addStock/{item_name}/{quantity}/{date}")
	public ItemOrder addStock(@PathVariable("item_name") String item_name, @PathVariable("quantity") int quantity, @PathVariable("date") String date)
	{
		return adminService.addStock(item_name,quantity,LocalDate.parse(date));
	}
}
