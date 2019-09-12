package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.ItemOrderRepository;
import com.example.demo.Repository.ItemRepository;
import com.example.demo.bean.Item;
import com.example.demo.bean.ItemOrder;

@Service
public class MerchantService {

	@Autowired
	ItemOrderRepository itemOrderRepository;

	@Autowired
	ItemRepository itemRepository;

	public List<ItemOrder> viewOrders() {
		return itemOrderRepository.viewOrders();
	}

	public ItemOrder acceptOrder(int id, int stock, int item_code) {

		Item item = new Item();
		item = itemRepository.findById(item_code).get();
		item.setItem_stock(item.getItem_stock() + stock);
		itemRepository.save(item);

		ItemOrder itemOrder = itemOrderRepository.findById(id).get();
		itemOrder.setStatus("Accept");
		itemOrderRepository.save(itemOrder);
		
		return itemOrder;
	}

	public void cancelOrder(int id) {
		ItemOrder itemOrder = itemOrderRepository.findById(id).get();
		itemOrder.setStatus("Cancel");
		itemOrderRepository.save(itemOrder);

	}
}
