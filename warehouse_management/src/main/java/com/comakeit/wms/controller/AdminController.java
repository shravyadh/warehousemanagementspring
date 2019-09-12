package com.comakeit.wms.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.comakeit.wms.bean.Customer;
import com.comakeit.wms.bean.Item;
import com.comakeit.wms.bean.ItemOrder;
import com.comakeit.wms.bean.Login;
import com.comakeit.wms.bean.Purchase;
import com.comakeit.wms.constants.Constants;

@Controller
public class AdminController {
	RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(value = "/AdminOperations")
	public ModelAndView doOperations(HttpServletRequest request) {

		String operation = request.getParameter("operation");
		HttpSession session = request.getSession();

		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("adminoperations.jsp");

		if (operation.equals("ViewItems")) {

			ResponseEntity<List<Item>> genericItems = restTemplate.exchange(Constants.url + "viewItems", HttpMethod.GET,
					null, new ParameterizedTypeReference<List<Item>>() {
					});

			session.setAttribute("items", genericItems.getBody());
			modelView.setViewName("adminoperations.jsp?operation=ViewItems");

			return modelView;

		}

		if (operation.equals("add")) {
			String name = request.getParameter("name");
			String phoneno = request.getParameter("phoneno");
			String address = request.getParameter("address");

			Customer customer = new Customer();
			Login login = new Login();

			login = (Login) session.getAttribute("login");

			System.out.println(address);
			customer.setCustomer_name(name);
			customer.setPhone_number(phoneno);
			customer.setAddress(address);
			customer.setLogin(login);

			customer = restTemplate.postForObject(Constants.url + "addCustomer", customer, Customer.class);

			if (customer != null) {
				session.setAttribute("customer", customer);
				modelView.setViewName("register.jsp");
				return modelView;
			} else {
				modelView.setViewName("error.jsp");
				return modelView;
			}

		}

		if (operation.equals("ViewCustomers")) {
			int customer_code = Integer.parseInt(request.getParameter("code"));

			ResponseEntity<List<Customer>> customers = restTemplate.exchange(
					Constants.url + "viewCustomers/" + customer_code, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<Customer>>() {
					});

			if (customers.getBody().size() != 0) {
				session.setAttribute("customers", customers.getBody());
				modelView.setViewName("adminoperations.jsp?operation=viewCustomers");
				return modelView;
			}
			else {
				modelView.setViewName("adminoperations.jsp?operation=add");
				return modelView;
			}
		}
		if (operation.equals("deletecustomer")) {
			String customer_name = request.getParameter("customername");

			restTemplate.delete(Constants.url + "deleteCustomer/" + customer_name);

			modelView.setViewName("success.jsp");
			return modelView;
		}
		if (operation.equals("deleteitem")) {
			String item_name = request.getParameter("itemname");

			restTemplate.delete(Constants.url + "deleteItem/" + item_name);

			modelView.setViewName("success.jsp");
			return modelView;
		}
		if (operation.equals("order")) {
			Purchase purchase = new Purchase();

			double price = 0;
			int stock;
			int id = Integer.parseInt(request.getParameter("id"));
			int customer_code = Integer.parseInt(request.getParameter("code"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			LocalDate date = LocalDate.now();
			
			ResponseEntity<List<Item>> genericItems = restTemplate.exchange(Constants.url + "viewItems", HttpMethod.GET,
					null, new ParameterizedTypeReference<List<Item>>() {
					});
			if(genericItems.getBody().size()!=0)
			for (Item item : genericItems.getBody()) {
				if (item.getItem_code() == id) {
					if (item.getItem_stock() > quantity) {
						price = price + new Constants().getTotal(quantity, item.getItem_price());
						stock = item.getItem_stock()-quantity;
						
						item.setItem_stock(stock);
						
						purchase.setItem(item);
						purchase.setQuantity_purchased(quantity);

						ResponseEntity<List<Customer>> customers = restTemplate.exchange(
								Constants.url + "viewCustomers/" + customer_code, HttpMethod.GET, null,
								new ParameterizedTypeReference<List<Customer>>() {
								});
						for (Customer customer : customers.getBody()) {
							if (customer.getCustomer_code() == customer_code) {
								purchase.setCustomer(customer);
							}
							else {
								modelView.setViewName("adminoperations.jsp?operation=add");
								return modelView;
							}
						}
						purchase.setDate_Of_purchase(date);
						purchase.setTotal(price);

						purchase = restTemplate.postForObject(Constants.url + "purchase", purchase, Purchase.class);
						session.setAttribute("price", price);
					}
					else {
						modelView.setViewName("stock.jsp");
						return modelView;
					}
				}
			}
			if (purchase != null) {
				modelView.setViewName("order.jsp");
				return modelView;
			} else {
				modelView.setViewName("error.jsp");
			}
		}
		if(operation.equals("ViewPurchases"))
		{
			
			ResponseEntity<List<Purchase>> purchases = restTemplate.exchange(
					Constants.url + "viewPurchases/" + request.getParameter("date"), HttpMethod.GET, null,
					new ParameterizedTypeReference<List<Purchase>>() {
					});

			if (purchases != null) {
				session.setAttribute("purchases", purchases.getBody());
				modelView.setViewName("adminoperations.jsp?operation=viewPurchases");
				return modelView;
			}
		}
		if(operation.equals("itemorders"))
		{
			ResponseEntity<List<ItemOrder>> itemorders = restTemplate.exchange(
					Constants.url + "viewOrders", HttpMethod.GET, null,
					new ParameterizedTypeReference<List<ItemOrder>>() {
					});

			if (itemorders.getBody() != null) {
				session.setAttribute("itemorders", itemorders.getBody());
				modelView.setViewName("adminoperations.jsp?operation=itemorders");
				return modelView;
			}
			else {
				System.out.println("Error! Please place an order. There are no orders");
			}
		}
		if(operation.equals("stock"))
		{
			String item_name = request.getParameter("item");
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			LocalDate date = LocalDate.now();
			
			ResponseEntity<ItemOrder> genericItem = restTemplate.exchange(Constants.url+"addStock/" + item_name +"/" + quantity +"/"+date.toString(), HttpMethod.GET,
					null, new ParameterizedTypeReference<ItemOrder>() {
					});
			ItemOrder itemOrder = genericItem.getBody();
			if(itemOrder!=null)
			{
				modelView.setViewName("success.jsp");
				return modelView;
			}
			else {
				modelView.setViewName("error.jsp");
				return modelView;
			}
		}
		return modelView;
	}
}


