package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Order;


public interface OrderMapper {
	
	List<Order> selectAll() throws Exception;
	
	Order selectById(Integer id) throws Exception;
	
	void deleteById(Integer id) throws Exception;
	
	void insert(Order order) throws Exception;
	
	void update(Order order) throws Exception;
	
	//	詳細ページ用のリスト
	List<Order> selectOrderDetailById(Integer id)throws Exception;
	
	// ページネーション
	Long count() throws Exception;

	// ページネーション
	List<Order> selectLimited(@Param("offset") int offset, @Param("numPerPage") int numPerPage) throws Exception;


}
