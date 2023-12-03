package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Allergy;
import com.example.app.domain.Ingredient;
import com.example.app.domain.IngredientsAllergies;
import com.example.app.domain.IngredientsForm;
import com.example.app.mapper.IngredientMapper;
import com.example.app.mapper.IngredientsAllergiesMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class IngredientServiceImpl implements IngredientService {

	@Autowired
	private IngredientMapper ingredientMapper;

	@Autowired
	private IngredientsAllergiesMapper ingAgMapper;

	@Override
	public List<Ingredient> getIngredientList() throws Exception {
		return ingredientMapper.selectAll();
	}

	@Override
	public Ingredient getIngredientById(Integer id) throws Exception {
		return ingredientMapper.selectById(id);
	}

	@Override
	public void deleteIngredientById(Integer id) throws Exception {
		ingredientMapper.deleteById(id);
	}

	@Override
	public void addIngredient(IngredientsForm ingredientsForm) throws Exception {
		List<Ingredient> ingeIngredients = ingredientsForm.getIngredients();

		for (Ingredient inge : ingeIngredients) {
			Ingredient ingredient = new Ingredient();
			String name = inge.getName();
			if (!name.equals("")) {
				String kana = inge.getKana();
				Integer foodCatId = inge.getFoodCatId();

				ingredient.setName(name);
				ingredient.setKana(kana);
				ingredient.setFoodCatId(foodCatId);

				ingredientMapper.insert(ingredient);

				List<Allergy> allergyList = inge.getAllergyIdList();
				for (Allergy allergy : allergyList) {

					if (allergy.getId() != null) {
						IngredientsAllergies ingAg = new IngredientsAllergies();
						ingAg.setIngredientId(ingredient.getId());
						ingAg.setAllergyId(allergy.getId());

						ingAgMapper.insert(ingAg.getIngredientId(), ingAg.getAllergyId());
					}
				}

			}
		}

	}

	@Override
	public void editIngredient(Ingredient ingredient) throws Exception {
		ingredientMapper.update(ingredient);
	}

	//	selectフォーム用のList
	@Override
	public List<Ingredient> getIngredientFoodCat() throws Exception {
		return ingredientMapper.selectIngredientFoodCat();
	}

	//	ページネーション
	@Override
	public int getTotalPages(int numPerPage) throws Exception {
		double totalNum = (double) ingredientMapper.count();
		return (int) Math.ceil(totalNum / numPerPage);
	}

	//	ページネーション
	@Override
	public List<Ingredient> getIngredientListByPage(int page, int numPerPage) throws Exception {
		int offset = numPerPage * (page - 1);
		return ingredientMapper.selectLimited(offset, numPerPage);
	}

}
