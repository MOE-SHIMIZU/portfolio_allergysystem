package com.example.app.service;

import java.util.List;

import com.example.app.domain.Ingredient;
import com.example.app.domain.IngredientsForm;

public interface IngredientService {

	List<Ingredient> getIngredientList() throws Exception;

	Ingredient getIngredientById(Integer id) throws Exception;

	void deleteIngredientById(Integer id) throws Exception;

	void addIngredient(IngredientsForm IngredientsForm) throws Exception;

	void editIngredient(Ingredient ingredient) throws Exception;

	//	selectフォーム用のList
	List<Ingredient> getIngredientFoodCat() throws Exception;
	
	//	ページネーション
	int getTotalPages(int numPerPage) throws Exception;

	//	ページネーション
	List<Ingredient> getIngredientListByPage(int page, int numPerPage) throws Exception;


}
