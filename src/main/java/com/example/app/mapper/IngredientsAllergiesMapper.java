package com.example.app.mapper;

import org.apache.ibatis.annotations.Param;

public interface IngredientsAllergiesMapper {

	void insert(@Param("ingredientId")Integer ingredientId, @Param("allergyId") Integer allergyId) throws Exception;
	
}
