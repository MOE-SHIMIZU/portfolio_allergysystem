package com.example.app.service;

import java.util.List;

import com.example.app.domain.FoodCat;

public interface FoodCatService {

	List<FoodCat> getFoodCatList() throws Exception;

	FoodCat getFoodCatById(Integer id) throws Exception;

	void deleteFoodCatById(Integer id) throws Exception;
	
	void editFoodCat(FoodCat foodCat) throws Exception;

	// 1. 一括挿入用
	void addFoodCatsList(List<FoodCat> foodCats) throws Exception;
	
	//	ページネーション
	int getTotalPages(int numPerPage) throws Exception;

	//	ページネーション
	List<FoodCat> getAllergyListByPage(int page, int numPerPage) throws Exception;


}
