package com.example.app.service;

import java.util.ArrayList;
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
System.out.println("allegy"+allergy);
					if (allergy.getId() != null) {
						IngredientsAllergies ingAg = new IngredientsAllergies();
						ingAg.setIngredientId(ingredient.getId());
						ingAg.setAllergyId(allergy.getId());

						ingAgMapper.insert(ingredient.getId(),allergy.getId());
					}
				}

			}
		}

	}

	@Override
	public void editIngredient(Ingredient ingredient) throws Exception {

		Ingredient newIng = new Ingredient();

		newIng.setId(ingredient.getId());
		newIng.setName(ingredient.getName());
		newIng.setKana(ingredient.getKana());
		newIng.setFoodCatId(ingredient.getFoodCatId());
		ingredientMapper.update(newIng);

		List<IngredientsAllergies> allergyList = ingredient.getIngAgList();
		List<IngredientsAllergies> newIngAgList = new ArrayList<>();
		if (allergyList != null) {
			for (IngredientsAllergies allergy : allergyList) {
				if (allergy.getAllergyId() != null) {
					IngredientsAllergies ingAg = new IngredientsAllergies();
					
					ingAg.setId(allergy.getId());
					ingAg.setIngredientId(newIng.getId());
					ingAg.setAllergyId(allergy.getAllergyId());
					newIngAgList.add(ingAg);

				}
			}
		}

		// 新しいデータ確認
		for (IngredientsAllergies newIngAg : newIngAgList) {
			System.out.println("NEW"+newIngAg);
		}

		List<IngredientsAllergies> oldIngAgList = ingAgMapper.selectByingredientId(ingredient.getId());
		for (IngredientsAllergies oldIngAg : oldIngAgList) {
			boolean isExist = false;
			// 古いデータ確認
			System.out.println("OLD"+oldIngAg);
			for (IngredientsAllergies newIngAg : newIngAgList) {

				if (newIngAg.getAllergyId().equals(oldIngAg.getAllergyId())) {
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				ingAgMapper.deleteById(oldIngAg.getId());
			}

		}

		for (IngredientsAllergies newIngAg : newIngAgList) {

			if (newIngAg.getId() == null) {
				ingAgMapper.insert(newIng.getId(), newIngAg.getAllergyId());
			} else {
				// newIngAgList内のIDがnullでない場合、既存のレコードを更新
				IngredientsAllergies oldIngAg2 = ingAgMapper.selectById(newIngAg.getId());
					if (!newIngAg.getAllergyId().equals(oldIngAg2.getAllergyId())) {
						ingAgMapper.update(newIngAg);
					}
			}
		}

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
