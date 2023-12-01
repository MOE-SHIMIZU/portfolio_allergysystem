package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.app.domain.FoodCat;

public interface FoodCatMapper {

	List<FoodCat> selectAll() throws Exception;

	FoodCat selectById(Integer id) throws Exception;

	void deleteById(Integer id) throws Exception;
	
	void update(FoodCat foodCat) throws Exception;

	// 1. 一括挿入用のメソッドを定義
	void insertList(List<FoodCat> foodCats) throws Exception;

	// ページネーション
	Long count() throws Exception;

	// ページネーション
	List<FoodCat> selectLimited(@Param("offset") int offset, @Param("numPerPage") int numPerPage) throws Exception;

}
