package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Allergy;

public interface AllergyMapper {

	List<Allergy> selectAll() throws Exception;

	Allergy selectById(Integer id) throws Exception;

	void deleteById(Integer id) throws Exception;

	void update(Allergy allergy) throws Exception;

	// 1. 一括挿入用のメソッドを定義
	void insertList(List<Allergy> allergies) throws Exception;

	//	ページネーション
	Long count() throws Exception;

	//	ページネーション
	List<Allergy> selectLimited(@Param("offset") int offset, @Param("numPerPage") int numPerPage) throws Exception;
	
}
