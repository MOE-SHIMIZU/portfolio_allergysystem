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

import com.example.app.domain.FoodCat;
import com.example.app.domain.FoodCatsForm;
import com.example.app.service.FoodCatService;
import com.example.app.validation.AddGroup;
import com.example.app.validation.EditGroup;

@Controller
@RequestMapping("/foodCat")
public class FoodCatController {
	
	private static final int NUM_PER_PAGE = 10;

	@Autowired
	private FoodCatService foodCatService;

	@GetMapping
	public String foodCat(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model) throws Exception {
		model.addAttribute("FoodCatListByPage", foodCatService.getAllergyListByPage(page, NUM_PER_PAGE));
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", foodCatService.getTotalPages(NUM_PER_PAGE));
		return "system/foodCat/listFoodCat";
	}

	@GetMapping("/add")
	public String addGet(Model model) throws Exception {
		FoodCatsForm foodCatsForm = new FoodCatsForm();

		List<FoodCat> foodCats = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			foodCats.add(new FoodCat());
		}
		foodCatsForm.setFoodCats(foodCats);

		model.addAttribute("foodCatsForm", foodCatsForm);
		return "system/foodCat/addFoodCat";
	}

	@PostMapping("/add")
	public String addPost(
			@Validated(AddGroup.class) FoodCatsForm foodCatsForm,
			Errors errors,
			RedirectAttributes ra,
			Model model) throws Exception {

		List<FoodCat> foodCats = foodCatsForm.getFoodCats();
		List<FieldError> fieldErrors = new ArrayList<>();

		for (int i = 0; i < foodCats.size(); i++) {
			FoodCat currentFoodCat = foodCats.get(i);

			if (currentFoodCat.getName() == null || currentFoodCat.getName().isEmpty()) {
				if (currentFoodCat.getKana() == null || currentFoodCat.getKana().isEmpty()) {
					continue;
				} else {
					fieldErrors.add(new FieldError("foodCats", "foodCats[" + i + "].name", "error.name.empty"));
				}
			} else if (currentFoodCat.getKana() == null || currentFoodCat.getKana().isEmpty()) {
				fieldErrors.add(new FieldError("foodCats", "foodCats[" + i + "].kana", "error.kana.empty"));
			}

		}

		if (!fieldErrors.isEmpty()) {
			for (FieldError error : fieldErrors) {
				errors.rejectValue(error.getField(), error.getDefaultMessage());
			}
			return "system/foodCat/addFoodCat";
		}

		List<FoodCat> validFoodCats = foodCats.stream()
				.filter(foodCat -> foodCat.getName() != null && !foodCat.getName().isEmpty())
				.filter(foodCat -> foodCat.getKana() != null && !foodCat.getKana().isEmpty())
				.collect(Collectors.toList());

		if (validFoodCats == null || validFoodCats.isEmpty()) {
			model.addAttribute("errorMessage", "新規食品カテゴリー登録に必要なデータが見当たりません。必要項目を入力してください。");
			return "system/foodCat/addFoodCat";
		} else if (errors.hasErrors()) {
			return "system/foodCat/addFoodCat";
		}

		foodCatService.addFoodCatsList(validFoodCats);
		ra.addFlashAttribute("statusMessage", "食品カテゴリー項目を追加しました");
		return "redirect:/foodCat";
	}

	@GetMapping("/edit/{id}")
	public String editGet(@PathVariable Integer id, Model model) throws Exception {
		model.addAttribute("foodCat", foodCatService.getFoodCatById(id));
		return "system/foodCat/editFoodCat";
	}

	@PostMapping("/edit/{id}")
	public String editPost(
			@PathVariable Integer id,
			@Validated(EditGroup.class) FoodCat foodCat,
			Errors errors, 
			RedirectAttributes ra,
			Model model) throws Exception {

		if (errors.hasErrors()) {
			return "system/foodCat/editFoodCat";
		}

		foodCatService.editFoodCat(foodCat);
		ra.addFlashAttribute("statusMessage", "食品カテゴリー項目を更新しました");
		return "redirect:/foodCat";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id, RedirectAttributes ra)throws Exception {
		foodCatService.deleteFoodCatById(id);
		ra.addFlashAttribute("statusMessage", "食品カテゴリー項目を削除しました");
		return "redirect:/foodCat";
	}
}
