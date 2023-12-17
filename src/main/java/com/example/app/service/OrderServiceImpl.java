package com.example.app.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.app.domain.Detail;
import com.example.app.domain.Order;
import com.example.app.mapper.DetailMapper;
import com.example.app.mapper.OrderMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private DetailMapper detailMapper;

	@Override
	public List<Order> getOrderList() throws Exception {
		return orderMapper.selectAll();
	}

	@Override
	public Order getOrderById(Integer id) throws Exception {
		return orderMapper.selectById(id);
	}

	@Override
	public void deleteOrderById(Integer id) throws Exception {
		orderMapper.deleteById(id);
	}

	@Override
	public void addOrder(Order order) throws Exception {

		Order newOrder = new Order();
		newOrder.setName(order.getName());
		newOrder.setKana(order.getKana());
		newOrder.setPrice(order.getPrice());
		newOrder.setNote(order.getNote());

		MultipartFile upfile = order.getUpfile();
		if (!upfile.isEmpty()) {
			String img = upfile.getOriginalFilename();
			newOrder.setImg(img);
			File dest = new File("/Users/zoomohe/Desktop/gallery/"+ img);
			upfile.transferTo(dest);
		}

		orderMapper.insert(newOrder);

		for (Detail ingredientId : order.getIngredientIdList()) {

			Detail detail = new Detail();
			detail.setOrdersId(newOrder.getId());
			detail.setIngredientId(ingredientId.getId());

			detailMapper.insert(newOrder.getId(), ingredientId.getId());

		}

	}

	@Override
	public void editOrder(Order order) throws Exception {
		orderMapper.update(order);
	}

	//	詳細ページ用
	@Override
	public List<Order> getOrderDetailById(Integer id) throws Exception {
		return orderMapper.selectOrderDetailById(id);
	}

	//	ページネーション
	@Override
	public int getTotalPages(int numPerPage) throws Exception {
		double totalNum = (double) orderMapper.count();
		return (int) Math.ceil(totalNum / numPerPage);
	}

	//	ページネーション
	@Override
	public List<Order> getOrderListByPage(int page, int numPerPage) throws Exception {
		int offset = numPerPage * ( page - 1);
		return orderMapper.selectLimited(offset, numPerPage);
	}

}
