package com.example.app.service;

import java.util.List;

import com.example.app.domain.Order;
import com.example.app.domain.OrderForm;

public interface OrderService {

	List<Order> getOrderList() throws Exception;

	Order getOrderById(Integer id) throws Exception;

	void deleteOrderById(Integer id) throws Exception;

	void addOrder(OrderForm orderForm) throws Exception;

	void editOrder(Order order) throws Exception;

	//	詳細ページ用
	List<Order> getOrderDetailById(Integer id) throws Exception;

	//	ページネーション
	int getTotalPages(int numPerPage) throws Exception;

	//	ページネーション
	List<Order> getOrderListByPage(int page, int numPerPage) throws Exception;

}
