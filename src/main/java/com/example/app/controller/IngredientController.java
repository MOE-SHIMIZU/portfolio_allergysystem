package com.example.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Ingredient;
import com.example.app.domain.IngredientsAllergies;
import com.example.app.domain.IngredientsForm;
import com.example.app.service.AllergyService;
import com.example.app.service.FoodCatService;
import com.example.app.service.IngredientService;
import com.example.app.validation.EditGroup;

@Controller
@RequestMapping("/ingredient")
public class IngredientController {

	//	ページネーション　1ページあたりの表示件数
	private static final int NUM_PER_PAGE = 10;

	@Autowired
	private IngredientService ingredientService;

	@Autowired
	private FoodCatService foodCatService;

	@Autowired
	private AllergyService allergyService;

	@GetMapping
	public String ingrediet(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model)
			throws Exception {
		model.addAttribute("ingredientListByPage", ingredientService.getIngredientListByPage(page, NUM_PER_PAGE));
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", ingredientService.getTotalPages(NUM_PER_PAGE));
		return "system/ingredient/listIngredient";
	}

	@GetMapping("/add")
	public String addGet(Model model) throws Exception {
		model.addAttribute("foodCatList", foodCatService.getFoodCatList());
		model.addAttribute("allergyList", allergyService.getAllergyList());

		IngredientsForm ingredientsForm = new IngredientsForm();
		List<Ingredient> list = new ArrayList<>();
		Ingredient ingredient = new Ingredient();
		List<IngredientsAllergies> alist = new ArrayList<>();
		IngredientsAllergies allergy = new IngredientsAllergies();
		alist.add(allergy);
		alist.add(allergy);
		ingredient.setAllergyIdList(alist);
		list.add(ingredient);
		list.add(ingredient);
		list.add(ingredient);
		list.add(ingredient);
		list.add(ingredient);
		ingredientsForm.setIngredients(list);

		model.addAttribute("ingredientsForm", ingredientsForm);
		return "system/ingredient/addIngredient";
	}

	@PostMapping("/add")
	public String addPost(
			@Validated IngredientsForm ingredientsForm,
			Errors errors,
			RedirectAttributes ra,
			Model model) throws Exception {
		System.out.println(ingredientsForm);

		List<Ingredient> ingredientElements = ingredientsForm.getIngredients();
		List<FieldError> fieldErrors = new ArrayList<>(); // エラーを格納するリスト

		for (int i = 0; i < ingredientElements.size(); i++) {
			Ingredient currentElement = ingredientElements.get(i);

			boolean isNameEmpty = currentElement.getName() == null || currentElement.getName().isEmpty();
			boolean isKanaEmpty = currentElement.getKana() == null || currentElement.getKana().isEmpty();
			boolean isFoodCatIdNull = currentElement.getFoodCatId() == null;

			if (isNameEmpty) {
				if (!isKanaEmpty) {
					fieldErrors.add(
							new FieldError("ingredients", "ingredients[" + i + "].name", "error.ingredientName.empty"));
				}
			} else if (isKanaEmpty) {
				fieldErrors.add(new FieldError("ingredients", "ingredients[" + i + "].kana", "error.kana.empty"));
			}

			if (!isNameEmpty && !isKanaEmpty && isFoodCatIdNull) {
				fieldErrors.add(
						new FieldError("ingredients", "ingredients[" + i + "].foodCatId", "error.foodCatId.empty"));
			}

		}

		if (!fieldErrors.isEmpty()) {
			for (FieldError error : fieldErrors) {
				model.addAttribute("foodCatList", foodCatService.getFoodCatList());
				model.addAttribute("allergyList", allergyService.getAllergyList());
				errors.rejectValue(error.getField(), error.getDefaultMessage());
			}
			return "system/ingredient/addIngredient";
		}

		List<Ingredient> ingredients = ingredientsForm.getIngredients();
		List<Ingredient> validIngredients = ingredients.stream()
				.filter(ing -> ing.getName() != null && !ing.getName().isEmpty())
				.filter(ing -> ing.getKana() != null && !ing.getKana().isEmpty())
				.filter(ing -> ing.getFoodCatId() != null)
				.collect(Collectors.toList());

		if (validIngredients == null || validIngredients.isEmpty()) {
			model.addAttribute("foodCatList", foodCatService.getFoodCatList());
			model.addAttribute("allergyList", allergyService.getAllergyList());
			model.addAttribute("errorMessage", "新規食材登録に必要なデータが見当たりません。必要項目を入力してください。");
			return "system/ingredient/addIngredient";
		}

		ingredientsForm.setIngredients(validIngredients);
		ingredientService.addIngredient(ingredientsForm);

		ra.addFlashAttribute("statusMessage", "食材項目を追加しました。");
		return "redirect:/ingredient";
	}
	
	@GetMapping("/edit/{id}")
	public String updateGet(@PathVariable Integer id, Model model)throws Exception{
		model.addAttribute("ingredient", ingredientService.getIngredientById(id));
		model.addAttribute("foodCatList", foodCatService.getFoodCatList());
		model.addAttribute("allergyList", allergyService.getAllergyList());
		return "system/ingredient/editIngredient";
		
	}

	@PostMapping("/edit/{id}")
	public String updatePost(
			@PathVariable Integer id,
			@Validated(EditGroup.class) Ingredient ingredient,
			Errors errors,
			RedirectAttributes ra,
			Model model) throws Exception {

		if (errors.hasErrors()) {
			model.addAttribute("foodCatList", foodCatService.getFoodCatList());
			model.addAttribute("allergyList", allergyService.getAllergyList());
			return "system/ingredient/editIngredient";
		}

		ingredientService.editIngredient(ingredient);
		ra.addFlashAttribute("statusMessage", "食材項目を更新しました");
		return "redirect:/ingredient";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id, RedirectAttributes ra) throws Exception {
		ingredientService.deleteIngredientById(id);
		ra.addFlashAttribute("statusMessage", "食材項目を削除しました");
		return "redirect:/ingredient";
	}

}
