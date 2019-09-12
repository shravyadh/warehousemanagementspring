package com.comakeit.wms.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comakeit.wms.Service.MerchantService;
import com.comakeit.wms.bean.ItemOrder;

@RestController
public class MerchantRest {

	@Autowired
	MerchantService merchantService;

	@GetMapping("/viewO")
	public List<ItemOrder> viewOrders() {
		return merchantService.viewOrders();
	}

	@PostMapping("/accept/{id}/{stock}/{item_code}")
	public ItemOrder acceptOrder(@PathVariable("id") int id, @PathVariable("stock") int stock,
			@PathVariable("item_code") int item_code) {
		return merchantService.acceptOrder(id, stock, item_code);
	}

	@PostMapping("/cancel/{id}")
	public void cancelOrder(@PathVariable("id") int id) {
		merchantService.cancelOrder(id);
	}
}
