package com.example.app.service;

import java.util.List;

import com.example.app.domain.IngredientsAllergies;

public interface IngredientsAllergiesService {

	List<IngredientsAllergies> getIngredientsAllergiesById(Integer id) throws Exception;

	List<IngredientsAllergies> getAllergiesByIngredientId(Integer ingredientId) throws Exception;
	
	void edit(IngredientsAllergies ingredientsAllergies) throws Exception;

	void deleteIngredientsAllergiesById(Integer id) throws Exception;

}
