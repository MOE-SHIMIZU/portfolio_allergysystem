package com.example.app.mapper;

import org.apache.ibatis.annotations.Param;

public interface DetailMapper {
	
	void insert(@Param("ordersId")Integer ordersId, @Param("ingredientId")Integer ingredientId)throws Exception;
	
}
