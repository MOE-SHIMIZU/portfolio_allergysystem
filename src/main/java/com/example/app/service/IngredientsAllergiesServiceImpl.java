package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.IngredientsAllergies;
import com.example.app.mapper.IngredientsAllergiesMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class IngredientsAllergiesServiceImpl implements IngredientsAllergiesService {

	@Autowired
	private IngredientsAllergiesMapper ingAgMapper;
	
	@Override
	public List<IngredientsAllergies> getIngredientsAllergiesById(Integer id) throws Exception {
		return ingAgMapper.selectById(id);
	}
	
	@Override
	public List<IngredientsAllergies> getAllergiesByIngredientId(Integer ingredientId) throws Exception {
		return ingAgMapper.selectByingredientId(ingredientId);
	}

	@Override
	public void edit(IngredientsAllergies ingredientsAllergies) throws Exception {
		ingAgMapper.update(ingredientsAllergies);
		
	}

	@Override
	public void deleteIngredientsAllergiesById(Integer id) throws Exception {
		ingAgMapper.deleteById(id);
		
	}



}
