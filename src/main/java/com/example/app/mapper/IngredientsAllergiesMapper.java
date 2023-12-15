package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.app.domain.IngredientsAllergies;

public interface IngredientsAllergiesMapper {

	List<IngredientsAllergies> selectById(Integer id) throws Exception;

	List<IngredientsAllergies> selectByingredientId(Integer ingredientId) throws Exception;

	void insert(@Param("ingredientId") Integer ingredientId, @Param("allergyId") Integer allergyId) throws Exception;

	void update(IngredientsAllergies ingredientsAllergies) throws Exception;

	void deleteById(Integer id) throws Exception;

}
