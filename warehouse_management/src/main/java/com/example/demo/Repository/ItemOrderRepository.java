package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.bean.ItemOrder;
@Repository
public interface ItemOrderRepository extends JpaRepository<ItemOrder,Integer> {

	@Query("select item from ItemOrder item")
	List<ItemOrder> viewOrders();

}
