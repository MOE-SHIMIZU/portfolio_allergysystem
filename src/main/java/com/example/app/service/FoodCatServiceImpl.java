package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.FoodCat;
import com.example.app.mapper.FoodCatMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class FoodCatServiceImpl implements FoodCatService {

	@Autowired
	private FoodCatMapper foodCatMapper;

	@Override
	public List<FoodCat> getFoodCatList() throws Exception {
		return foodCatMapper.selectAll();
	}

	@Override
	public FoodCat getFoodCatById(Integer id) throws Exception {
		return foodCatMapper.selectById(id);
	}

	@Override
	public void deleteFoodCatById(Integer id) throws Exception {
		foodCatMapper.deleteById(id);
	}


	@Override
	public void editFoodCat(FoodCat foodCat) throws Exception {
		foodCatMapper.update(foodCat);
	}

	// 2. 一括挿入用
	@Override
	public void addFoodCatsList(List<FoodCat> foodCats) throws Exception {
		foodCatMapper.insertList(foodCats);
	}

	//	ページネーション
	@Override
	public int getTotalPages(int numPerPage) throws Exception {
		double totalNum = (double) foodCatMapper.count();
		return (int) Math.ceil(totalNum / numPerPage );
	}

	//	ページネーション
	@Override
	public List<FoodCat> getAllergyListByPage(int page, int numPerPage) throws Exception {
		int offset = numPerPage * (page -1);
		return  foodCatMapper.selectLimited(offset, numPerPage) ;
	}

}
