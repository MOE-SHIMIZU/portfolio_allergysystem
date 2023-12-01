package com.example.app.service;

import java.util.List;

import com.example.app.domain.Allergy;

public interface AllergyService {

	List<Allergy> getAllergyList() throws Exception;

	Allergy getAllergyById(Integer id) throws Exception;

	void deleteAllergyById(Integer id) throws Exception;

	void editAllergy(Allergy allergy) throws Exception;

	// 1. 一括挿入用
	void addAllergyList(List<Allergy> allergies) throws Exception;

	//	ページネーション
	int getTotalPages(int numPerPage) throws Exception;

	//	ページネーション
	List<Allergy> getAllergyListByPage(int page, int numPerPage) throws Exception;

}
