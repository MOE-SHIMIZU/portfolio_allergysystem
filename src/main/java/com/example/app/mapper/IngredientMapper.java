package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Ingredient;

public interface IngredientMapper {

	List<Ingredient> selectAll() throws Exception;

	Ingredient selectById(Integer id) throws Exception;

	void deleteById(Integer id) throws Exception;

	void insert(Ingredient ingredient) throws Exception;

	void update(Ingredient ingredient) throws Exception;

	//	selectフォーム用のList
	List<Ingredient> selectIngredientFoodCat() throws Exception;
	
	// ページネーション
	Long count() throws Exception;

	// ページネーション
	List<Ingredient> selectLimited(@Param("offset") int offset, @Param("numPerPage") int numPerPage) throws Exception;


}
