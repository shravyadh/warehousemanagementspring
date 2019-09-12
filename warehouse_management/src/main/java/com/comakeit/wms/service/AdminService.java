package com.comakeit.wms.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comakeit.wms.bean.Customer;
import com.comakeit.wms.bean.Item;
import com.comakeit.wms.bean.ItemOrder;
import com.comakeit.wms.bean.Login;
import com.comakeit.wms.bean.Purchase;
import com.comakeit.wms.repository.CustomerRepository;
import com.comakeit.wms.repository.ItemOrderRepository;
import com.comakeit.wms.repository.ItemRepository;
import com.comakeit.wms.repository.PurchaseRepository;

@Service
public class AdminService implements IAdmin{
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	PurchaseRepository purchaseRepository;
	
	@Autowired
	ItemOrderRepository itemOrderRepository;

	@Override
	public List<Item> getItems() {
		return itemRepository.getItems();
	}
	
	@Override
	public Customer addCustomer(Customer customer) {
		return customerRepository.save(customer);
	}
	
	@Override
	public List<Customer> getCustomers(int customer_code) {
		return customerRepository.getCustomers(customer_code);
	}
	
	@Override
	public void deleteCustomer(String customer_name) {
		Customer customer = customerRepository.deleteCustomer(customer_name);
		customer.setLogin(new Login());
		customerRepository.delete(customer);
	}
	
	@Override
	public void deleteItem(String item_name) {
		Item item = itemRepository.selectItemByName(item_name);
		itemRepository.delete(item);
	}
	
	@Override
	public Purchase placeOrder(Purchase purchase)
	{
		Item item=itemRepository.findById(purchase.getItem().getItem_code()).get();
		item.setItem_stock(purchase.getItem().getItem_stock());
		itemRepository.save(item);
		return purchaseRepository.saveAndFlush(purchase);
	}
	
	@Override
	public List<Purchase> viewPurchases(LocalDate date) {
		return purchaseRepository.viewPurchases(date);
	}

	@Override
	public List<ItemOrder> viewOrders() {
		return itemOrderRepository.viewOrders();
	}

	public ItemOrder addStock(String item_name, int quantity, LocalDate date) {
		
		Item item = new Item();
		item = itemRepository.selectItemByName(item_name);
		
		ItemOrder itemOrder = new ItemOrder();
		itemOrder.setDate(date);
		itemOrder.setItem(item);
		itemOrder.setMerchant_name(item.getMerchant().getMerchant_name());
		itemOrder.setQuantity(quantity);
		itemOrder.setStatus("pending");
		itemOrderRepository.save(itemOrder);
		
		return itemOrder;
		
	}

}
